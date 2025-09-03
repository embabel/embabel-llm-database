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
package com.embabel.database.server.service;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import com.embabel.agent.config.annotation.EnableAgents;
import com.embabel.agent.core.AgentPlatform;
import com.embabel.database.agent.ModelProviderSuggestionAgent;
import com.embabel.database.agent.service.LlmLeaderboardModelMetadataDiscoveryService;
import com.embabel.database.agent.service.ModelMetadataDiscoveryService;
import com.embabel.database.agent.service.ModelMetadataService;
import com.embabel.database.agent.util.LlmLeaderboardParser;
import com.embabel.database.agent.util.LlmLeaderboardTagParser;
import com.embabel.database.agent.util.ModelMetadataParser;
import com.embabel.database.agent.util.TagParser;
import com.embabel.database.core.repository.AiModelRepository;
import com.embabel.database.core.repository.InMemoryAiModelRepository;
import com.embabel.database.server.service.AgentExecutionService;
import com.embabel.database.server.service.ModelSuggestionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes={ModelSuggestionServiceITest.class,ModelSuggestionServiceITest.TestConfig.class})
@ActiveProfiles("ollama")
public class ModelSuggestionServiceITest {
    
    private static final Logger logger = LoggerFactory.getLogger(ModelSuggestionServiceITest.class);

    @Autowired
    ModelSuggestionService modelSuggestionService;

    @Autowired
    AiModelRepository aiModelRepository;

    @Test
    void testGetProviders() throws Exception {      
        //load the registry
        ((InMemoryAiModelRepository) aiModelRepository).load();
        //prompt
        String sessionId = UUID.randomUUID().toString();
        String prompt = "I want a model that can narrate a script";
        String result = modelSuggestionService.getProviders(prompt,sessionId);
        //show
        logger.info(result);
    }

    @TestConfiguration
    @EnableAgents
    public static class TestConfig {

        @Bean
        public ModelMetadataService metadataService() {
            return new ModelMetadataService();
        }

        @Bean
        public ModelMetadataDiscoveryService modelMetadataDiscoveryService(ModelMetadataParser modelMetadataParser) {
            return new LlmLeaderboardModelMetadataDiscoveryService(modelMetadataParser);
        }

        @Bean
        public ModelMetadataParser llmLeaderboardParser(ObjectMapper objectMapper, TagParser tagParser) {
            return new LlmLeaderboardParser(objectMapper,tagParser);
        }

        @Bean
        public LlmLeaderboardTagParser llmLeaderboardTagParser() {
            return new LlmLeaderboardTagParser();
        }

        @Bean
        public ModelSuggestionService modelSuggestionService() {
            return new ModelSuggestionService();
        }

        @Bean
        public AgentExecutionService agentExecutionService(AgentPlatform agentPlatform) {
            return new AgentExecutionService(agentPlatform);
        }

        @Bean        
        public ModelProviderSuggestionAgent modelSuggestionAgent() {
            return new ModelProviderSuggestionAgent();
        }

        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }

        @Bean
        public AiModelRepository aiModelRepository() {
            return new InMemoryAiModelRepository();
        }
    }
}
