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
package com.embabel.database.server.controller

import com.embabel.database.agent.domain.ModelSuggestion
import com.embabel.database.agent.domain.SessionContext
import com.embabel.database.agent.service.ModelSuggestionService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

private const val sessionKey = "x-embabel-request-id"

@RestController
@RequestMapping("/api/v1/models")
class ModelSuggestionController(
    private val modelSuggestionService: ModelSuggestionService,
    objectMapper: ObjectMapper,
    @Qualifier("asyncJobLauncher") private val jobLauncher: JobLauncher,
    private val jobExplorer: JobExplorer,
    @Qualifier("parserAgentJob") private val job: Job
) {

    private val logger = LoggerFactory.getLogger(ModelSuggestionController::class.java)

    private val mapper: ObjectMapper = objectMapper.registerKotlinModule()
        .registerModule(JavaTimeModule())

    private val expectedAuthId: String = UUID.randomUUID().toString()

    init {
        logger.info("Generated x-embabel-auth-id for this instance: $expectedAuthId")
    }

    @PostMapping("/recommend")
    fun getProviders(@RequestBody payload: Map<String,String>, @RequestHeader headers: Map<String,String>): ResponseEntity<Map<String,Any?>> {
        //log detail
        logger.info("headers $headers");
        //get the prompt
        val prompt = payload["message"]
        //check if there's a session id
        return if (sessionKey in headers) {
            //we've already started this conversation...
            val models : ModelSuggestion = modelSuggestionService.getModelSuggestion(prompt, headers[sessionKey])
            //convert
            val resultMap = mapOf("models" to models)
            ResponseEntity.ok()
                .header(sessionKey, headers[sessionKey])
                .body(resultMap)
        } else {
            //new conversation
//            val sessionContext : SessionContext = modelSuggestionService.getProviderSuggestions(prompt)
            val sessionContext: SessionContext? = modelSuggestionService.getProviderSuggestions(prompt)
            //set the header
//            val resultMap = mapOf("providers" to sessionContext.providers())
            val resultMap = if (sessionContext != null) {
                mapOf("providers" to sessionContext.providers())
            } else {
                mapOf("providers" to emptyList<Any>())
            }

            logger.info(resultMap.toString());

            val sessionId = sessionContext?.sessionid() ?: ""
            ResponseEntity.ok()
                .header(sessionKey, sessionId)
                .body(resultMap)

        }
    }

    @PostMapping("/refresh")
    fun update(@RequestHeader(value = "x-embabel-auth-id") authId: String?): ResponseEntity<Any?> {
        if (authId.isNullOrBlank()) {
            return ResponseEntity
                .badRequest()
                .body(mapOf("error" to "Missing required header: x-embabel-auth-id"))
        }

        if (authId != expectedAuthId) {
            logger.warn("Invalid x-embabel-auth-id provided: $authId")
            return ResponseEntity
                .badRequest()
                .body(mapOf("error" to "Invalid authentication header"))
        }

        return try {
            val params = JobParametersBuilder()
                .addLong("run.id", System.currentTimeMillis())
                .toJobParameters()

            val execution = jobLauncher.run(job, params)
            logger.info("Started parseAgentJob with status=${execution.status}")

            ResponseEntity.accepted().body(mapOf("status" to execution.status.toString(),
                "executionId" to execution.id.toString()))
        } catch (ex: Exception) {
            logger.error("Failed to start refreshJob", ex)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("error" to "Failed to start job"))
        }
    }

    @GetMapping("/refresh/{executionId}")
    fun status(@PathVariable("executionId") executionId : Long): ResponseEntity<Any?> {
        return ResponseEntity.ok()
            .body(mapOf("status" to jobExplorer.getJobExecution(executionId)?.status.toString()))
    }

    @GetMapping("/refresh/{executionId}/counts")
    fun counts(@PathVariable("executionId") executionId: Long): ResponseEntity<Any?> {
        val currentCountMap = HashMap<String,Any?>()
        jobExplorer.getJobExecution(executionId)?.stepExecutions?.forEach { stepExecution ->
            if (stepExecution.executionContext.containsKey("currentCount")) {
                logger.info("current count ${stepExecution.executionContext.get("currentCount")}")
                currentCountMap["currentCount"] = stepExecution.executionContext.get("currentCount")
            }
        }
        //add the start count
        currentCountMap["startCount"] = jobExplorer.getJobExecution(executionId)?.executionContext?.get("startCount")
        //return
        return ResponseEntity.ok()
            .body(currentCountMap)
    }


}
