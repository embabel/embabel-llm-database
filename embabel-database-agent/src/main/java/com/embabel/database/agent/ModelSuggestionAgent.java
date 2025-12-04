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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.embabel.database.agent.domain.ModelList;
import com.embabel.database.agent.domain.ModelSuggestion;
import com.embabel.database.core.repository.domain.Model;
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

    @Value("${embabel.database.agent.suggestion.filter-prompt}")
    String filterPrompt;

    @Value("${embabel.models.defaultLlm:llama3.1:8b}")
    String modelName;

    @AchievesGoal(
        description = "Generates a list of models based on user selected provider, tags and previously filtered list. Using the selected provider and the model list, narrow by the description"
    )
    @Action
    public ModelSuggestion getModels(UserInput userInput, String previousPrompt, ListModels listModels, OperationContext operationContext) throws Exception {
        logger.info("inputs provider: " + userInput.getContent() + " previous: " + previousPrompt);
        //loop and filter by the provider
        List<Model> models = listModels.models()
                .stream()
                .filter(model -> model.getModelProviders()
                        .stream()
                        .anyMatch(modelProvider -> userInput.getContent()
                                .equalsIgnoreCase(modelProvider.getProvider().getName())))
                .toList();
        logger.info("filtering by Provider from " + listModels.models().size() + " to " + models.size());
        //build a model list
        List<Map<String,String>> formattedModels = models.stream()
                .map(model -> {
                    Map<String,String> map = new HashMap<>();
                    map.put("modelId",model.getId());
                    map.put("description",model.getDescription());
                    map.put("tags",String.join(",",model.getTags() != null ? model.getTags() : List.of("")));
                    return map;
                })
                .toList();
        //exit out
        if (formattedModels.isEmpty()) {
            logger.warn("no filtered models");
            return null;//
        }
        //convert to json
        String json = objectMapper.writeValueAsString(formattedModels);
        //now we have a sublist of models, get the descriptions, tags and names and ask the LLM to filter with original request
        var prompt = filterPrompt.formatted(previousPrompt,json);
        logger.info(prompt);
        //invoke
        ModelList modelList =  operationContext.ai()
                .withAutoLlm()
                .createObject(prompt, ModelList.class);

        //convert into a list of tables
        models = modelList.models()
                .stream()
                .map(map -> {
                    //use the id to retrieve from the list models
                    String modelId = map.get("modelId");
                    Model model = listModels.models()
                            .stream()
                            .filter(m -> m.getId().equalsIgnoreCase(modelId))
                            .distinct()
                            .findFirst()
                            .get();
                    return model;
                }).toList();
        //return the converted list
        return new ModelSuggestion("Here are the suggested models",new ListModels(models));
    }

}
