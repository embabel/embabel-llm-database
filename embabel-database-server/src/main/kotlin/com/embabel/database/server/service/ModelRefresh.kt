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
package com.embabel.database.server.service

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Profile("scheduled")
@Component
class ModelRefresh(
    @Qualifier("asyncJobLauncher") private val jobLauncher: JobLauncher,
    private val jobExplorer: JobExplorer,
    @Qualifier("parserAgentJob") private val job: Job
) {

    private val logger = LoggerFactory.getLogger(ModelRefresh::class.java)

    //initial delay 30 seconds to allow for startup
    @Scheduled(initialDelayString = "\${embabel.agent.scheduling.initial-delay-ms:30000}", fixedRateString = "\${embabel.agent.scheduling.fixed-rate-ms:86400000}") //Default is 24hrs in milliseconds
    fun refreshModels() {
        logger.info("running batch loader process id")
        try {
            val params = JobParametersBuilder()
                .addLong("run.id", System.currentTimeMillis())
                .toJobParameters()

            val execution = jobLauncher.run(job, params)
            logger.info("Started parseAgentJob with status=${execution.status}")
        } catch (ex: Exception) {
            logger.error("Failed to start refreshJob", ex)
        }
    }
}
