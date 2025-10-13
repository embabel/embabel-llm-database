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
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping

import org.slf4j.LoggerFactory
import com.embabel.database.agent.service.ModelSuggestionService

import java.util.UUID

private const val sessionKey = "x-embabel-request-id"

@RestController
@RequestMapping("/api/v1/models")
class ModelSuggestionController {

    private val logger = LoggerFactory.getLogger(ModelSuggestionController::class.java)

    @Autowired
    lateinit var modelSuggestionService: ModelSuggestionService

    @PostMapping("/recommend")
    fun getProviders(@RequestBody prompt: String, @RequestHeader headers: Map<String,String>): ResponseEntity<Map<String,Any?>> {
        logger.info("headers " + headers);
        //check if this one is passing in a session id
        return if (sessionKey in headers) {
            logger.info("getting the options");
            //this is a reply with the same session
            val requestId: String? = headers[sessionKey]
            val sessionId: String = modelSuggestionService.getModelOptions(prompt, requestId!!)
            //set the session id in the body
            val resultMap = mapOf("sessionId" to sessionId)
            ResponseEntity.ok()
                .header(sessionKey,requestId)
                .body(resultMap)
        } else {
            logger.info("getting the providers");
            // Call the Java method to generate a new session ID and start the process
            val sessionId: String = modelSuggestionService.getProviderSuggestions(prompt)
            //set the session id in the body
            val resultMap = mapOf("sessionId" to sessionId)
            // Build and return the response entity with the session ID in the header
            ResponseEntity.ok()
                .header(sessionKey, sessionId)
                .body(resultMap)
        } //end if
    }

    @GetMapping("/recommend/{sessionId}")
    fun getResults(
        @PathVariable sessionId: String,
        @RequestHeader headers: Map<String, String>
    ): ResponseEntity<Map<String, Any?>> {
        // Check if the header contains the same session ID
        val headerSessionId = headers[sessionKey]
        if (headerSessionId != null && headerSessionId != sessionId) { //TODO fix logic to cater for no header
            return ResponseEntity.badRequest()
                .body(mapOf(
                    "error" to "Session ID in path does not match session ID in header"
                ))
        }

        // Call the  method to get the latest results for this session
        val sessionResults: Map<String, Any?>? = modelSuggestionService.getResponse(sessionId)

        // Prepare the result map
        val resultMap: Map<String, Any?> = if (sessionResults != null) {
            mapOf(
                "sessionId" to sessionId,
                "result" to sessionResults["result"]
            )
        } else {
            mapOf(
                "sessionId" to sessionId,
                "result" to null
            )
        }

        // Return results with the session ID in the header
        return ResponseEntity.ok()
            .header(sessionKey, sessionId)
            .body(resultMap)
    }

}
