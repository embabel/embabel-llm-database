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

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.embabel.agent.config.annotation.EnableAgentMcpServer;
import com.embabel.agent.config.annotation.EnableAgents;
import com.embabel.database.agent.AiModelRepositoryAgent;
import com.embabel.database.agent.DefaultTestConfig;
import com.embabel.database.agent.ModelProviderSuggestionAgent;
import com.embabel.database.agent.ModelSuggestionAgent;
import com.embabel.database.agent.ModelSuggestionAgentITest;
import com.embabel.database.agent.util.LlmLeaderboardParser;
import com.embabel.database.agent.util.LlmLeaderboardTagParser;
import com.embabel.database.agent.util.ModelMetadataParser;
import com.embabel.database.agent.util.TagParser;
import com.embabel.database.core.repository.AiModelRepository;
import com.embabel.database.core.repository.InMemoryAiModelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes={ModelSuggestionServiceITest.class,DefaultTestConfig.class})
// @SpringBootTest
// @Import(DefaultTestConfig.class)
@ActiveProfiles("ollama")
public class ModelSuggestionServiceITest {

    private static final Log logger = LogFactory.getLog(ModelSuggestionServiceITest.class);

    @Autowired
    ModelSuggestionService modelSuggestionService;

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void testGetProviderSuggestions() throws Exception {
        //load up the repository for testing
        InMemoryAiModelRepository repository = applicationContext.getBean(InMemoryAiModelRepository.class);
        repository.load();//invoke the load
        //setup the query
        // String userInputText = "find a model that can extract text from an image";  
        // String userInputText = "I want a model that will create an image from text";      
        String userInputText = "I want a model that can create images";
        //invoke the service
        String sessionId = modelSuggestionService.getProviderSuggestions(userInputText);
        assertNotNull(sessionId);
        Map<String,Object> results = null;
        //wait for complete
        while (true) {
            results = modelSuggestionService.getResponse(sessionId);
            if (results != null) {
                break;//end loop
            } //end if
            //wait again
            Thread.sleep(200);
        } //end while
        //get the sessionid
        assertNotNull(results.get("result"));
    }

    // @TestConfiguration
    // @EnableAgents
    public static class TestConfig {
      
        @Bean
        public ModelSuggestionService modelSuggestionService() {
            return new ModelSuggestionService();
        }

        @Bean
        public ModelProviderSuggestionAgent modelProviderSuggestionAgent(TagParser tagParser) {
            return new ModelProviderSuggestionAgent(tagParser);
        }

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
