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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.embabel.database.agent.domain.Providers;
import com.embabel.database.core.repository.ModelRepository;
import com.embabel.database.core.repository.domain.Model;
import com.embabel.database.core.repository.domain.ModelProvider;
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
import com.embabel.database.agent.domain.ListModels;
import com.embabel.database.agent.domain.TagList;
import org.springframework.transaction.annotation.Transactional;

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

    @Value("${embabel.database.agent.suggestion.taglist-prompt}")
    String taglistPrompt;

    @Value("${embabel.database.agent.suggestion.relevance-prompt}")
    String relevantPrompt;

    @Autowired
    ModelRepository modelRepository;

    //this is where the LLM does the grunt work of turning an NLP request into a category
    @Action(pre="is_relevant")
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
    @Transactional(readOnly = true)
    public ListModels getModelsByTag(TagList tagList) {
        logger.info("getting models: " + tagList.toString());
        //build the search criteria
        List<String> tags = new ArrayList<>(tagList.tags());
        List<Model> models = modelRepository.findByTags(tags);
        if (models == null || models.isEmpty()) {
            logger.info("No matching models for tags " + tagList.tags());
            return null;//done
        }

        // deep copy
        List<Model> copiedModels = models.stream()
                .map(Model::deepCopy)
                .collect(Collectors.toList());

        // return a new ListModels containing deep-copied objects
        return new ListModels(copiedModels);
    }

    //group by providers for model(s)
    @AchievesGoal(
        description="Retrieves providers list for models based on criteria and sets up response"
    )
    @Action
    public Providers getProviders(ListModels listModels, UserInput userInput, OperationContext operationContext) {
        logger.info("(getProviders) getting provider group");
        //group
        List<String> providers = listModels.models()
                .stream()
                .flatMap(model -> {
                    List<ModelProvider> p = model.getModelProviders();
                    return p.stream();
                })
                .collect(Collectors.toMap(map -> map.getProvider().getId(),
                        map -> map.getProvider().getName(),
                        (existing, replacement) -> existing))
                .values()
                .stream()
                .filter(providerName -> !providerName.equalsIgnoreCase(NO_PROVIDER)) //filter out placeholder names
                .toList();
        //convert the list of providers to a comma-delimited string
        return new Providers("Please choose your preferred provider from the following list",providers); //TODO externalize message
    }    

    @Condition(name="have_models")
    public boolean haveModels() {
        logger.info("(haveModels) checking for models");
        return (modelRepository.count() > 0);
    }

    @Condition(name="is_relevant")
    public boolean isRelevant(UserInput userInput, OperationContext operationContext) {
        if (operationContext.getProcessContext().getBlackboard().get("isRelevant") != null) {
            logger.info("using the context cache " + operationContext.getProcessContext().getBlackboard().get("isRelevant"));
            return (boolean) operationContext.getProcessContext().getBlackboard().get("isRelevant");
        } //end if
        var prompt = relevantPrompt.formatted(userInput.getContent());
        String result = operationContext
                .ai()
                .withAutoLlm()
                .createObject(prompt, String.class);
        boolean relevant = Boolean.parseBoolean(result);
        logger.info("is relevant " + result);
        //set the results for next time
        operationContext.getProcessContext()
                .getBlackboard()
                .set("isRelevant", relevant);
        return relevant;
    }

    @Transactional(readOnly = true)
    List<String> getAvailableTags() {
        //return
        return modelRepository.findAllDistinctTags();
    }




}
