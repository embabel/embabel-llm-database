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

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.embabel.common.ai.model.ModelMetadata;
import com.embabel.common.ai.model.PerTokenPricingModel;
import com.embabel.common.ai.model.PricingModel;
import com.embabel.database.core.repository.LlmModelMetadata;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GoogleParser implements ModelMetadataParser {

    private static Log logger = LogFactory.getLog(GoogleParser.class);

    private static final String MODEL_LIST_KEY = "models";

    @Autowired
    TagParser googleTaskParser;

    @Autowired
    ObjectMapper objectMapper;

    @SuppressWarnings("unchecked")
    @Override
    public List<ModelMetadata> parse(String json) {
        //init
        List<ModelMetadata> listModelMetadata = new ArrayList<>();
        //get the raw list
        try {
            Map<String,Object> modelsHighLevel = objectMapper.readValue(json,new TypeReference<Map<String,Object>>(){});
            //get the list
            List<Map<String,Object>> models = (List<Map<String, Object>>) modelsHighLevel.get(MODEL_LIST_KEY);
            //loop
            for (Map<String,Object> model : models) {
                //get components
                String providerName = "google";
                String modelName = model.get("name").toString();
                //date parse
                LocalDate knowledgeCutoffDate = LocalDate.of(1970, 1, 1); //TODO need to fix updated at
                //price
                PricingModel pricingModel = new PerTokenPricingModel(0.0, 0.0);
                //tokens
                Long contextLength = model.get("inputTokenLimit") != null ? Long.parseLong(model.get("inputTokenLimit").toString()) : 0l;
                //get the task
                List<String> tags = googleTaskParser.getTags(model);
                //build the metadatamodel
                ModelMetadata modelMetadata = LlmModelMetadata.Companion.create(modelName,providerName,knowledgeCutoffDate,pricingModel,contextLength,tags,this.getClass().getSimpleName(),0l,modelName);
                //add
                listModelMetadata.add(modelMetadata);
            } //end for
        } catch (Exception e) {
            logger.error("error parsing",e);
        }
        return listModelMetadata;
    }

    @Override
    public List<ModelMetadata> parse(Path path) {
        logger.error("Not implemented for " + this.getClass().getName());
        return null;
    }

    @Override
    public List<ModelMetadata> parse(List<?> list) {
        logger.error("Not implemented for " + this.getClass().getName());
        return null;
    }
    
}
