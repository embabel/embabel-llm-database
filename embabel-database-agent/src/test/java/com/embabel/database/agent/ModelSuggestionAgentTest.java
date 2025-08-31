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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.embabel.agent.domain.io.UserInput;
import com.embabel.agent.testing.unit.FakeOperationContext;
import com.embabel.common.ai.model.ModelMetadata;
import com.embabel.common.ai.model.PerTokenPricingModel;
import com.embabel.common.ai.model.PricingModel;
import com.embabel.database.agent.util.LlmLeaderboardTagParser;
import com.embabel.database.agent.util.TagParser;
import com.embabel.database.core.repository.LlmModelMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ModelSuggestionAgentTest {

    private static Log logger = LogFactory.getLog(ModelSuggestionAgentTest.class);
    
    @Test
    void testGetSuggestedTagList() throws Exception {
        //setup the objects
        ObjectMapper objectMapper = new ObjectMapper();
        ModelSuggestionAgent modelSuggestionAgent = new ModelSuggestionAgent();
        TagParser tagParser = new LlmLeaderboardTagParser();
        ReflectionTestUtils.setField(modelSuggestionAgent, "objectMapper", objectMapper);
        ReflectionTestUtils.setField(modelSuggestionAgent, "tagParser", tagParser);
        ReflectionTestUtils.setField(modelSuggestionAgent, "modelName", "llama3.1:8b");

        List<String> expectedTags = Collections.singletonList("image-to-text");

        FakeOperationContext operationContext = new FakeOperationContext();
        operationContext.expectResponse(new TagList(expectedTags));
        
        UserInput userInput = new UserInput("find a model that can extract text from an image");
        //invoke
        modelSuggestionAgent.getSuggestedTagList(userInput,operationContext);
    }

    @Test
    void testConvertModelList() throws Exception {
        //create a model
        LocalDate dateStamp = LocalDate.now();
        LocalDateTime matchTime = LocalDateTime.now();
        String modelName = "model-0";
        String providerName = "provider-0";        
        PricingModel pricingModel = new PerTokenPricingModel(0.1, 0.25);
        LlmModelMetadata singleModel = new LlmModelMetadata(UUID.randomUUID().toString(),modelName, providerName, dateStamp, pricingModel, 1l,Collections.singletonList("task"),"test",0l,"modelName");
        List<ModelMetadata> list = Collections.singletonList(singleModel);
        //wrap
        ListModelMetadata listModelMetadata = new ListModelMetadata(list);
        //now 'lighten'
        LiteModelList liteList = new ModelSuggestionAgent().convertModelList(listModelMetadata);
        assertNotNull(liteList.models());
        assertTrue(liteList.models().size() == 1);
    }
}
