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

import java.util.List;
import java.util.stream.Collectors;

import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.annotation.Condition;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.common.ai.model.ModelMetadata;
import com.embabel.database.agent.domain.ListModelMetadata;

//@Agent(name="ModelSuggestionAgentCoordinator", description="Uses ModelSuggestionAgent, ModelProviderSuggestionAgent and others to coordinate Model Recommendation")
public class ModelSuggestionAgentCoordinator {
       
    @Condition(name="has_selected_provider")
    public boolean hasProvider(ListModelMetadata listModelMetadata,UserInput userInput) {
        String userInputText = userInput.getContent();
        //looks to see if the response is a provider
        //group
        List<String> providers = listModelMetadata.models()
            .stream()
            .map(ModelMetadata::getProvider)
            .distinct()
            .collect(Collectors.toList());
        //now check
        return providers.stream()
            .anyMatch(provider -> provider.equalsIgnoreCase(userInputText));
    }

    @Condition(name="has_selected_model")
    public boolean hasModel(ListModelMetadata listModelMetadata,UserInput userInput) {
        String userInputText = userInput.getContent();
        //validate
        return listModelMetadata.models()
            .stream()
            .anyMatch(model -> model.getName().equalsIgnoreCase(userInputText));
    }

}
