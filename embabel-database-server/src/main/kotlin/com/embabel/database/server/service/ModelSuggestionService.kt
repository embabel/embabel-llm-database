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

import com.embabel.agent.core.AgentPlatform;
import com.embabel.agent.core.AgentProcess
import com.embabel.agent.domain.io.UserInput;

import com.embabel.common.ai.model.ModelMetadata

import com.embabel.database.agent.domain.ProviderList
import com.embabel.database.agent.domain.TagList
import com.embabel.database.core.repository.AiModelRepository
import com.embabel.database.core.repository.LlmModelMetadata

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import org.slf4j.LoggerFactory

import java.util.concurrent.ConcurrentHashMap

public object RequestRegistry {

    val processMap: MutableMap<String, Map<String,Any>> = ConcurrentHashMap()
}

@Service
class ModelSuggestionService {

    private val logger = LoggerFactory.getLogger(ModelSuggestionService::class.java)

    val agentName = "ModelSuggestionAgent"

    //reuse the existing agent execution service
    @Autowired
    lateinit var agentExecutionService: AgentExecutionService

    @Autowired
    lateinit var aiModelRepository: AiModelRepository

    fun getProviders(prompt: String, requestId: String) : String? {
        //execute the agent
        //wait
        //get response objects
        //posit back to the user
        //build the input
        val userInput = UserInput(prompt)
        val options = mapOf("userInput" to userInput)
        var agentProcess = agentExecutionService.createProcess(agentName,options)
        //run
        agentExecutionService.runAgentProcessAsync(agentProcess)
        //monitor
        agentExecutionService.monitor(agentProcess)
        //register this result
        var executionMap = mapOf("prompt" to prompt, "process" to agentProcess)
        RequestRegistry.processMap[requestId] = executionMap
        //complete - get status and objects
        var results = agentProcess.lastResult()
        var providerList = results as? ProviderList
        // return
        return providerList?.providers?.joinToString(",")
    }

    fun getModels(provider: String, requestId: String) : List<ModelMetadata>? {
        //use the provider (string) and the request id to retrieve the tags
        var process = RequestRegistry.processMap[requestId]
        //search for models from the repository for the provider
        var models: List<ModelMetadata>? = aiModelRepository.findByProvider(provider)
        //now validate for the tag
        var processMap = process as? Map<String,Any>
        var history = processMap?.get("process") as AgentProcess
        var objectList = history?.objects
        //loop
        val tagList: TagList? = objectList?.filterIsInstance<TagList>()?.firstOrNull()
        val tagsArray: Array<String>? = tagList?.tags?.toTypedArray()
        //filter
        val results = models?.filterIsInstance<LlmModelMetadata>()?.filter { model -> model.tags?.any { it in (tagsArray ?: emptyArray())} == true }.orEmpty()
        //return
        return results.takeIf { it.isNotEmpty() }
    }
}
