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
package com.embabel.database.core.repository.util

import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

import com.embabel.database.core.repository.InMemoryAiModelRepository
import org.slf4j.LoggerFactory

/**
 * must set auto-load profile
 */
@Component
class InMemoryAiModelRepositoryLoader(
    private val environment: Environment
) : ApplicationListener<ContextRefreshedEvent> {

    private val logger = LoggerFactory.getLogger(InMemoryAiModelRepositoryLoader::class.java)

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        val activeProfiles = environment.activeProfiles.orEmpty()
        if ("no-auto-load" in activeProfiles) {
            logger.debug("NOT loading...")
            return
        } //end if
        logger.debug("Loading models on startup from local backup");
        val repository = event.applicationContext.getBean(InMemoryAiModelRepository::class.java)
        //trigger load
        repository.load()
    }

}
