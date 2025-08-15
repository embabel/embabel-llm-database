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

import jakarta.annotation.PostConstruct

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile


@Profile("!aws")
@Configuration
class AWSErrorSuppressionConfig {

    @PostConstruct
    fun init() {
        // set a system property to fix region discovery error
        System.setProperty("aws.region", "us-east-1")
    }
}
