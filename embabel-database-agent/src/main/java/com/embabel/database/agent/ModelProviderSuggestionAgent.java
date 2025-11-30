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

import com.embabel.database.agent.domain.ModelProviders;
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
import com.embabel.database.agent.domain.ListModelMetadata;
import com.embabel.database.agent.domain.TagList;
import com.embabel.database.agent.util.TagParser;
import com.embabel.database.core.repository.AiModelRepository;
import com.embabel.database.core.repository.LlmModelMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    // @Autowired
    TagParser tagParser;

    @Value("${embabel.database.agent.suggestion.taglist-prompt}")
    String taglistPrompt;

    @Value("${embabel.database.agent.suggestion.providers-prompt}")
    String providersPrompt;

    @Autowired
    AiModelRepository aiModelRepository;

    public ModelProviderSuggestionAgent(TagParser tagParser) {
        this.tagParser = tagParser;
    }
    
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
    public ListModelMetadata getModelsByTag(TagList tagList) {
        logger.info("getting models: " + tagList.toString());
        //build the search criteria
        String[] tags = tagList.tags().toArray(String[]::new);
        List<ModelMetadata> models = aiModelRepository.findByTags(tags);
        //TODO need to be able to short circuit here if there are no matches
        //return
        return new ListModelMetadata(models);
    }

    //group by providers for model(s)
    @AchievesGoal(
        description="Retrieves providers list for models based on criteria and sets up response"
    )
    @Action
    public ModelProviders getProviders(ListModelMetadata listModelMetadata, UserInput userInput, OperationContext operationContext) {
        logger.info("(getProviders) getting provider group");
        //group
        List<String> providers = listModelMetadata.models()
            .stream()
            .map(ModelMetadata::getProvider)
            .distinct()
            .collect(Collectors.toList());
        //convert the list of providers to a comma-delimited string
        var prompt = providersPrompt.formatted(userInput.getContent(),String.join(",",providers));
        logger.info(prompt);//quick dump of the prompt
        return operationContext.ai()
                .withAutoLlm()
            .createObject(prompt, ModelProviders.class);
    }    

    @Condition(name="have_models")
    public boolean haveModels() {
        logger.info("(haveModels) checking for models");
        return (aiModelRepository.count() > 0);
    }

    List<String> getAvailableTags() {
        //get all the models
        List<ModelMetadata> models = aiModelRepository.findAll();
        //filter to get a unique list of actual tags in the repository
        //return
        return models.stream()
            .flatMap(model -> ((LlmModelMetadata) model).getTags().stream())
            .distinct()
            .collect(Collectors.toList());
    }

}
