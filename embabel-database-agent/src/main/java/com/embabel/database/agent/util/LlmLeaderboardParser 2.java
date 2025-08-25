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
package com.embabel.database.agent.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.embabel.common.ai.model.ModelMetadata;
import com.embabel.common.ai.model.PerTokenPricingModel;
import com.embabel.common.ai.model.PricingModel;
import com.embabel.database.core.repository.LlmModelMetadata;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LlmLeaderboardParser implements ModelMetadataParser {
        
    private static Log logger = LogFactory.getLog(LlmLeaderboardParser.class);

    private static final String ALTERNATE_DATE_FORMAT = "yyyy-M-d";
    private static final String JSON_SUFFIX = ".json";
    private static final String MODEL_ID = "model_id";
    private static final String MODEL_PATH = "models";
    private static final String NAME = "name";
    private static final String PARAM_COUNT = "param_count";
    private static final String PRICE_PER_INPUT_TOKEN = "price_per_input_token";
    private static final String PRICE_PER_OUTPUT_TOKEN = "price_per_output_token";
    private static final String PROVIDER_MODELS = "providermodels";
    private static final String PROVIDER_PATH = "providers";
    private static final String UPDATED_AT = "updated_at";

    ObjectMapper objectMapper;

    public LlmLeaderboardParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @SuppressWarnings("unchecked")
    public List<ModelMetadata> parse(String json) {
        //init
        List<ModelMetadata> listModelMetadata = new ArrayList<>();
        //convert to the list map
        try {
            Map<String,Object> providerModels = objectMapper.readValue(json, new TypeReference<Map<String,Object>>(){});
            //get the primary components
            String providerName = providerModels.get(NAME).toString();
            //loop through the model
            List<Map<String,Object>> models = (List<Map<String, Object>>) providerModels.get(PROVIDER_MODELS);
            for (Map<String,Object> model : models) {
                String modelName = model.get(MODEL_ID).toString();
                //date parse
                LocalDate knowledgeCutoffDate = null;
                if (model.get(UPDATED_AT) != null && model.get(UPDATED_AT).toString().length() > 0) {
                    knowledgeCutoffDate = parseLocalDate(model.get(UPDATED_AT).toString());
                } //end if
                double pricePerInput = model.get(PRICE_PER_INPUT_TOKEN) != null ? (double) model.get(PRICE_PER_INPUT_TOKEN) : 0.0;
                double pricePerOutput = model.get(PRICE_PER_OUTPUT_TOKEN) != null ? (double) model.get(PRICE_PER_OUTPUT_TOKEN) : 0.0;
                //bulid the pricingModel
                PricingModel pricingModel = new PerTokenPricingModel(pricePerInput, pricePerOutput);
                //build the metadata
                ModelMetadata modelMetadata = LlmModelMetadata.Companion.create(modelName,providerName,knowledgeCutoffDate,pricingModel);
                //add
                listModelMetadata.add(modelMetadata);
            } //end for            
        } catch (Exception e) {
            logger.error(e);
        }
        //return 
        return listModelMetadata;
    }

    @Override
    public List<ModelMetadata> parse(Path path) {
        //load the model directory being the Git clone
        if (!path.toFile().exists() || !path.toFile().isDirectory()) {
            //don't continue
            logger.warn("Path location doesn't exist " + path.toString());            
            return null;
        } //end if
        //init
        List<ModelMetadata> completeModels = new ArrayList<>();
        List<Path> providerModels = null;
        //process
        try {
            //get the provider path
            Path providerPath = path.resolve(PROVIDER_PATH);
            //list model
            providerModels = getPaths(providerPath);
            //validate it's not null
            if (providerModels == null) {
                //don't continue
                logger.warn("No jsons in the location " + path.toString());
                return completeModels;
            } //end if
            //init empty model list
            List<ModelMetadata> models = new ArrayList<>();
            // Map<String,String> providerDictionary = new HashMap<>();
            //parse the providers        
            for (Path providerModelPath : providerModels) {
                //now we have the json file location
                String contents = new String(Files.readAllBytes(providerModelPath));
                //pass and parse
                models.addAll(parse(contents)); //add
            } //end for                
            //check if we have any models
            if (models.isEmpty()) {
                logger.warn("unable to parse models from json path");
                return completeModels; //exit out
            } //end if
            //init
            List<Path> sourceModels = null; 
            Path modelPath = path.resolve(MODEL_PATH);
            sourceModels = getPaths(modelPath);
            //then parse the model data to match
            if (sourceModels == null) {
                //don't continue
                logger.warn("No jsons in the location " + path.toString());
                return completeModels;
            } //end if
            //now we have a list of paths which includes the MODEL NAME and MODEL SOURCE (NOT PROVIDER)
            for (Path sourceModelPath : sourceModels) {
                String name = getModelName(sourceModelPath);
                //retrieve the model
                for (ModelMetadata model : models) { //these are only the models that have providers
                    if (model.getName().equals(name)) {
                        //name matches, update with the parameters
                        //new model
                        ModelMetadata modelMetadata = updateModel(sourceModelPath,model);
                        //add back
                        completeModels.add(modelMetadata);
                    }//end if
                } //end if
            } //end for
        }
        catch (IOException e) {
            logger.error(e);
        }
        //return
        return completeModels;
    }

    /**
     * support function
     * @param jsonLocation
     * @param existingModel
     * @return
     * @throws IOException
     */
    private ModelMetadata updateModel(Path jsonLocation,ModelMetadata existingModel) throws IOException {
        //cast model
        LlmModelMetadata model = (LlmModelMetadata) existingModel;
        InputStream inputStream = Files.newInputStream(jsonLocation);
        Map<String,Object> modelData = objectMapper.readValue(inputStream,new TypeReference<Map<String,Object>>(){});
        Long paramCount = modelData.get(PARAM_COUNT) != null ? (Long) modelData.get(PARAM_COUNT) : 0l;
        //new model
        return LlmModelMetadata.Companion.create(model.getName(),model.getProvider(),model.getKnowledgeCutoffDate(),model.getPricingModel(),paramCount);
    }

    /**
     * support function
     */
    private List<Path> getPaths(Path basePath) throws IOException {
        try (Stream<Path> walk = Files.walk(basePath)) {
            return walk.filter(Files::isRegularFile)
                .filter(p -> p.getFileName().toString().endsWith(JSON_SUFFIX))
                .collect(Collectors.toList());        
        }
    }

    /**
     * support function
     * @param path
     * @return
     */
    private String getModelName(Path path) {
        String pathString = path.toString();
        int lastSlash = pathString.lastIndexOf('/');
        //now get the next to last
        int previousSlash = pathString.lastIndexOf('/',lastSlash -1);
        //extract
        return pathString.substring(previousSlash + 1,lastSlash);
    }

    /**
     * date helper
     * @param date
     * @return
     */
    private LocalDate parseLocalDate(String date) {
        try {
            return LocalDate.parse(date);
        }
        catch (DateTimeParseException dtp) {
            logger.warn(dtp);//only sending the exception to keep the log line smaller
        }
        //setup the formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ALTERNATE_DATE_FORMAT); //alternate format
        try {
            //try again
            return LocalDate.parse(date, formatter);
        }
        catch (DateTimeParseException e) {
            logger.error(e);
        }
        //return
        return null;
    }

}
