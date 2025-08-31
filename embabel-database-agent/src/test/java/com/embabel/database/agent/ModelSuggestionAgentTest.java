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

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.embabel.agent.domain.io.UserInput;
import com.embabel.agent.testing.unit.FakeOperationContext;
import com.embabel.database.agent.util.LlmLeaderboardTagParser;
import com.embabel.database.agent.util.TagParser;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ModelSuggestionAgentTest {
    
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

}
