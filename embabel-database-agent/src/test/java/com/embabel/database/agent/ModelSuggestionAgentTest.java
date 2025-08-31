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
package com.embabel.database.agent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.embabel.agent.domain.io.UserInput;
import com.embabel.agent.testing.unit.FakeOperationContext;
import com.embabel.common.ai.model.ModelMetadata;
import com.embabel.common.ai.model.PerTokenPricingModel;
import com.embabel.common.ai.model.PricingModel;
import com.embabel.database.agent.domain.ProviderList;
import com.embabel.database.agent.domain.TagList;
import com.embabel.database.agent.util.LlmLeaderboardTagParser;
import com.embabel.database.agent.util.TagParser;
import com.embabel.database.core.repository.AiModelRepository;
import com.embabel.database.core.repository.InMemoryAiModelRepository;
import com.embabel.database.core.repository.LlmModelMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ModelSuggestionAgentTest {

    private static Log logger = LogFactory.getLog(ModelSuggestionAgentTest.class);
    
    @Test
    void testGetSuggestedTagList() throws Exception {
        //setup the objects
        ObjectMapper objectMapper = new ObjectMapper();
        ModelSuggestionAgent modelSuggestionAgent = new ModelSuggestionAgent();
        TagParser tagParser = new LlmLeaderboardTagParser();
        ReflectionTestUtils.setField(modelSuggestionAgent, "objectMapper", objectMapper);
        ReflectionTestUtils.setField(modelSuggestionAgent, "tagParser", tagParser);
        ReflectionTestUtils.setField(modelSuggestionAgent, "modelName", "llama3.1:8b");

        List<String> expectedTags = Collections.singletonList("image-to-text");

        FakeOperationContext operationContext = new FakeOperationContext();
        operationContext.expectResponse(new TagList(expectedTags));
        
        UserInput userInput = new UserInput("find a model that can extract text from an image");
        //invoke
        modelSuggestionAgent.getSuggestedTagList(userInput,operationContext);
    }

    @Test
    void testGetProviders() throws Exception {
        //build a tag list
        //get the models
        //get a list of groups
        ModelSuggestionAgent modelSuggestionAgent = new ModelSuggestionAgent();
        List<String> expectedTags = Collections.singletonList("image-to-text");
        TagList tagList = new TagList(expectedTags);

        AiModelRepository inMemoryAiModelRepository = new InMemoryAiModelRepository();
        //create models that match the list
        var today = LocalDate.now();
        //stub object
        var pricing = new PerTokenPricingModel(15.0,75.0);
        var llmInstance = new LlmModelMetadata(UUID.randomUUID().toString(),"gpt-4","OpenAI",today,pricing,10000l,expectedTags,"test",0l,"test");
        //insert
        inMemoryAiModelRepository.save(llmInstance);
        ReflectionTestUtils.setField(modelSuggestionAgent, "aiModelRepository", inMemoryAiModelRepository);
        //hand over to the models for tag
        ListModelMetadata models = modelSuggestionAgent.getModelsByTag(tagList);
        //now get providers
        ProviderList providers = modelSuggestionAgent.getProviders(models);
        assertNotNull(providers);
        assertNotNull(providers.providers());
        assertFalse(providers.providers().isEmpty());
    }
}
