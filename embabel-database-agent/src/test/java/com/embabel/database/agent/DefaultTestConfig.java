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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Qualifier;

import com.embabel.agent.config.annotation.EnableAgentMcpServer;
import com.embabel.agent.config.annotation.EnableAgents;
import com.embabel.database.agent.AiModelRepositoryAgent;
import com.embabel.database.agent.ModelProviderSuggestionAgent;
import com.embabel.database.agent.ModelSuggestionAgent;
import com.embabel.database.agent.service.AiRepositoryModelMetadataValidationService;
import com.embabel.database.agent.service.LlmLeaderboardModelMetadataDiscoveryService;
import com.embabel.database.agent.service.ModelMetadataService;
import com.embabel.database.agent.service.ModelMetadataDiscoveryService;
import com.embabel.database.agent.service.ModelMetadataValidationService;
import com.embabel.database.agent.service.ModelSuggestionService;
import com.embabel.database.agent.util.LlmLeaderboardParser;
import com.embabel.database.agent.util.LlmLeaderboardTagParser;
import com.embabel.database.agent.util.ModelMetadataParser;
import com.embabel.database.agent.util.TagParser;
import com.embabel.database.core.repository.AiModelRepository;
import com.embabel.database.core.repository.InMemoryAiModelRepository;
import com.embabel.database.core.repository.util.InMemoryAiModelRepositoryLoader;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableAgents
@EnableAgentMcpServer
public class DefaultTestConfig {

    
    // repository
    @Bean
    public AiModelRepository aiModelRepository() {
        return new InMemoryAiModelRepository();
    }

    // agent
    @Bean
    public AiModelRepositoryAgent aiModelRepositoryAgent() {
        return new AiModelRepositoryAgent();
    }

    // tag parser
    @Bean
    public TagParser tagParser() {
        return new LlmLeaderboardTagParser();
    }

    // model parser
    @Bean
    public ModelMetadataParser modelMetadataParser(ObjectMapper objectMapper,
                                                   @Qualifier("tagParser") TagParser taskParser) {
        return new LlmLeaderboardParser(objectMapper, taskParser);
    }

    // discovery service
    @Bean
    public ModelMetadataDiscoveryService modelMetadataDiscoveryService(ModelMetadataParser modelMetadataParser) {
        return new LlmLeaderboardModelMetadataDiscoveryService(modelMetadataParser);
    }

    // model loader action
    @Bean
    public InMemoryAiModelRepositoryLoader modelLoader(Environment env) {
        return new InMemoryAiModelRepositoryLoader(env);
    }

    // coordination service
    @Bean
    public ModelMetadataService modelMetadataService() {
        return new ModelMetadataService();
    }

    // validation service
    @Bean
    public ModelMetadataValidationService modelMetadataValidationService(AiModelRepository aiModelRepository) {
        return new AiRepositoryModelMetadataValidationService(aiModelRepository);
    }

    // suggestion agent
    @Bean
    public ModelProviderSuggestionAgent modelProviderSuggestionAgent(@Qualifier("tagParser") TagParser tagParser) {
        return new ModelProviderSuggestionAgent(tagParser);
    }

    @Bean
    public ModelSuggestionAgent modelSuggestionAgent() {
        return new ModelSuggestionAgent();
    }

    @Bean
    public ModelSuggestionService modelSuggestionService() {
        return new ModelSuggestionService();
    }
}
