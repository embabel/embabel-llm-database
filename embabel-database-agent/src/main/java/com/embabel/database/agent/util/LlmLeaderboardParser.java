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
    private static final String NAME = "model_name";
    private static final String PARAM_COUNT = "max_input_tokens";
    private static final String PRICE_PER_INPUT_TOKEN = "input_cents_per_million_tokens";
    private static final String PRICE_PER_OUTPUT_TOKEN = "output_cents_per_million_tokens";
    private static final String PROVIDER_MODELS = "providermodels";
    private static final String PROVIDER_PATH = "data/providers";
    private static final String UPDATED_AT = "updated_at";

    ObjectMapper objectMapper;

    TagParser taskParser;

    public LlmLeaderboardParser(ObjectMapper objectMapper,TagParser taskParser) {
        this.objectMapper = objectMapper;
        this.taskParser = taskParser;
    }

    @SuppressWarnings("unchecked")
    public List<ModelMetadata> parse(String json) {
        //init
        List<ModelMetadata> listModelMetadata = new ArrayList<>();
        //convert to the list map
        try {
            List<Map<String,Object>> providerModelList = objectMapper.readValue(json, new TypeReference<List<Map<String,Object>>>(){});
            //get the primary components
            for (Map<String,Object> providerModel : providerModelList) {
                String providerName = providerModel.get(NAME).toString();
                String modelName = providerModel.get(MODEL_ID).toString();
                //date parse
                LocalDate knowledgeCutoffDate = LocalDate.of(1970, 1, 1); //TODO need to fix updated at
                // if (providerModels.get(UPDATED_AT) != null && providerModels.get(UPDATED_AT).toString().length() > 0) {
                //     knowledgeCutoffDate = parseLocalDate(providerModels.get(UPDATED_AT).toString());
                // } //end if
                double pricePerInput = providerModel.get(PRICE_PER_INPUT_TOKEN) != null ? Double.parseDouble(providerModel.get(PRICE_PER_INPUT_TOKEN).toString()) : 0.0;
                double pricePerOutput = providerModel.get(PRICE_PER_OUTPUT_TOKEN) != null ? Double.parseDouble(providerModel.get(PRICE_PER_OUTPUT_TOKEN).toString()) : 0.0;
                //bulid the pricingModel
                PricingModel pricingModel = new PerTokenPricingModel(pricePerInput, pricePerOutput);
                //tokens
                Long paramCount = providerModel.get(PARAM_COUNT) != null ? Long.parseLong(providerModel.get(PARAM_COUNT).toString()) : 0l;
                //task
                List<String> tags = taskParser.getTags(providerModel);
                //build the metadata
                ModelMetadata modelMetadata = LlmModelMetadata.Companion.create(modelName,providerName,knowledgeCutoffDate,pricingModel,paramCount,tags,this.getClass().getSimpleName());
                //add
                listModelMetadata.add(modelMetadata);
            } //end loop                       
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
            //parse the providers        
            for (Path providerModelPath : providerModels) {
                //now we have the json file location
                String contents = new String(Files.readAllBytes(providerModelPath));
                //pass and parse
                models.addAll(parse(contents)); //add
            } //end for        
            //add
            completeModels.addAll(models);
        }
        catch (IOException e) {
            logger.error(e);
        }
        //return
        return completeModels;
    }

    @Override
    public List<ModelMetadata> parse(List<?> list) {
        logger.error("Not implemented for " + this.getClass().getName());
        return null;
    }

    /**
     * support function
     */
    private List<Path> getPaths(Path basePath) throws IOException {
        try (Stream<Path> walk = Files.walk(basePath)) {
            return walk.filter(Files::isRegularFile)
                .filter(p -> p.getFileName().toString().endsWith(JSON_SUFFIX))
                .filter(p -> p.getFileName().toString().contains(MODEL_PATH))
                .collect(Collectors.toList());        
        }
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
