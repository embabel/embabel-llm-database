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
import java.util.stream.Stream;

import com.embabel.database.agent.domain.ModelProviders;
import com.embabel.database.core.repository.ModelRepository;
import com.embabel.database.core.repository.domain.Model;
import com.embabel.database.core.repository.domain.ModelProvider;
import com.embabel.database.core.repository.domain.Provider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.annotation.Condition;
import com.embabel.agent.api.common.OperationContext;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.common.ai.model.ModelMetadata;
import com.embabel.database.agent.domain.ListModels;
import com.embabel.database.agent.domain.TagList;

/**
 * flow
 * - user request for a model(s) suggestion based on criteria
 * - "I want a model to create an image from a prompt"
 * - "I want a model that can update an image"
 * - "I want a model that can create a narration"
 * - "I want to ask a model to summarize a document"
 * - LLM database has tags, models, providers, and prices
 * - Step 1 is to convert request to a "tag"
 * - Step 2 is to use the "tag" to collate a set of providers (group by providers)
 * - present the options to the user for a provider
 * - on provider choice, present a list of models to choose from
 */

@Agent(name="ModelProviderSuggestionAgent", description = "Suggest model providers based on user criteria")
public class ModelProviderSuggestionAgent {
    
    private static final Log logger = LogFactory.getLog(ModelProviderSuggestionAgent.class);

    private static final String NO_PROVIDER = "no-provider";
    private static final String DELIMITER = ",";

    @Value("${embabel.database.agent.suggestion.taglist-prompt}")
    String taglistPrompt;

    @Value("${embabel.database.agent.suggestion.providers-prompt}")
    String providersPrompt;

    @Autowired
    ModelRepository modelRepository;

    
    @Action
    public TagList getSuggestedTagList(UserInput userInput, OperationContext operationContext) {
        //retrieves the tags available
        logger.info("checking for tags");
        //uses an LLM to take the "criteria" from the user and build a tag option
        //convert to just a list of strings     
        List<String> tagNames = getAvailableTags();
        //set up the prompt
        var prompt = taglistPrompt.formatted(userInput.getContent(),tagNames);
        logger.info(prompt);//quick dump of the prompt
        return operationContext.ai()
                .withAutoLlm()
                .createObject(prompt,TagList.class);
    }

    //retrieve models for the specified tag(s)
    @Action(pre="have_models")
    public ListModels getModelsByTag(TagList tagList) {
        logger.info("getting models: " + tagList.toString());
        //build the search criteria
        String[] tags = tagList.tags().toArray(String[]::new);
        List<Model> models = modelRepository.findByTags(tags);
        if (models == null || models.isEmpty()) {
            logger.info("No matching models for tags " + tagList.tags());
            return null;//done
        }
        //return
        return new ListModels(models);
    }

    //group by providers for model(s)
    @AchievesGoal(
        description="Retrieves providers list for models based on criteria and sets up response"
    )
    @Action
    public ModelProviders getProviders(ListModels listModels, UserInput userInput, OperationContext operationContext) {
        logger.info("(getProviders) getting provider group");
        //group
        List<String> providers = listModels.models()
                .stream()
                .flatMap(model -> {
                    List<ModelProvider> p = model.getModelProviders();
                    return p != null ? p.stream() : Stream.<ModelProvider>empty();
                })
                .collect(Collectors.toMap(map -> map.getProvider().getId(),
                        map -> map.getProvider().getName(),
                        (existing, replacement) -> existing))
                .values()
                .stream()
                .filter(providerName -> !providerName.equalsIgnoreCase(NO_PROVIDER)) //filter out placeholder names
                .toList();
        //convert the list of providers to a comma-delimited string
        var prompt = providersPrompt.formatted(userInput.getContent(),String.join(DELIMITER,providers));
        logger.info(prompt);//quick dump of the prompt
        return operationContext.ai()
                .withAutoLlm()
            .createObject(prompt, ModelProviders.class);
    }    

    @Condition(name="have_models")
    public boolean haveModels() {
        logger.info("(haveModels) checking for models");
        return (modelRepository.count() > 0);
    }

    List<String> getAvailableTags() {
        //get all the models
        List<Model> models = modelRepository.findAll();
        //filter to get a unique list of actual tags in the repository
        //return
        return models.stream()
            .flatMap(model -> model.getTags().stream())
            .distinct()
            .collect(Collectors.toList());
    }

}
