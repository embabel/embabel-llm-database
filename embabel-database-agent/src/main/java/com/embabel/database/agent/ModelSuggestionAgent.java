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
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.OperationContext;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.common.ai.model.PerTokenPricingModel;
import com.embabel.common.ai.model.PricingModel;
import com.embabel.database.agent.util.TagParser;
import com.fasterxml.jackson.databind.ObjectMapper;

record TagList(List<String> tags) { }

@Agent(name="ModelSuggestionAgent", description = "Suggest models based on user criteria")
public class ModelSuggestionAgent {
    
    private static Log logger = LogFactory.getLog(ModelSuggestionAgent.class);

    @Autowired
    TagParser tagParser;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${embabel.models.defaultLlm:llama3.1:8b}")
    String modelName;

    @AchievesGoal(
        description="Retrieves model suggestions based on the entered criteria"
    )
    @Action
    public ListModelMetadata getModelSuggestions(TagList tags, OperationContext operationContext) {

        var prompt = """
                Review the following request from the user and return a list of suggested models.  
                """;

        return null;
    }


    @Action
    public TagList getSuggestedTagList(UserInput userInput, OperationContext operationContext) {
        //retrieves the tags available
        //uses an LLM to take the "criteria" from the user and build a tag option
        List<Map<String,Object>> tags = tagParser.getTasks(objectMapper,TagParser.RESOURCE_LOCATION);
        //convert to just a list of strings     
        List<String> tagNames = tags.stream()
            .map(map -> (String) map.get("tag"))
            .collect(Collectors.toList());   
        //set up the prompt
        var prompt = """
                Review the following request from the user and return a list of tag names that meet
                the users requested criteria. Respond only with a list of tags.

                Criteria = %s

                Tag Options = %s
                """.formatted(userInput.getContent(),tagNames);
        logger.debug(prompt);//quick dump of the prompot
        return operationContext.ai().withLlm(modelName).createObject(prompt, TagList.class);
    }
}
