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

import com.embabel.database.agent.AiModelRepositoryAgent
import com.embabel.database.agent.service.AiRepositoryModelMetadataValidationService
import com.embabel.database.agent.service.LlmLeaderboardModelMetadataDiscoveryService
import com.embabel.database.agent.service.ModelMetadataService
import com.embabel.database.agent.service.ModelMetadataDiscoveryService
import com.embabel.database.agent.service.ModelMetadataValidationService
import com.embabel.database.agent.util.LlmLeaderboardParser
import com.embabel.database.agent.util.LlmLeaderboardTaskParser
import com.embabel.database.agent.util.ModelMetadataParser
import com.embabel.database.agent.util.TaskParser
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

    //task parser
    @Bean
    fun taskParser(): TaskParser {
        return LlmLeaderboardTaskParser()
    }

    //model parser
    @Bean
    fun modelMetadataParser(objectMapper: ObjectMapper, taskParser: TaskParser): ModelMetadataParser {
        return LlmLeaderboardParser(objectMapper, taskParser)
    }

    //discovery service
    @Bean
    fun modelMetadataDiscoveryService(modelMetadataParser: ModelMetadataParser): ModelMetadataDiscoveryService {
        return LlmLeaderboardModelMetadataDiscoveryService(modelMetadataParser)
    }

    //model loader action
    @Bean
    fun modelLoader(): InMemoryAiModelRepositoryLoader {
        return InMemoryAiModelRepositoryLoader()
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
}
