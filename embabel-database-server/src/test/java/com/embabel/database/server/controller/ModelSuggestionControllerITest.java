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
import org.springframework.context.annotation.Import;
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
import com.embabel.database.server.config.DefaultConfig;
import com.embabel.database.server.service.AgentExecutionService;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

@SpringBootTest
@Import(DefaultConfig.class)
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
        // Load the registry
        ((InMemoryAiModelRepository) aiModelRepository).load();

        String prompt = "Recommend a model that can narrate a script";
        logger.info("Sending initial provider suggestion request...");

        // Step 1: Send prompt to initiate session
        MvcResult initialResult = mockMvc.perform(post(url)
                .content(prompt.getBytes()))
            .andExpect(status().isOk())
            .andReturn();

        // Extract session ID from header
        String sessionId = initialResult.getResponse()
            .getHeader("X-embabel-request-id");
        assertNotNull(sessionId, "Session ID must be returned in header");
        logger.info("Session started with ID: {}", sessionId);

        // Step 2: Poll the GET endpoint for results until they are ready
        Map<String, Object> responseData = null;
        int maxAttempts = 5;
        int attempt = 0;
        while (true) {
            MvcResult pollResult = mockMvc.perform(get(url + "/" + sessionId)
                    .header("X-embabel-request-id", sessionId))
                .andExpect(status().isOk())
                .andReturn();

            String content = pollResult.getResponse().getContentAsString();
            logger.info("Polling attempt {} response: {}", attempt + 1, content);

            // Parse JSON response
            responseData = new ObjectMapper().readValue(content, Map.class);

            if (responseData.get("result") != null) {
                break; // results ready
            }

            Thread.sleep(10000); // wait 10 seconds before polling again
            attempt++;
        }

        assertNotNull(responseData, "Response data should not be null");
        assertTrue(responseData.get("result") != null, "Expected non-null results after polling");
        assertTrue(responseData.get("result").toString().contains("sambanova"),
            "Result should contain 'sambanova' provider");

        logger.info("Final provider results: {}", responseData);

        // Step 3: Send back a selection using the same session
        MvcResult selectionResult = mockMvc.perform(post(url)
                .content("sambanova")
                .header("X-embabel-request-id", sessionId))
            .andExpect(status().isOk())
            .andReturn();

        logger.info("asking the agent to use the sambanova model");

        //loop again on the session id
        while (true) {
            MvcResult pollResult = mockMvc.perform(get(url + "/" + sessionId)
                    .header("X-embabel-request-id", sessionId))
                .andExpect(status().isOk())
                .andReturn();

            String content = pollResult.getResponse().getContentAsString();
            logger.info("Polling attempt {} response: {}", attempt + 1, content);

            // Parse JSON response
            responseData = new ObjectMapper().readValue(content, Map.class);

            if (responseData.get("result") != null) {
                break; // results ready
            }

            Thread.sleep(10000); // wait 10 seconds before polling again
            attempt++;
        }        

        assertNotNull(responseData, "Response data should not be null");
        assertTrue(responseData.get("result") != null, "Expected non-null results after polling");
        logger.info("Models response: {}", responseData.get("result").toString());
    }

}
