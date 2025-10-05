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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.embabel.agent.domain.io.UserInput;
import com.embabel.common.ai.model.PerTokenPricingModel;
import com.embabel.common.ai.model.PricingModel;
import com.embabel.database.agent.domain.ListModelMetadata;
import com.embabel.database.core.repository.LlmModelMetadata;

public class ModelSuggestionAgentCoordinatorTest {
    
    @Test
    void testHasProvider() throws Exception {
        String provider = "deepinfra";
        //create a user input
        UserInput userInput = new UserInput(provider);
        //create a list of models        
        PricingModel pricing = new PerTokenPricingModel(15.0,75.0);        
        LlmModelMetadata llmInstance = new LlmModelMetadata(
            UUID.randomUUID().toString(), //UUID
            "gpt-4",          // name
            provider,         // provider
            LocalDate.now(),            // knowledgeCutoffDate
            pricing,          // pricingModel
            10000l,            // size (context length)
            Collections.singletonList("hello"),             // tags
            "test",           // source
            0l,                // parameters
            "test"            // modelName
        );        
        ListModelMetadata listModelMetadata = new ListModelMetadata(List.of(llmInstance));
        //instantiate
        ModelSuggestionAgentCoordinator modelSuggestionAgentCoordinator = new ModelSuggestionAgentCoordinator();
        //invoke the test
        assertTrue(modelSuggestionAgentCoordinator.hasProvider(listModelMetadata, userInput));
    }

    @Test
    void testHasModel() throws Exception {
        String modelName = "gpt-4";
        String provider = "deepinfra";
        //create a user input
        UserInput userInput = new UserInput(modelName);
        //create the list
        PricingModel pricing = new PerTokenPricingModel(15.0,75.0);        
        LlmModelMetadata llmInstance = new LlmModelMetadata(
            UUID.randomUUID().toString(), //UUID
            modelName,          // name
            provider,         // provider
            LocalDate.now(),            // knowledgeCutoffDate
            pricing,          // pricingModel
            10000l,            // size (context length)
            Collections.singletonList("hello"),             // tags
            "test",           // source
            0l,                // parameters
            "test"            // modelName
        );        
        ListModelMetadata listModelMetadata = new ListModelMetadata(List.of(llmInstance));
        //instantiate
        ModelSuggestionAgentCoordinator modelSuggestionAgentCoordinator = new ModelSuggestionAgentCoordinator();
        //invoke
        assertTrue(modelSuggestionAgentCoordinator.hasModel(listModelMetadata, userInput));
    }
}
