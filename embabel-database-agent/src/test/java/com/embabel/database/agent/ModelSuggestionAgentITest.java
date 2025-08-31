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

import java.util.Collections;
import java.util.List;

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
import com.embabel.database.agent.domain.ProviderList;
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

@SpringBootTest(classes={ModelSuggestionAgentITest.class,ModelSuggestionAgentITest.TestConfig.class})
@ActiveProfiles("ollama")
public class ModelSuggestionAgentITest {

    private static final Log logger = LogFactory.getLog(ModelSuggestionAgentITest.class);
    
    @Autowired
    ApplicationContext applicationContext;

    @Test
    void testGetSuggestedTagList() throws Exception {
        String userInputText = "find a model that can extract text from an image";
        UserInput userInput = new UserInput(userInputText);
        //get the agent execution environment
        AgentPlatform agentFactory = applicationContext.getBean(AgentPlatform.class);
        //get the agent
        Agent agent = null;
        List<Agent> agents = agentFactory.agents();        
        for (Agent a : agents) {
            logger.info("Agent Name: " + a.getName());
            if (a.getName().equals("ModelSuggestionAgent")) {
                logger.info(a.getName());
                //this is the one
                agent = a;
                break;
            } //end if
        } //end for
        assertNotNull(agent);
        //load up the repository for testing
        InMemoryAiModelRepository repository = applicationContext.getBean(InMemoryAiModelRepository.class);
        repository.load();//invoke the load
        //execute
        ProcessOptions processOptions = ProcessOptions.getDEFAULT();
        AgentProcess agentProcess = agentFactory.createAgentProcess(agent, processOptions, Collections.singletonMap("userInput", userInput));
        //get the id
        String id = agentProcess.getId();
        logger.info(id);
        //invoke
        agentProcess.run();
        //check
        while (agentProcess.getStatus().equals(AgentProcessStatusCode.RUNNING) || agentProcess.getStatus().equals(AgentProcessStatusCode.WAITING)) {
            //still waiting
            Thread.sleep(500);//wait 500ms            
        }//end while        

        //use getObjects to get the history

        Object result = agentProcess.lastResult();
        assertNotNull(result);
        ProviderList providerList = (ProviderList) result;
        assertNotNull(providerList.providers());
        assertFalse(providerList.providers().isEmpty());
        logger.info(providerList.providers());
    }

    @TestConfiguration
    @EnableAgents
    public static class TestConfig {
      
        @Bean
        public ModelSuggestionAgent modelSuggestionAgent() {
            return new ModelSuggestionAgent();
        }

        /*
         * Setup Below is to support the AiModelRepositoryAgent
         */
        @Bean
        public AiModelRepository aiModelRepository() {
            return new InMemoryAiModelRepository();
        }        

        @Bean
        public AiModelRepositoryAgent aiModelRepositoryAgent() {
            return new AiModelRepositoryAgent();
        }

        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }

        @Bean
        public ModelMetadataParser modelMetadataParser(ObjectMapper objectMapper,TagParser categoryParser) {
            return new LlmLeaderboardParser(objectMapper,categoryParser);
        }

        @Bean
        public ModelMetadataDiscoveryService modelMetadataDiscoveryService(ModelMetadataParser modelMetadataParser) {
            return new LlmLeaderboardModelMetadataDiscoveryService(modelMetadataParser);
        }

        @Bean
        public ModelMetadataService modelMetadataService() {
            return new ModelMetadataService();
        }

        @Bean
        public ModelMetadataValidationService modelMetadataValidationService(AiModelRepository aiModelRepository) {
            return new AiRepositoryModelMetadataValidationService(aiModelRepository);
        }

        @Bean
        public TagParser llmLeaderboardCategoryParser() {
            return new LlmLeaderboardTagParser();
        }

    }

}
