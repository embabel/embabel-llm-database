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
package com.embabel.database.core.repository;


import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.ai.chat.model.ChatModel;

import com.embabel.common.ai.prompt.PromptContributor;
import com.embabel.database.core.repository.LlmModelMetadata;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes={InMemoryAiModelRepositoryITest.class,InMemoryAiModelRepositoryITest.TestConfig.class})
public class InMemoryAiModelRepositoryITest {

    @Autowired
    AiModelRepository aiModelRepository;

    @Test
    public void testSave() throws Exception {
        //check empty
        assertTrue(aiModelRepository.findAll().isEmpty());
        //create a model
        LocalDate dateStamp = LocalDate.now();
        String modelName = "model-0";
        String providerName = "provider-0";
        //setup a single model
        LlmModelMetadata singleModel = new LlmModelMetadata(modelName, providerName, dateStamp, null, 1l,"task");
        //save
        aiModelRepository.save(singleModel);
        //check
        assertFalse(aiModelRepository.findAll().isEmpty());
    }

    @TestConfiguration
    public static class TestConfig {

        @Bean
        public AiModelRepository aiModelRepository() {
            return new InMemoryAiModelRepository();
        }
    }
}