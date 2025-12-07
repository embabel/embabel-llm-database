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

import com.embabel.database.core.repository.JpaModelProviderRepository
import com.embabel.database.core.repository.JpaProviderRepository
import com.embabel.database.core.repository.ModelRepository
import com.embabel.database.core.repository.ModelService
import com.embabel.database.core.repository.util.ModelRepositoryLoader
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.EntityManagerFactory
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.*
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.web.client.RestClient

@Profile("jpa")
@Configuration
@ComponentScan(basePackages = ["com.embabel.database.server.util","com.embabel.database.batch","com.embabel.database.agent"])
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = ["com.embabel.database.core.repository"])
@EntityScan("com.embabel.database.core.repository.domain")
@EnableJpaAuditing
@EnableTransactionManagement
class JpaConfig {

    @Bean
    fun restClient(): RestClient {
        return RestClient.builder().build()
    }

    @Bean
    @Primary
    fun transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager {
        return JpaTransactionManager(entityManagerFactory)  // Links to JPA EntityManagerFactory
    }

    @Bean
    fun modelRepositoryLoader(objectMapper: ObjectMapper, modelService: ModelService): ModelRepositoryLoader {
        return ModelRepositoryLoader(objectMapper, modelService)
    }

    @Bean
    fun modelService(jpaModelRepository: ModelRepository, jpaModelProviderRepository: JpaModelProviderRepository, jpaProviderRepository: JpaProviderRepository): ModelService {
        return ModelService(jpaModelRepository,jpaModelProviderRepository, jpaProviderRepository)
    }
}
