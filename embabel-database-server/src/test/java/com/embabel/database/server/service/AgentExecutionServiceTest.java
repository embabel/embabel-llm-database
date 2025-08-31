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
package com.embabel.database.server.service;

import com.embabel.agent.core.Agent;
import com.embabel.agent.core.AgentPlatform;
import com.embabel.agent.core.AgentProcess;
import com.embabel.database.server.service.AgentExecutionService;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.Test;

public class AgentExecutionServiceTest {
    
    @Test
    void testRunAgentProcessAsync() {
        String agentName = "mockName";
        String otherName = "blah";
        //set up mocks
        AgentPlatform agentPlatform = mock(AgentPlatform.class);
        Agent agent = mock(Agent.class);
        AgentProcess agentProcess = mock(AgentProcess.class);
        when(agentProcess.getId()).thenReturn("test-id");
        when(agent.getName()).thenReturn(agentName);
        when(agentPlatform.agents()).thenReturn(Collections.singletonList(agent));
        when(agentPlatform.createAgentProcess(any(), any(), any())).thenReturn(agentProcess);
        //setup
        AgentExecutionService agentExecutionService = new AgentExecutionService(agentPlatform);
        //run
        AgentProcess createdAgentProcess = agentExecutionService.createProcess(agentName,Collections.emptyMap());
        agentExecutionService.runAgentProcessAsync(createdAgentProcess);
        //validate
        verify(agentProcess,times(1)).run();
        //validate for exception 
        assertThrows(IllegalArgumentException.class, () -> {agentExecutionService.createProcess(otherName,Collections.emptyMap());});
    }

}
