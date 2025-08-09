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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.embabel.common.ai.model.ModelMetadata;
import com.embabel.database.agent.util.ModelMetadataParser;

import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrock.BedrockClient;
import software.amazon.awssdk.services.bedrock.model.FoundationModelSummary;
import software.amazon.awssdk.services.bedrock.model.ListFoundationModelsResponse;

@Service
public class AWSBedrockModelMetadataDiscoveryService implements ModelMetadataDiscoveryService {

    private static final Log logger = LogFactory.getLog(AWSBedrockModelMetadataDiscoveryService.class);

    ModelMetadataParser modelMetadataParser;

    BedrockClient bedrockClient;

    Region region;

    public AWSBedrockModelMetadataDiscoveryService(ModelMetadataParser modelMetadataParser, BedrockClient bedrockClient, Region region) {
        this.modelMetadataParser = modelMetadataParser;
        this.bedrockClient = bedrockClient;
        this.region = region;
    }

    @Override
    public List<ModelMetadata> retrieveModelMetadata() {
        //init
        List<ModelMetadata> listModelMetadata = new ArrayList<>();          
        //use the AWS API to retrieve the models
        try {
            logger.info("Invoking Bedrock retrieval");
            //get the response
            ListFoundationModelsResponse response = bedrockClient.listFoundationModels(r -> {});
            //get the models
            List<FoundationModelSummary> models = response.modelSummaries();
            //validate
            if (models.isEmpty()) {
                logger.warn("No available foundation models in " + region.toString());
            } else {
                //retrieve
                listModelMetadata = modelMetadataParser.parse(models);
            } //end if            
        } catch (SdkClientException e) {
            logger.error("failed to retrieve from AWS",e);
        }        

        //return
        return listModelMetadata;
    }

}