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
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

import com.embabel.database.server.service.AgentExecutionService

@RestController
@RequestMapping("/api/v1/agents")
class DirectAgentController {

    @Autowired
    lateinit var agentExecutionService: AgentExecutionService

    @PostMapping("/{agentName}")
    fun runAgent(@PathVariable agentName: String): ResponseEntity<String> {
        //create the process
        val agentProcess = agentExecutionService.createProcess(agentName)
        //execute
        agentExecutionService.runAgentProcessAsync(agentProcess)
        //return
        return ResponseEntity.ok(agentProcess.id)
    }
}
