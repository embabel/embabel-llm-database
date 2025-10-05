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
package com.embabel.database.server.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.embabel.agent.config.annotation.EnableAgents;
import com.embabel.agent.core.AgentPlatform;
import com.embabel.database.agent.ModelProviderSuggestionAgent;
import com.embabel.database.agent.ModelSuggestionAgent;
import com.embabel.database.agent.service.LlmLeaderboardModelMetadataDiscoveryService;
import com.embabel.database.agent.service.ModelMetadataDiscoveryService;
import com.embabel.database.agent.service.ModelMetadataService;
import com.embabel.database.agent.service.ModelSuggestionService;
import com.embabel.database.agent.util.LlmLeaderboardParser;
import com.embabel.database.agent.util.LlmLeaderboardTagParser;
import com.embabel.database.agent.util.ModelMetadataParser;
import com.embabel.database.agent.util.TagParser;
import com.embabel.database.core.repository.AiModelRepository;
import com.embabel.database.core.repository.InMemoryAiModelRepository;
import com.embabel.database.server.service.AgentExecutionService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes={ModelSuggestionControllerITest.class,ModelSuggestionControllerITest.TestConfig.class})
@ActiveProfiles("ollama")
public class ModelSuggestionControllerITest {

    private static final Logger logger = LoggerFactory.getLogger(ModelSuggestionControllerITest.class);
    
    String url = "/api/v1/models/recommend";

    MockMvc mockMvc;

    @Autowired
    ModelSuggestionController modelSuggestionController;

    @Autowired
    AiModelRepository aiModelRepository;    

    @BeforeEach
    public void beforeTest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(modelSuggestionController).build();
    }

    @Test
    void testRecommendations() throws Exception {
        //load the registry
        ((InMemoryAiModelRepository) aiModelRepository).load();
        //run the prompt        
        String prompt = "Recommend a model that can narrate a script";
        //expected results are text to audio tags
        MvcResult result = mockMvc.perform(post(url).content(prompt.getBytes()))
                                .andExpect(status().isOk())
                                .andReturn();
        //check for the presence of the header
        String requestId = result.getResponse()
                                .getHeader("X-embabel-request-id");
        assertNotNull(requestId);
        //check for the provider names
        String providers = result.getResponse().getContentAsString();
        assertNotNull(providers);
        assertTrue(providers.contains("sambanova")); //this should be in the list
        logger.info(providers);
        //now send back the "selection"
        result = mockMvc.perform(post(url).content("sambanova").header("X-embabel-request-id",requestId))
                        .andExpect(status().isOk())
                        .andReturn();
        String models = result.getResponse().getContentAsString();
        assertNotNull(models);
        logger.info(models);

    }


    @TestConfiguration
    @EnableAgents
    public static class TestConfig {

      
        @Bean
        public ModelProviderSuggestionAgent modelProviderSuggestionAgent(TagParser tagParser) {
            return new ModelProviderSuggestionAgent(tagParser);
        }

        @Bean
        public ModelSuggestionAgent modelSuggestionAgent() {
            return new ModelSuggestionAgent();
        }

        @Bean
        public ModelSuggestionController modelSuggestionController() {
            return new ModelSuggestionController();
        }

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
        public TagParser llmLeaderboardTagParser() {
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
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }

        @Bean
        public AiModelRepository aiModelRepository() {
            return new InMemoryAiModelRepository();
        }
    }
}
