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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.embabel.common.ai.model.ModelMetadata;
import com.embabel.common.ai.model.PerTokenPricingModel;
import com.embabel.common.ai.model.PricingModel;
import com.embabel.database.core.repository.LlmModelMetadata;

import software.amazon.awssdk.services.bedrock.model.FoundationModelSummary;

public class AWSBedrockParser implements ModelMetadataParser {

    private static Log logger = LogFactory.getLog(AWSBedrockParser.class);

    @Autowired
    TaskParser awsBedrockTaskParser;
    
    @Override
    public List<ModelMetadata> parse(String json) {
        logger.error("Method parse(String json) not supported " + this.getClass().getName());
        return null;
    }

    @Override
    public List<ModelMetadata> parse(Path path) {
        logger.error("Method parse(Path path) not supported " + this.getClass().getName());
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ModelMetadata> parse(List<?> list) {
        //list is of FoundationModelSummary
        List<FoundationModelSummary> models = (List<FoundationModelSummary>) list;
        //init
        List<ModelMetadata> listModelMetadata = new ArrayList<>();
        for (FoundationModelSummary model : models) {
            //construct a model metadata
            String modelName = model.modelName();
            String providerName = model.providerName();
            // no knowledgecutoffdate available from AWS
            LocalDate knowledgeCutoffDate = LocalDate.of(1970,1,1);
            // no pricing data available for bedrock
            PricingModel pricingModel = new PerTokenPricingModel(0.0, 0.0);
            //get the task
            Map<String,Object> attributeMap = new HashMap<>();
            attributeMap.put(AWSBedrockTaskParser.INPUT_MODALITY_KEY,model.inputModalitiesAsStrings());
            attributeMap.put(AWSBedrockTaskParser.OUTPUT_MODALITY_KEY,model.outputModalitiesAsStrings());
            //task
            String task = awsBedrockTaskParser.getTask(attributeMap);
            //build the metadata
            ModelMetadata modelMetadata = LlmModelMetadata.Companion.create(modelName,providerName,knowledgeCutoffDate,pricingModel,0l,task,this.getClass().getSimpleName());
            //add
            listModelMetadata.add(modelMetadata);
        }
        
        //return
        return listModelMetadata;
    }
}