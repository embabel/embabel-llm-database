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

import com.embabel.agent.api.common.autonomy.AgentInvocation
import com.embabel.agent.core.AgentPlatform
import com.embabel.database.agent.ModelProviderSuggestionAgent
import com.embabel.database.agent.ModelSuggestionAgent
import com.embabel.database.agent.service.ModelSuggestionService
import com.embabel.database.agent.service.SessionManagementService
import com.embabel.database.core.repository.InMemoryModelRepository
import com.embabel.database.core.repository.ModelRepository
import com.embabel.database.core.repository.domain.Model
import com.embabel.database.core.repository.util.ModelRepositoryLoader
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
@ComponentScan(basePackages = ["com.embabel.database.server.util","com.embabel.database.batch","com.embabel.database.agent"])
class DefaultConfig {

    //repository
    @Bean
    fun modelRepository(): ModelRepository {
        return InMemoryModelRepository()
    }

    @Bean
    fun modelRepositoryLoader(modelRepository: ModelRepository, objectMapper: ObjectMapper): ModelRepositoryLoader {
        return ModelRepositoryLoader(modelRepository, objectMapper)
    }

    @Bean
    fun restClient(): RestClient {
        return RestClient.builder().build()
    }

    @Bean
    fun agentInvocation(agentPlatform: AgentPlatform): AgentInvocation<Model> {
        return AgentInvocation.builder(agentPlatform).build(Model::class.java)
    }
}
