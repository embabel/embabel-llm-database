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
package com.embabel.database.server.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.beans.factory.annotation.Qualifier

import com.embabel.database.agent.AiModelRepositoryAgent
import com.embabel.database.agent.ModelProviderSuggestionAgent
import com.embabel.database.agent.ModelSuggestionAgent
import com.embabel.database.agent.service.AgentManagementService;
import com.embabel.database.agent.service.AiRepositoryModelMetadataValidationService
import com.embabel.database.agent.service.LlmLeaderboardModelMetadataDiscoveryService
import com.embabel.database.agent.service.InMemorySessionManagementService;
import com.embabel.database.agent.service.ModelMetadataService
import com.embabel.database.agent.service.ModelMetadataDiscoveryService
import com.embabel.database.agent.service.ModelMetadataValidationService
import com.embabel.database.agent.service.ModelSuggestionService;
import com.embabel.database.agent.service.SessionManagementService;
import com.embabel.database.agent.util.LlmLeaderboardParser
import com.embabel.database.agent.util.LlmLeaderboardTagParser
import com.embabel.database.agent.util.ModelMetadataParser
import com.embabel.database.agent.util.TagParser
import com.embabel.database.core.repository.AiModelRepository
import com.embabel.database.core.repository.InMemoryAiModelRepository
import com.embabel.database.core.repository.util.InMemoryAiModelRepositoryLoader
import com.fasterxml.jackson.databind.ObjectMapper

@Configuration
class DefaultConfig {

    //repository
    @Bean
    fun aiModelRepository(): AiModelRepository {
        return InMemoryAiModelRepository()
    }

    //agent
    @Bean
    fun aiModelRepositoryAgent(): AiModelRepositoryAgent {
        return AiModelRepositoryAgent()
    }

    //tag parser
    @Bean
    fun tagParser(): TagParser {
        return LlmLeaderboardTagParser()
    }

    //model parser
    @Bean
    fun modelMetadataParser(objectMapper: ObjectMapper, @Qualifier("tagParser") taskParser: TagParser): ModelMetadataParser {
        return LlmLeaderboardParser(objectMapper, taskParser)
    }

    //discovery service
    @Bean
    fun modelMetadataDiscoveryService(modelMetadataParser: ModelMetadataParser): ModelMetadataDiscoveryService {
        return LlmLeaderboardModelMetadataDiscoveryService(modelMetadataParser)
    }

    //model loader action
    @Bean
    fun modelLoader(env: Environment): InMemoryAiModelRepositoryLoader {
        return InMemoryAiModelRepositoryLoader(env)
    }

    //instantiate coordination service
    @Bean
    fun modelMetadataService(): ModelMetadataService {
        return ModelMetadataService()
    }

    //instantiate instance of single service
    @Bean
    fun modelMetadataValidationService(aiModelRepository: AiModelRepository): ModelMetadataValidationService {
        return AiRepositoryModelMetadataValidationService(aiModelRepository)
    }

    @Bean
    fun modelProviderSuggestionAgent(@Qualifier("tagParser") tagParser: TagParser): ModelProviderSuggestionAgent {
        return ModelProviderSuggestionAgent(tagParser)
    }

    @Bean
    fun modelSuggestionAgent(): ModelSuggestionAgent {
        return ModelSuggestionAgent()
    }

    @Bean
    fun modelSuggestionService(): ModelSuggestionService {
        return ModelSuggestionService();
    }

    @Bean
    fun agentManagementService(): AgentManagementService {
        return AgentManagementService();
    }

    @Bean
    fun sessionManagementService(): SessionManagementService {
        return InMemorySessionManagementService();
    }
}
