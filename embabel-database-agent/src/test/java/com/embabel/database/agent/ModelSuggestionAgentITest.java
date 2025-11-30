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

import java.util.Collections;
import java.util.Map;

import com.embabel.agent.api.common.autonomy.AgentInvocation;
import com.embabel.database.agent.domain.*;
import com.embabel.database.core.repository.ModelRepository;
import com.embabel.database.core.repository.util.ModelRepositoryLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.embabel.agent.core.AgentPlatform;
import com.embabel.agent.core.AgentProcess;
import com.embabel.agent.core.ProcessOptions;
import com.embabel.agent.domain.io.UserInput;

@SpringBootTest(classes={ModelSuggestionAgentITest.class, AgentConfigurationSupport.class}, properties = {
        "spring.ai.bedrock.aws.region=us-east-1",
        "spring.ai.model.chat=ollama",
        "embabel.models.default-llm=us.anthropic.claude-3-5-sonnet-20240620-v1:0"
})
public class ModelSuggestionAgentITest {

    private static final Log logger = LogFactory.getLog(ModelSuggestionAgentITest.class);

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
    void testGetSuggestedTagList() throws Exception {
        // String userInputText = "find a model that can extract text from an image";
        String userInputText = "I want a model that will transcribe video";
        UserInput userInput = new UserInput(userInputText);

        //get the invocation
        AgentInvocation<ModelProviders> modelProvidersAgentInvocation = AgentInvocation.builder(agentPlatform).build(ModelProviders.class);
        //invoke
        AgentProcess agentProcess = modelProvidersAgentInvocation.run(Collections.singletonMap("userInput",userInput));
        while (!agentProcess.getFinished()) {
            //wait
            Thread.sleep(500);//wait 500ms
        }//end while

        Object result = agentProcess.lastResult();
        assertNotNull(result);
        ModelProviders modelProviders = (ModelProviders) result;
        logger.info(modelProviders.providers());

        ListModels listModels = agentProcess.getProcessContext().getBlackboard().last(ListModels.class);

        assertNotNull(listModels);

        ProcessOptions processOptions = ProcessOptions.builder().
                blackboard(agentProcess.getProcessContext()
                        .getBlackboard())
                .build();

        //invoke the second agent with the same context id
        AgentInvocation<ModelSuggestion> modelSuggestionAgentInvocation = AgentInvocation.builder(agentPlatform)
                .options(processOptions)
                .build(ModelSuggestion.class);

        userInput = new UserInput("deepinfra");

        ModelSuggestion modelSuggestion = modelSuggestionAgentInvocation.invoke(Map.of("userInput",userInput,"previousPrompt",userInputText));

        logger.info("final message " + modelSuggestion.message());
        logger.info("final suggestion " + modelSuggestion.listModels());

    }


}
