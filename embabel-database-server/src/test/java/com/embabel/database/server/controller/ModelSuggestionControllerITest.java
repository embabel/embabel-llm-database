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

import com.embabel.database.core.repository.ModelRepository;
import com.embabel.database.core.repository.util.ModelRepositoryLoader;
import com.embabel.database.server.IntegrationSupport;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.embabel.database.server.config.DefaultConfig;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

@SpringBootTest(classes = {IntegrationSupport.class}, properties = {
        "spring.ai.model.chat=ollama",
        "embabel.models.default-llm=us.anthropic.claude-3-5-sonnet-20240620-v1:0"
})
@Import(DefaultConfig.class)
public class ModelSuggestionControllerITest {

    private static final Logger logger = LoggerFactory.getLogger(ModelSuggestionControllerITest.class);
    
    String url = "/api/v1/models/recommend";

    MockMvc mockMvc;

    @Autowired
    ModelSuggestionController modelSuggestionController;

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    ModelRepositoryLoader modelRepositoryLoader;

    @Autowired
    ObjectMapper objectMapper;


    @BeforeEach
    public void beforeTest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(modelSuggestionController).build();
        //clean
        modelRepository.reset();
        //load
        modelRepositoryLoader.loadFromFile("./json/export.json");
    }

//    @Test
    void testRecommendations() throws Exception {

        String prompt = "Recommend a model that can narrate a script";
        logger.info("Sending initial provider suggestion request...");

        // Step 1: Send prompt to initiate session
        MvcResult initialResult = mockMvc.perform(post(url)
                .content(prompt.getBytes())
                        .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

        // Extract session ID from header
        String sessionId = initialResult.getResponse()
            .getHeader("x-embabel-request-id");
        assertNotNull(sessionId, "Session ID must be returned in header");
        logger.info("Session started with ID: {}", sessionId);
        //use "DeepSeek"

        // Step 2: send provider "DeepSeek"
        initialResult = mockMvc.perform(post(url)
                        .content("deepseek")
                        .header("x-embabel-request-id",sessionId))
                .andExpect(status().isOk())
                .andReturn();
        //dump
        logger.info(initialResult.getResponse().getContentAsString());
    }

    //trigger a batch job and monitor
    @Test
    void testRefresh() throws Exception {
        String response = mockMvc.perform(post("/api/v1/models/refresh")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn()
                .getResponse()
                .getContentAsString();
        //get the executionId
        Map<String,String> execution = objectMapper.readValue(response, new TypeReference<Map<String, String>>() {});
        assertTrue(execution.containsKey("executionId"));
        String executionId = execution.get("executionId");
        assertNotNull(executionId);
        // now use to monitor the counts
        Thread.sleep(10000);//wait 5 seoncds
        response = mockMvc.perform(get("/api/v1/models/refresh/" + executionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        execution = objectMapper.readValue(response, new TypeReference<Map<String, String>>() {});
        assertTrue(execution.containsKey("status"));
        logger.info(execution.get("status"));
        //now try for the counts
        response = mockMvc.perform(get("/api/v1/models/refresh/" + executionId + "/counts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        logger.info(response);
        execution = objectMapper.readValue(response, new TypeReference<Map<String, String>>() {});
        assertTrue(execution.containsKey("startCount"));
        assertTrue(execution.containsKey("currentCount"));
        logger.info("startCount " + execution.get("startCount") + " currentCount " + execution.get("currentCount"));

    }

}
