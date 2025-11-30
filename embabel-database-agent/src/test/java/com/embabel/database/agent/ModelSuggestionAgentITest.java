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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.embabel.agent.api.common.autonomy.AgentInvocation;
import com.embabel.database.agent.domain.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import com.embabel.agent.config.annotation.EnableAgents;
import com.embabel.agent.core.Agent;
import com.embabel.agent.core.AgentPlatform;
import com.embabel.agent.core.AgentProcess;
import com.embabel.agent.core.AgentProcessStatusCode;
import com.embabel.agent.core.ProcessOptions;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.database.agent.service.AiRepositoryModelMetadataValidationService;
import com.embabel.database.agent.service.LlmLeaderboardModelMetadataDiscoveryService;
import com.embabel.database.agent.service.ModelMetadataDiscoveryService;
import com.embabel.database.agent.service.ModelMetadataService;
import com.embabel.database.agent.service.ModelMetadataValidationService;
import com.embabel.database.agent.util.LlmLeaderboardParser;
import com.embabel.database.agent.util.LlmLeaderboardTagParser;
import com.embabel.database.agent.util.ModelMetadataParser;
import com.embabel.database.agent.util.TagParser;
import com.embabel.database.core.repository.AiModelRepository;
import com.embabel.database.core.repository.InMemoryAiModelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes={ModelSuggestionAgentITest.class, AgentConfigurationSupport.class}, properties = {
        "spring.ai.bedrock.aws.region=us-east-1",
        "spring.ai.model.chat=ollama",
        "aws.accessKeyId=key",
        "aws.secretAccessKey=secret"
})
public class ModelSuggestionAgentITest {

    private static final Log logger = LogFactory.getLog(ModelSuggestionAgentITest.class);

    @Autowired
    AgentPlatform agentPlatform;

    @Test
    void testGetSuggestedTagList() throws Exception {
        // String userInputText = "find a model that can extract text from an image";
        String userInputText = "I want a model that will create an image from text";
        UserInput userInput = new UserInput(userInputText);

        //get the invocation
        AgentInvocation<ModelProviders> modelProvidersAgentInvocation = AgentInvocation.builder(agentPlatform).build(ModelProviders.class);
        //invoke
        AgentProcess agentProcess = modelProvidersAgentInvocation.run(Collections.singletonMap("userInput",userInput));
        while (!agentProcess.getFinished()) {
//
//        while (agentProcess.getStatus().equals(AgentProcessStatusCode.RUNNING) || agentProcess.getStatus().equals(AgentProcessStatusCode.WAITING)) {
//            //still waiting
            Thread.sleep(500);//wait 500ms
        }//end while

        Object result = agentProcess.lastResult();
        assertNotNull(result);
        ModelProviders modelProviders = (ModelProviders) result;
        logger.info(modelProviders.providers());

        ListModelMetadata listModelMetadata = agentProcess.getProcessContext().getBlackboard().last(ListModelMetadata.class);

        assertNotNull(listModelMetadata);

        ProcessOptions processOptions = ProcessOptions.builder().
                blackboard(agentProcess.getProcessContext()
                        .getBlackboard())
                .build();

        //invoke the second agent with the same context id
        AgentInvocation<ModelSuggestion> modelSuggestionAgentInvocation = AgentInvocation.builder(agentPlatform)
                .options(processOptions)
                .build(ModelSuggestion.class);

        userInput = new UserInput("deepinfra");

//        CompletableFuture<ModelSuggestion> modelSuggestionCompletableFuture = modelSuggestionAgentInvocation.invokeAsync(Collections.singletonMap("userInput",userInput));
        ModelSuggestion modelSuggestion = modelSuggestionAgentInvocation.invoke(Collections.singletonMap("userInput",userInput));

        logger.info("final message " + modelSuggestion.message());
        logger.info("final suggestion " + modelSuggestion.listModelMetadata());


//        modelSuggestionCompletableFuture.thenAccept(modelSuggestion -> {
//            logger.info("final message " + modelSuggestion.message());
//            logger.info("final suggestion " + modelSuggestion.listModelMetadata());
//        });

//        ModelProviders providers = modelProvidersAgentInvocation.invoke(Collections.singletonMap("userInput",userInput));
        //now that we have the providers, invoke the second model
//        AgentInvocation<ModelSuggestion> modelSuggestionAgentInvocation = AgentInvocation.builder(agentPlatform).build(ModelSuggestion.class);
        //setup the context


//
//        //get the agent execution environment
//        AgentPlatform agentFactory = applicationContext.getBean(AgentPlatform.class);
//        //get the agent
//        Agent agent = null;
//        List<Agent> agents = agentFactory.agents();
//        for (Agent a : agents) {
//            logger.info("Agent Name: " + a.getName());
//            if (a.getName().equals("ModelProviderSuggestionAgent")) {
//                logger.info("Matched " + a.getName());
//                //this is the one
//                agent = a;
//                break;
//            } //end if
//        } //end for
//        assertNotNull(agent);
//        //load up the repository for testing
//        InMemoryAiModelRepository repository = applicationContext.getBean(InMemoryAiModelRepository.class);
//        repository.load();//invoke the load
//        //execute
//        AgentProcess agentProcess = agentFactory.createAgentProcess(agent, ProcessOptions.DEFAULT, Collections.singletonMap("userInput", userInput));
//        //get the id
//        String id = agentProcess.getId();
//        logger.info(id);
//        //invoke
//        agentProcess.run();
//        //check
//        while (agentProcess.getStatus().equals(AgentProcessStatusCode.RUNNING) || agentProcess.getStatus().equals(AgentProcessStatusCode.WAITING)) {
//            //still waiting
//            Thread.sleep(500);//wait 500ms
//        }//end while
//        //use getObjects to get the history
//        Object result = agentProcess.lastResult();
//        assertNotNull(result);
//        logger.info(result);
//        //should be able to get the history and invoke a second agent
//        Optional<Object> list = agentProcess.spawn()
//            .getObjects()
//            .stream()
//            .filter(obj -> obj.getClass().isAssignableFrom(ListModelMetadata.class))
//            .findFirst();
//        assertTrue(list.isPresent());
//        //now pull the model list from the prompt
//        ListModelMetadata listModelMetadata = (ListModelMetadata) list.get();
//        assertNotNull(listModelMetadata);
//        //can reuse this in the next prompt --> lets use 'deepinfra'
//        String selectedProvider = "deepinfra";
//        //check its there
//        assertTrue(result.toString().contains(selectedProvider));
//        //update the user input with the full context
//        StringBuilder stringBuilder = new StringBuilder();
//        //now use this to invoke the ModelSuggestionAgent
//        for (Agent a : agents) {
//            logger.info("Agent Name: " + a.getName());
//            if (a.getName().equals("ModelSuggestionAgent")) {
//                logger.info("Matched " + a.getName());
//                //this is the one
//                agent = a;
//                break;
//            } //end if
//        } //end for
//        userInput = new UserInput(selectedProvider);
//        //build the context
//        Map<String,Object> contextMap = new HashMap<>();
//        contextMap.put("userInput",userInput);
//        contextMap.put("listModelMetadata",listModelMetadata);
//        agentProcess = agentFactory.createAgentProcess(agent, ProcessOptions.DEFAULT, contextMap);
//        //get the id
//        id = agentProcess.getId();
//        logger.info(id);
//        //invoke
//        agentProcess.run();
//        //check
//        while (agentProcess.getStatus().equals(AgentProcessStatusCode.RUNNING) || agentProcess.getStatus().equals(AgentProcessStatusCode.WAITING)) {
//            //still waiting
//            Thread.sleep(500);//wait 500ms
//        }//end while
//        result = agentProcess.lastResult();
//        assertNotNull(result);
//        logger.info(result);
    }


}
