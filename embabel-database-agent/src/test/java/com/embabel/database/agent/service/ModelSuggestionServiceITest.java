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
package com.embabel.database.agent.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import com.embabel.agent.api.common.autonomy.AgentInvocation;
import com.embabel.agent.core.AgentPlatform;
import com.embabel.database.agent.*;
import com.embabel.database.agent.domain.ModelSuggestion;
import com.embabel.database.agent.domain.SessionContext;
import com.embabel.database.core.repository.ModelRepository;
import com.embabel.database.core.repository.util.ModelRepositoryLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(classes={ModelSuggestionServiceITest.class, AgentConfigurationSupport.class}, properties = {
        "spring.ai.bedrock.aws.region=us-east-1",
        "spring.ai.model.chat=ollama",
        "embabel.models.default-llm=us.anthropic.claude-3-5-sonnet-20240620-v1:0"
})
public class ModelSuggestionServiceITest {

    private static final Log logger = LogFactory.getLog(ModelSuggestionServiceITest.class);

    @Autowired
    ModelSuggestionService modelSuggestionService;

    @Autowired
    AgentPlatform agentPlatform;

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    ModelRepositoryLoader modelRepositoryLoader;

    @BeforeEach
    void before() {
        modelRepository.deleteAll();
        //load
        modelRepositoryLoader.loadFromFile("./json/export.json");
    }

    @Test
    void testFlow() throws Exception {
        //load up the repository for testing
        // String userInputText = "find a model that can extract text from an image";
        // String userInputText = "I want a model that will create an image from text";      
        String userInputText = "I want a model that can create images";
        //invoke the service
        SessionContext sessionContext = modelSuggestionService.getProviderSuggestions(userInputText);
        assertNotNull(sessionContext);
        //test second invocation
        ModelSuggestion modelSuggestion = modelSuggestionService.getModelSuggestion("xAi",sessionContext.sessionid());
        assertNotNull(modelSuggestion);
        logger.info(modelSuggestion);
    }

}
