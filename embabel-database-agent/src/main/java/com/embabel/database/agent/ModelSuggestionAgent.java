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

import com.embabel.database.agent.domain.ModelSuggestion;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.OperationContext;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.common.ai.model.ModelMetadata;
import com.embabel.database.agent.domain.ListModels;
import com.fasterxml.jackson.databind.ObjectMapper;

@Agent(name="ModelSuggestionAgent", description = "Suggest models based on selected providers and previously filtered tags")
public class ModelSuggestionAgent {

    private static final Log logger = LogFactory.getLog(ModelSuggestionAgent.class);

    @Autowired
    ObjectMapper objectMapper;

    @Value("${embabel.database.agent.suggestion.format-prompt}")
    String formatPrompt;

    @Value("${embabel.models.defaultLlm:llama3.1:8b}")
    String modelName;

    @AchievesGoal(
        description = "Generates a list of models based on user selected provider, tags and previously filtered list"
    )
    @Action
    public ModelSuggestion getModels(UserInput userInput, ListModels listModels, OperationContext operationContext) {
        String models = getModelsForProvider(userInput.getContent(), listModels);
        //build the prompt
        var prompt = formatPrompt.formatted(models);
        logger.info(prompt);
        return operationContext.ai()
                .withAutoLlm()
                .createObject(prompt,ModelSuggestion.class);
    }

    // @Action
    String getModelsForProvider(String provider, ListModels listModels) {
        //loop and filter by the provider
        List<String> modelNames = listModels.models()
                .stream()
                .flatMap(model -> model.getModelProviders().stream())
                .filter(modelProvider -> provider.equalsIgnoreCase(modelProvider.getProvider().getName()))
                .map(modelProvider -> modelProvider.getProvider().getName())
                .distinct()
                .collect(Collectors.toList());
        
        return String.join(",",modelNames);
    }

}
