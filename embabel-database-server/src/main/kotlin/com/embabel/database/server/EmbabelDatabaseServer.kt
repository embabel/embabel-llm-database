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
package com.embabel.database.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableAsync

import com.embabel.agent.config.annotation.EnableAgents
import com.embabel.agent.config.annotation.EnableAgentMcpServer
import com.embabel.agent.config.annotation.LocalModels
import com.embabel.database.agent.AiModelRepositoryAgent
import com.embabel.database.agent.service.AiRepositoryModelMetadataValidationService
import com.embabel.database.agent.service.LlmLeaderboardModelMetadataDiscoveryService
import com.embabel.database.agent.service.ModelMetadataDiscoveryService
import com.embabel.database.agent.service.ModelMetadataValidationService
import com.embabel.database.agent.util.LlmLeaderboardParser
import com.embabel.database.agent.util.ModelMetadataParser
import com.embabel.database.core.repository.AiModelRepository
import com.embabel.database.core.repository.InMemoryAiModelRepository
import com.fasterxml.jackson.databind.ObjectMapper

@EnableAgents
@EnableAgentMcpServer
@EnableAsync
@SpringBootApplication
class EmbabelDatabaseServer {

    @Bean
    fun aiModelRepository(): AiModelRepository {
        return InMemoryAiModelRepository()
    }

    @Bean
    fun aiModelRepositoryAgent(): AiModelRepositoryAgent {
        return AiModelRepositoryAgent()
    }

    @Bean
    fun modelMetadataParser(objectMapper: ObjectMapper): ModelMetadataParser {
        return LlmLeaderboardParser(objectMapper)
    }

    @Bean
    fun modelMetadataDiscoveryService(modelMetadataParser: ModelMetadataParser): ModelMetadataDiscoveryService {
        return LlmLeaderboardModelMetadataDiscoveryService(modelMetadataParser)
    }

    @Bean
    fun modelMetadataValidationService(aiModelRepository: AiModelRepository): ModelMetadataValidationService {
        return AiRepositoryModelMetadataValidationService(aiModelRepository)
    }
}

fun main(args: Array<String>) {
    runApplication<EmbabelDatabaseServer>(*args)
}
