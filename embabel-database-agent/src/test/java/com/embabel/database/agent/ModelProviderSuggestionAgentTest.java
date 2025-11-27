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

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.embabel.agent.domain.io.UserInput;
import com.embabel.agent.testing.unit.FakeOperationContext;
import com.embabel.agent.testing.unit.FakePromptRunner;
import com.embabel.common.ai.model.PerTokenPricingModel;
import com.embabel.database.agent.domain.ListModelMetadata;
import com.embabel.database.agent.domain.ProviderOptions;
import com.embabel.database.agent.domain.TagList;
import com.embabel.database.agent.util.LlmLeaderboardTagParser;
import com.embabel.database.agent.util.TagParser;
import com.embabel.database.core.repository.AiModelRepository;
import com.embabel.database.core.repository.InMemoryAiModelRepository;
import com.embabel.database.core.repository.LlmModelMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ModelProviderSuggestionAgentTest {

    private static Log logger = LogFactory.getLog(ModelProviderSuggestionAgentTest.class);
    
    @Test
    void testGetSuggestedTagList() {
        //setup the objects
        ObjectMapper objectMapper = new ObjectMapper();
        ModelProviderSuggestionAgent modelSuggestionAgent = new ModelProviderSuggestionAgent(new LlmLeaderboardTagParser());
        TagParser tagParser = new LlmLeaderboardTagParser();
        ReflectionTestUtils.setField(modelSuggestionAgent, "objectMapper", objectMapper);
        ReflectionTestUtils.setField(modelSuggestionAgent, "tagParser", tagParser);
        ReflectionTestUtils.setField(modelSuggestionAgent, "modelName", "llama3.1:8b");
        List<String> expectedTags = Collections.singletonList("image-to-text");

        AiModelRepository inMemoryAiModelRepository = new InMemoryAiModelRepository();
        //create models that match the list
        var today = LocalDate.now();
        //stub object
        var pricing = new PerTokenPricingModel(15.0,75.0);
        var llmInstance = new LlmModelMetadata(UUID.randomUUID().toString(),"gpt-4","OpenAI",today,pricing, 10000L,expectedTags,"test",0L,"test");
        //insert
        inMemoryAiModelRepository.save(llmInstance);
        ReflectionTestUtils.setField(modelSuggestionAgent, "aiModelRepository", inMemoryAiModelRepository);

        // List<String> expectedTags = Collections.singletonList("image-to-text");

        FakeOperationContext operationContext = new FakeOperationContext();
        operationContext.expectResponse(new TagList(expectedTags));
        
        UserInput userInput = new UserInput("find a model that can extract text from an image");
        //invoke
        modelSuggestionAgent.getSuggestedTagList(userInput,operationContext);
    }

//     @Test
    void testGetProviders() throws Exception {
        //build a tag list
        //get the models
        //get a list of groups
        ModelProviderSuggestionAgent modelSuggestionAgent = new ModelProviderSuggestionAgent(new LlmLeaderboardTagParser());
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
        ReflectionTestUtils.setField(modelSuggestionAgent,"modelName","llama3.1:8b");
        //hand over to the models for tag
        ListModelMetadata models = modelSuggestionAgent.getModelsByTag(tagList);
        //sample response from ollama / llama3.1:8b
        var response = """
            **Select a Provider**

            To provide you with the best possible models, we need to determine which provider will be used for your request. Below are our available providers:

            **Provider Options:**

            1. **OpenAI**
                * Strengths: Highly advanced language models, capable of generating human-like text.
                * Weaknesses: Can be expensive and may not always provide the desired level of accuracy.

            Please select one of the providers above by entering its corresponding number (1). This will allow us to retrieve a set of models that meet your requirements. 

            What is your selection?                
                """;
        //now get providers
        UserInput userInput = new UserInput("prompt");
        FakeOperationContext operationContext = new FakeOperationContext(); 
        FakePromptRunner promptRunner = (FakePromptRunner) operationContext.promptRunner();      
        ProviderOptions expected = new ProviderOptions(response);//,Collections.singletonList("OpenAI"));
        operationContext.expectResponse(operationContext);         
        modelSuggestionAgent.getProviders(models,userInput,operationContext);
        promptRunner.getLlmInvocations().getFirst().getMessages().forEach(message -> {
            logger.info("role " + message.getRole().toString() + " " + message.getContent().contains("OpenAI"));
        });
//        assertTrue(promptRunner.getLlmInvocations().getFirst().getPrompt().contains("OpenAI"));
        // assertNotNull(providers);
        // assertNotNull(providers.response());
    }
}
