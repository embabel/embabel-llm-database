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


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.embabel.common.ai.model.ModelMetadata;
import com.embabel.database.agent.util.LlmLeaderboardParser;
import com.embabel.database.agent.util.ModelMetadataParser;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes={LlmLeaderboardModelMetadataDiscoveryServiceITest.class,LlmLeaderboardModelMetadataDiscoveryServiceITest.TestConfig.class})
public class LlmLeaderboardModelMetadataDiscoveryServiceITest {
    
    @Autowired
    ModelMetadataDiscoveryService modelMetadataDiscoveryService;

    @Test
    void testRetrieveModelMetadata() {
        List<ModelMetadata> models = modelMetadataDiscoveryService.retrieveModelMetadata();
        assertFalse(models.isEmpty());
        assertTrue(models.size() > 50);
    }

    @TestConfiguration
    public static class TestConfig {

        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }

        @Bean
        public ModelMetadataParser modelMetadataParser(ObjectMapper objectMapper) {
            return new LlmLeaderboardParser(objectMapper);
        }

        @Bean
        public ModelMetadataDiscoveryService modelMetadataDiscoveryService(ModelMetadataParser modelMetadataParser) {
            return new LlmLeaderboardModelMetadataDiscoveryService(modelMetadataParser);
        }
    }
}
