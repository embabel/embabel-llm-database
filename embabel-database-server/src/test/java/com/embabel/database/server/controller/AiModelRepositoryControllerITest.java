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

import com.embabel.common.ai.model.ModelMetadata;
import com.embabel.common.ai.model.ModelType;
import com.embabel.database.server.config.DefaultConfig;
import com.embabel.database.core.repository.AiModelRepository;
import com.embabel.database.core.repository.LlmModelMetadata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

@SpringBootTest
@Import(DefaultConfig.class)
@ActiveProfiles({"ollama","no-auto-load"})
public class AiModelRepositoryControllerITest {
    
    MockMvc mockMvc;

    @Autowired
    AiModelRepository aiModelRepository;

    @Autowired
    AiModelRepositoryController aiModelRepositoryController;

    @BeforeEach
    public void beforeTest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(aiModelRepositoryController).build();
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get("/api/v1/models"))
            .andExpect(status().isNotFound()); //no data
        //add some content
        LocalDate dateStamp = LocalDate.now();
        String modelName = "model-0";
        String providerName = "provider-0";
        //setup a single model
        LlmModelMetadata singleModel = new LlmModelMetadata(UUID.randomUUID().toString(),modelName, providerName, dateStamp, null, 1l,Collections.singletonList("task"),"test",0l,"modelName");
        //save
        aiModelRepository.save(singleModel);
        //try the mock again
        mockMvc.perform(get("/api/v1/models"))
            .andExpect(status().isOk()); //simple is ok (200)        
    }

    // Stub/mock implementation for testing
    static class TestModelMetadata implements ModelMetadata {
        private final String name;
        private final String provider;
        private final ModelType type;

        public TestModelMetadata(String name, String provider, ModelType type) {
            this.name = name;
            this.provider = provider;
            this.type = type;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getProvider() {
            return provider;
        }

        @Override
        public ModelType getType() {
            return type;
        }
    }

}
