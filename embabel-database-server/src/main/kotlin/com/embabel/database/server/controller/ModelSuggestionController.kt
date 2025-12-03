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
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

private const val sessionKey = "x-embabel-request-id"

@RestController
@RequestMapping("/api/v1/models")
class ModelSuggestionController(
    private val modelSuggestionService: ModelSuggestionService,
    objectMapper: ObjectMapper
) {

    private val logger = LoggerFactory.getLogger(ModelSuggestionController::class.java)

//    @Autowired
//    lateinit var modelSuggestionService: ModelSuggestionService
//
//    @Autowired
//    lateinit var objectMapper: ObjectMapper

    private val mapper: ObjectMapper = objectMapper.registerKotlinModule()
        .registerModule(JavaTimeModule())

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
            val jsonModels = mapper.writeValueAsString(models.listModels().models())
//            val resultMap = mapOf("models" to jsonModels)
            val resultMap = mapOf("models" to models)
            ResponseEntity.ok()
                .header(sessionKey, headers[sessionKey])
                .body(resultMap)
        } else {
            //new conversation
            val sessionContext : SessionContext = modelSuggestionService.getProviderSuggestions(prompt)
            //set the header
            val jsonProviders = mapper.writeValueAsString(sessionContext.modelProviders());
//            val resultMap = mapOf("providers" to jsonProviders)
            val resultMap = mapOf("providers" to sessionContext.modelProviders())
            ResponseEntity.ok()
                .header(sessionKey,sessionContext.sessionid())
                .body(resultMap)
        }
    }

}
