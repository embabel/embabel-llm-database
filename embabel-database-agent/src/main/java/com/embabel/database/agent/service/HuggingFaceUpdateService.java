/*
 * Copyright 2024-2025 Embabel Software, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.embabel.database.agent.service;

import java.io.File;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.ResponseSpec;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.embabel.common.ai.model.ModelMetadata;
import com.embabel.database.core.repository.LlmModelMetadata;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HuggingFaceUpdateService implements UpdateService {

private static final Log logger = LogFactory.getLog(HuggingFaceUpdateService.class);

    private static final String HF_MODEL_FILE = "hf.models.json";
    private static final String HF_MODEL_DETAILS_FILE = "hf.models.details.json";
    private static final String HF_MODEL_FILE_PATH = "./data";

    Pattern pattern = Pattern.compile("<([^>]+)>;\\s*rel=\"next\"");
    String baseUrl = "https://huggingface.co/api/models";

    @Autowired
    ObjectMapper objectMapper;

    List<Map<String,Object>> hfModels;

    Map<String,Map<String,Object>> hfModelDetails;

    private final WebClient webClient;

    public HuggingFaceUpdateService() {        
        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory();
        defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        this.webClient = WebClient.builder()
            .uriBuilderFactory(defaultUriBuilderFactory)
            .exchangeStrategies(ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1 * 1024 * 1024)) //1MB memory
                .build())
            .build();

        hfModels = new ArrayList<>();
        hfModelDetails = new HashMap<>();
    }

    @Override
    public List<ModelMetadata> updateModels(List<ModelMetadata> models) {
        //retrieve the model list from huggingface
        //iterate over and compare to existing model list
        //when a match is found, update the models with additional datapoints from huggingface

        loadModels(); //load from either disk or setup a retrieval
        loadModelDetails(); //load the details that are known

        //iterate over the models
        for (Map<String,Object> hfModel : hfModels) {
            //let's get the name to help validate
            String hfRepository = hfModel.get("modelId").toString(); // this is the huggingface modelId field
            //trim to get the name that will match the model we have
            String hfName = getModelName(hfRepository);
            String hfTag = hfModel.get("pipeline_tag") != null ? hfModel.get("pipeline_tag").toString() : null;
            //got through existing models and find matches for the names
            // for (ModelMetadata model : models) {
            for (int i=0;i<models.size();i++) {
                LlmModelMetadata llmModel = (LlmModelMetadata) models.get(i);
                if (llmModel.getModelName() == null) {
                    logger.warn(llmModel.getName() + " is null");
                    continue;
                } //end if
                if (llmModel.getModelName().toLowerCase().contains(hfName)) {
                    //suspected match --> need corrboration, use tags, any match will do
                    //check if there's a tag
                    if (hfTag == null) {
                        //can't validate --> move on
                        logger.warn("can't validate, no tag " + hfName + " " + llmModel.getModelName());
                        continue;
                    } //end if
                    Set<String> llmTags = new HashSet<>(llmModel.getTags());
                    if (llmTags.contains(hfTag)) {
                        //have a match
                        //now we can enhance with
                        //knowledgecutoffdate
                        String createdAt = hfModel.get("createdAt").toString();
                        long parameters = 0l;//setup for empty
                        Map<String,Object> hfModelDetail = getHfModel(hfRepository);
                        //retrieve the key "safetensors" > "total"
                        if (hfModelDetail.containsKey("safetensors")) {
                            Map<String,Object> safetensors = (Map<String,Object>) hfModelDetail.get("safetensors");
                            parameters = Long.parseLong(safetensors.get("total").toString());
                        } //end if
                        //update the model      
                        llmModel = updateModel(llmModel,createdAt,parameters);
                        models.set(i,llmModel);
                    } //end if
                }
            }
        }

        return models;
    }

    Map<String,Object> getHfModel(String hfModelId) {
        //check in the map
        if (hfModelDetails.containsKey(hfModelId)) {
            return hfModelDetails.get(hfModelId);
        }//end if
        //load from hf
        String uri = baseUrl + hfModelId;
        //build
        ResponseEntity<Map<String,Object>> responseEntity = webClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Map<String,Object>>() {})
                .block();
        if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
            //add to the map
            hfModelDetails.put(hfModelId,responseEntity.getBody());
            //write out the map
            saveHfDetails();
            //return
            return responseEntity.getBody();
        } else {
            logger.error("unable to retrieve details for " + hfModelId);
            return null;
        } //end if
    }

    void saveHfDetails() {
        try {
            //make sure it exists
            if (!new File(HF_MODEL_FILE_PATH).exists()) {
                new File(HF_MODEL_FILE_PATH).mkdirs();
            } //end if
            //now dump
            objectMapper.writeValue(new File(HF_MODEL_FILE_PATH, HF_MODEL_DETAILS_FILE), hfModelDetails);            
        } catch (Exception e) {
            logger.error("error writing details to file",e);
        }
    }

    //load models from JSON
    //if the JSON doesn't exist, get from the website
    //once loaded, write out to the disk
    void loadModels() {
        if (!hfModels.isEmpty()) {
            return; //nothing to do here
        } //end if
        try {
            //check if the file exists
            File location = new File(HF_MODEL_FILE_PATH, HF_MODEL_FILE);
            if (location.exists()) {
                //load into the object
                logger.info("loading HF models from file");
                hfModels = objectMapper.readValue(location,new TypeReference<List<Map<String,Object>>>(){});
                return;
            } //end if
            //file doesn't exist --> load from hf and serialize
            hfModels = getModels();
            //make sure it exists
            if (!new File(HF_MODEL_FILE_PATH).exists()) {
                new File(HF_MODEL_FILE_PATH).mkdirs();
            } //end if
            //now dump
            objectMapper.writeValue(new File(HF_MODEL_FILE_PATH, HF_MODEL_FILE), hfModels);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    void loadModelDetails() {
        if (!hfModelDetails.isEmpty()) {
            return; //nothing to do here
        }//end if
        try {
            File location = new File(HF_MODEL_FILE_PATH, HF_MODEL_DETAILS_FILE);
            if (location.exists()) {
                //load into the object
                hfModels = objectMapper.readValue(location,new TypeReference<List<Map<String,Object>>>(){});
                return;
            } //end if
            //file doesn't exist --> it will be build dynamically
        } catch (Exception e) {
            logger.error(e);
        }
    }

    List<Map<String,Object>> getModels() throws Exception {
        logger.info("starting to load the HF models...");
        List<Map<String,Object>> models = new ArrayList<>();

        String currentUrl = baseUrl;
        while (currentUrl != null) {
            //inject a pause to help slow down the requests
            Thread.sleep(100); //wait 100ms
            ResponseEntity<List<Map<String,Object>>> responseEntity = webClient.get()
                .uri(currentUrl)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<Map<String,Object>>>() {})
                .block();
           
            if (responseEntity != null && responseEntity.getStatusCode().is2xxSuccessful()) {
                List<Map<String,Object>> page = responseEntity.getBody();
                //add
                models.addAll(page);
                //continue
                HttpHeaders headers = responseEntity.getHeaders();
                currentUrl = getNextPageLink(headers);
            } else {
                logger.error("No data");
                currentUrl = null;
            } //end if
        } //end while
        logger.info("finished loading the HF models: " + models.size());
        //return
        return models;
    }    

    private String getNextPageLink(HttpHeaders headers) {
        String linkHeader = headers.getFirst(HttpHeaders.LINK);
        if (linkHeader == null) {
            logger.info("null headers");
            return null;
        } //end if
        //regex
        Matcher matcher = pattern.matcher(linkHeader);

        if (matcher.find()) {
            return matcher.group(1); // return the URL for the next page
        } //end if
        return null;
    }

    private String getModelName(String hfModelName) {
        return hfModelName.substring(hfModelName.indexOf("/")+1);
    }

    private LlmModelMetadata updateModel(LlmModelMetadata model,String createdAt,long parameters) {
        //build the date
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(createdAt);
        LocalDate knowledgecutoffdate = offsetDateTime.atZoneSameInstant(ZoneId.systemDefault()).toLocalDate();
        //handle parameters
        long updateParameters = model.getParameters() > 0 ? model.getParameters() : parameters;
        //return
        return new LlmModelMetadata(
            model.getModelId(),
            model.getName(),
            model.getProvider(),
            knowledgecutoffdate,
            model.getPricingModel(),
            model.getSize(),
            model.getTags(),
            model.getSource(),
            updateParameters,
            model.getModelName()
        );
    }
}
