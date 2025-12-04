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

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.annotation.Condition;
import com.embabel.agent.api.common.OperationContext;
import com.embabel.database.agent.domain.ListModelProvider;
import com.embabel.database.agent.domain.TagList;
import com.embabel.database.core.repository.ModelRepository;
import com.embabel.database.core.repository.domain.Model;
import com.embabel.database.core.repository.domain.ModelProvider;
import com.embabel.database.core.repository.domain.Organization;
import com.embabel.database.core.repository.domain.Provider;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;

/**
 * Uses LLM as a Shim to build the model object
 */
@Agent(name="ModelParserAgent", description="Parses a JSON fragment into the Model Domain Object")
public class ModelParserAgent {

    private static final Log logger = LogFactory.getLog(ModelParserAgent.class);

    @Value("${embabel.database.agent.parser.json-to-model-prompt}")
    String jsonToModelPrompt;

    @Value("${embabel.database.agent.parser.json-to-providers-prompt}")
    String extractProviders;

    @Value("${embabel.database.agent.parser.json-to-organization-prompt}")
    String extractOrganization;

    @Value("${embabel.database.agent.parser.json-to-tags-prompt}")
    String taggingPrompt;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ModelRepository modelRepository;

    @AchievesGoal(description = "Builds a full model from the provided JSON")
    @Action(pre="model_does_not_exist")
    public Model getCompleteModel(String json, ListModelProvider listModelProvider, Organization organization, TagList tags, OperationContext operationContext) {
        //get the foundational model
        var prompt = jsonToModelPrompt.formatted(json);
        logger.info(prompt);
        Model model = operationContext.ai()
                .withAutoLlm()
                .createObject(prompt,Model.class);
        //add the rest
        model.setModelProviders(listModelProvider.modelProviders());
        model.setTags(tags.tags());
        model.setOrganization(organization);
        //return
        return model;
    }

    @Action
    public ListModelProvider getProviders(String json,OperationContext operationContext) throws Exception {
        if (!hasProvider(json)) {
            //stub and return an "empty" modelProvider
            ModelProvider modelProvider = new ModelProvider("no-id",
                    new Provider("no-id",
                            "no-provider",
                            "no-website"),
                    0.0,
                    0.0,
                    List.of("no-tags"),
                    false);
            return new ListModelProvider(List.of(modelProvider));
        } //end ifr
        //there is a provider --> proceed
        var prompt = extractProviders.formatted(json);
        logger.info(prompt);

        List<Class<? extends ModelProvider>> providerClasses = List.of(ModelProvider.class);

        return operationContext.ai()
                .withAutoLlm()
                .createObject(prompt,ListModelProvider.class);
    }

    @Action
    public Organization getOrganization(String json, OperationContext operationContext) {
        var prompt = extractOrganization.formatted(json);
        logger.info(prompt);
        //convert
        return operationContext.ai()
                .withAutoLlm()
                .createObject(prompt,Organization.class);
    }

    @Action
    public TagList getTag(String json,OperationContext operationContext) {
        var prompt = taggingPrompt.formatted(json);
        logger.info(prompt);
        //convert
        return operationContext.ai()
                .withAutoLlm()
                .createObject(prompt,TagList.class);
    }

    @Condition(name="model_does_not_exist")
    public boolean modeDoesNotExist(String json) throws Exception {
        //convert and extract the name
        Map<String,Object> map = objectMapper.readValue(json,new TypeReference<Map<String,Object>>(){});
        String modelId = map.get("model_id").toString();
        //query the repository
        Model model = modelRepository.findById(modelId);
        //check if null
        return model == null;
    }


    boolean hasProvider(String json) throws Exception {
        //convert to a map and check for providers
        Map<String,Object> map = objectMapper.readValue(json,new TypeReference<Map<String,Object>>(){});
        Object providers = map.get("providers");
        if (providers != null) {
            try {
                List<Map<String, Object>> providersList = (List<Map<String, Object>>) providers;
                if (!providersList.isEmpty()) {
                    return true;
                } //end if
            }
            catch (Exception e) {
                logger.error("error parsing providers",e);
            }

        }//end if
        return false;//not there
    }
}
