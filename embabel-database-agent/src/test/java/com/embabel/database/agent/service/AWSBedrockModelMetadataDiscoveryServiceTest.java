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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.embabel.common.ai.model.ModelMetadata;
import com.embabel.database.agent.util.AWSBedrockTaskParser;
import com.embabel.database.agent.util.AWSBedrockParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrock.BedrockClient;
import software.amazon.awssdk.services.bedrock.model.FoundationModelSummary;
import software.amazon.awssdk.services.bedrock.model.ListFoundationModelsResponse;


public class AWSBedrockModelMetadataDiscoveryServiceTest {
    

    @SuppressWarnings("unchecked")
    @Test
    void testRetrieveModelMetadata() {
        //init
        String modelName = "model";
        String providerName = "provider";                
        //setup dependencies
        AWSBedrockTaskParser taskParser = new AWSBedrockTaskParser();
        ObjectMapper objectMapper = new ObjectMapper();
        ReflectionTestUtils.setField(taskParser, "objectMapper", objectMapper);
        AWSBedrockParser awsBedrockParser = new AWSBedrockParser();
        ReflectionTestUtils.setField(awsBedrockParser, "awsBedrockTaskParser", taskParser);
        Region region = Region.US_EAST_1;
        //stub out components
        BedrockClient bedrockClient = mock(BedrockClient.class);
        ListFoundationModelsResponse listFoundationModelsResponse = mock(ListFoundationModelsResponse.class);
        FoundationModelSummary foundationModelSummary = FoundationModelSummary.builder().modelName(modelName).providerName(providerName).build();
        List<FoundationModelSummary> foundationModelSummaryList = Collections.singletonList(foundationModelSummary);
        //setup responses
        when(bedrockClient.listFoundationModels(any(Consumer.class))).thenReturn(listFoundationModelsResponse);
        when(listFoundationModelsResponse.modelSummaries()).thenReturn(foundationModelSummaryList);
        //setup
        AWSBedrockModelMetadataDiscoveryService awsBedrockModelMetadataDiscoveryService = new AWSBedrockModelMetadataDiscoveryService(awsBedrockParser,bedrockClient,region);
        //invoke
        List<ModelMetadata> results = awsBedrockModelMetadataDiscoveryService.retrieveModelMetadata();
        //test
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(modelName,results.get(0).getName());
        assertEquals(providerName,results.get(0).getProvider());
        
    }
}
