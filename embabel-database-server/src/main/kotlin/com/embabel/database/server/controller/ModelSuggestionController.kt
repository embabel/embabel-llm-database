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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping

import org.slf4j.LoggerFactory
import com.embabel.database.agent.service.ModelSuggestionService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

import java.util.UUID

private const val sessionKey = "x-embabel-request-id"

@RestController
@RequestMapping("/api/v1/models")
class ModelSuggestionController {

    private val logger = LoggerFactory.getLogger(ModelSuggestionController::class.java)

    @Autowired
    lateinit var modelSuggestionService: ModelSuggestionService

    @Autowired
    lateinit var objectMapper: ObjectMapper

    private val mapper: ObjectMapper = objectMapper.registerKotlinModule()
        .registerModule(JavaTimeModule())

    @PostMapping("/recommend")
    fun getProviders(@RequestBody prompt: String, @RequestHeader headers: Map<String,String>): ResponseEntity<Map<String,Any?>> {
        //check if there's a session id
        logger.info("headers " + headers);
        return if (sessionKey in headers) {
            //we've already started this conversation...
            val models : ModelSuggestion = modelSuggestionService.getModelSuggestion(prompt,headers.get(sessionKey))
            //convert
            val jsonModels = objectMapper.writeValueAsString(models.listModels().models())
            val resultMap = mapOf("models" to jsonModels)
            ResponseEntity.ok()
                .header(sessionKey,headers.get(sessionKey))
                .body(resultMap)
        } else {
            //new conversation
            val sessionContext : SessionContext = modelSuggestionService.getProviderSuggestions(prompt)
            //set the header
            val jsonProviders = objectMapper.writeValueAsString(sessionContext.modelProviders());
            val resultMap = mapOf("providers" to jsonProviders)
            ResponseEntity.ok()
                .header(sessionKey,sessionContext.sessionid())
                .body(resultMap)
        }
    }

}
