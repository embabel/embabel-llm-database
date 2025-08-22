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

import com.embabel.database.agent.service.GoogleMetadataDiscoveryService
import com.embabel.database.agent.service.ModelMetadataDiscoveryService
import com.embabel.database.agent.util.GoogleParser
import com.embabel.database.agent.util.GoogleTagParser
import com.embabel.database.agent.util.ModelMetadataParser
import com.embabel.database.agent.util.TagParser

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile


@Profile("google")
@Configuration
class GoogleConfig {

    //Google Task Parser
    @Bean
    fun googleTaskParser(): TagParser {
        return GoogleTagParser()
    }

    //Google Model parser
    @Bean("googleParser")
    fun googleParser(): ModelMetadataParser {
        return GoogleParser()
    }

    //Google discovery service
    @Bean
    fun googleModelMetadataDiscoveryService(@Qualifier("googleParser") googleParser: ModelMetadataParser): ModelMetadataDiscoveryService {
        return GoogleMetadataDiscoveryService(googleParser)
    }
}
