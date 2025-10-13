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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.embabel.common.ai.model.PerTokenPricingModel;
import com.embabel.common.ai.model.PricingModel;
import com.embabel.database.agent.domain.ProviderOptions;
import com.embabel.database.agent.domain.ListModelMetadata;
import com.embabel.database.agent.domain.ProviderList;
import com.embabel.database.agent.domain.TagList;
import com.embabel.database.agent.util.TagParser;
import com.embabel.database.core.repository.AiModelRepository;
import com.embabel.database.core.repository.LlmModelMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;

// record TagList(List<String> tags) { }

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
    
    private static Log logger = LogFactory.getLog(ModelProviderSuggestionAgent.class);

    // @Autowired
    TagParser tagParser;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AiModelRepository aiModelRepository;

    @Value("${embabel.models.defaultLlm:llama3.1:8b}")
    String modelName;

    public ModelProviderSuggestionAgent(TagParser tagParser) {
        this.tagParser = tagParser;
    }
    
    @Action
    public TagList getSuggestedTagList(UserInput userInput, OperationContext operationContext) {
        //retrieves the tags available
        logger.info("checking for tags");
        //uses an LLM to take the "criteria" from the user and build a tag option
        // List<Map<String,Object>> tags = tagParser.getTasks(objectMapper,TagParser.RESOURCE_LOCATION);
        

        //convert to just a list of strings     
        // List<String> tagNames = tags.stream()
        //     .map(map -> (String) map.get("tag"))
        //     .collect(Collectors.toList());   
        List<String> tagNames = getAvailableTags();
       
        //set up the prompt
        var prompt = """
                Review the following request from the user and return a list of tag names that meet
                the users requested criteria. Respond only with a list of tags.

                Criteria = %s

                Tag Options = %s
                """.formatted(userInput.getContent(),tagNames);
        logger.info(prompt);//quick dump of the prompot
        return operationContext.ai()
            .withLlm(modelName)
            .createObject(prompt, TagList.class);
    }

    //retrieve models for the specified tag(s)
    @Action(pre="have_models")
    public ListModelMetadata getModelsByTag(TagList tagList) {
        logger.info("getting models: " + tagList.toString());
        //build the search criteria
        String[] tags = tagList.tags().toArray(new String[tagList.tags().size()]);
        logger.info("tags passed " + String.join(", ",tags));
        List<ModelMetadata> models = aiModelRepository.findByTags(tags);
        logger.info("models is null: " + (models == null));
        logger.info("models is empty: " + (models != null ? models.isEmpty() : "null"));
        //TODO need to be able to short circuit here if there are no matches
        //return
        return new ListModelMetadata(models);
    }

    //group by providers for model(s)
    @AchievesGoal(
        description="Retrieves providers list for models based on criteria and sets up response"
    )
    @Action
    public String getProviders(ListModelMetadata listModelMetadata,UserInput userInput, OperationContext operationContext) {
        logger.info("(getProviders) getting provider group");
        //group
        List<String> providers = listModelMetadata.models()
            .stream()
            .map(ModelMetadata::getProvider)
            .distinct()
            .collect(Collectors.toList());
        //convert the list of provdiers to a comma-delimited string        
        var prompt = """
            Review the following request from the user and the list of providers and respond with a request
            for the user to select a single provider.  Do not elaborate on the providers, just provide them
            as a list.

            Criteria = %s

            Providers = %s
        """.formatted(userInput.getContent(),String.join(",",providers));
        //return
        logger.info(prompt);//quick dump of the prompot
        return operationContext.ai()
            .withLlm(modelName)
            .createObject(prompt, String.class);
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
