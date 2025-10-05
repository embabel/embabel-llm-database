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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping

import org.slf4j.LoggerFactory
import com.embabel.database.agent.service.ModelSuggestionService

import java.util.UUID

private const val sessionKey = "X-embabel-request-id"

@RestController
@RequestMapping("/api/v1/models")
class ModelSuggestionController {

    private val logger = LoggerFactory.getLogger(ModelSuggestionController::class.java)

    @Autowired
    lateinit var modelSuggestionService: ModelSuggestionService

    @PostMapping("/recommend")
    fun getProviders(@RequestBody prompt: String, @RequestHeader headers: Map<String,String>): ResponseEntity<Map<String,Any?>> {
        //check if this one is passing in a session id
        return if (sessionKey in headers) {
            //this is a reply with the same session
            val requestId: String? = headers[sessionKey]
            val results = modelSuggestionService.getModelOptions(prompt, requestId!!)
            val resultMap = mapOf("result" to results["result"])
            ResponseEntity.ok()
                .header(sessionKey,requestId)
                .body(resultMap)
        } else {
            //generate a session id
            // val requestId = UUID.randomUUID().toString()
            //use to get the providers
            val results = modelSuggestionService.getProviderSuggestions(prompt)
            val resultMap = mapOf("result" to results["result"])
            val requestId: String? = results["sessionId"] as String?
            //add to the header and return
            ResponseEntity.ok()
                .header(sessionKey,requestId)
                .body(resultMap)
        } //end if

    }


}
