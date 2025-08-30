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

import com.embabel.agent.core.Agent;
import com.embabel.agent.core.AgentPlatform;
import com.embabel.agent.core.AgentProcess;
import com.embabel.agent.core.ProcessOptions;

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

import java.util.concurrent.ConcurrentHashMap

object AgentProcessRegistry {
    val processMap: MutableMap<String, String> = ConcurrentHashMap()

    fun getProcessIdsByAgentName(agentName: String): List<String> {
        return processMap.filterValues { it == agentName }.keys.toList()
    }
}


@Service
class AgentExecutionService(
    private val agentFactory: AgentPlatform
) {

    fun createProcess(agentName: String) : AgentProcess {
        val agent = agentFactory.agents().find { it.name == agentName } ?: throw IllegalArgumentException("Agent with name $agentName not found")
        val processOptions = ProcessOptions.DEFAULT
        val agentProcess = agentFactory.createAgentProcess(agent, processOptions, emptyMap<String, Any>())
        //update the registry
        AgentProcessRegistry.processMap[agentProcess.id] = agentName
        //return
        return agentProcess
    }


    @Async
    fun runAgentProcessAsync(agentProcess: AgentProcess) {
        //execute
        agentProcess.run()
    }

    fun getProcessIds(agentName: String) : List<String> {
        val processIds = AgentProcessRegistry.getProcessIdsByAgentName(agentName)
        return if (processIds.isEmpty()) {
            emptyList()
        } else {
            processIds
        } //end if
    }

}
