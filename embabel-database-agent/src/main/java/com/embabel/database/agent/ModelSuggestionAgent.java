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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.OperationContext;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.common.ai.model.ModelMetadata;
import com.embabel.database.agent.domain.ListModelMetadata;
import com.embabel.database.agent.domain.ModelList;
import com.fasterxml.jackson.databind.ObjectMapper;

@Agent(name="ModelSuggestionAgent", description = "Suggest models based on selected providers and previously filtered tags")
public class ModelSuggestionAgent {
    
    @Autowired
    ObjectMapper objectMapper;

    @Value("${embabel.models.defaultLlm:llama3.1:8b}")
    String modelName;

    @AchievesGoal(
        description = "Generates a list of models based on user selected provider, tags and previously filtered list"
    )
    @Action
    public ModelList getModels(UserInput userInput, ListModelMetadata listModelMetadata, OperationContext operationContext) {
        String models = getModelsForProvider(userInput.getContent(),listModelMetadata);
        //build the prompt
        var prompt = """
                Format the following list of models into a human readable table with a request for the user
                to select a model.

                models = %s
                """.formatted(models);
        return operationContext.ai()
            .withLlm(modelName)
            .createObject(prompt,ModelList.class);
    }

    // @Action
    String getModelsForProvider(String provider, ListModelMetadata listModelMetadata) {
        //loop and filter by the provider
        List<String> modelNames = listModelMetadata.models()
                                    .stream()
                                    .filter(model -> provider.equalsIgnoreCase(model.getProvider()))
                                    .map(ModelMetadata::getName)
                                    .collect(Collectors.toList());
        
        return String.join(",",modelNames);
    }

}
