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
package com.embabel.database.agent.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.embabel.database.agent.domain.AgentSession;

public class InMemorySessionManagementServiceTest {
    
    @Test
    public void testSave() throws Exception {
        SessionManagementService sessionManagementService = new InMemorySessionManagementService();
        //test with the creation of the simplest save
        Map<String,Object> data = Collections.singletonMap("hello","world");
        String sessionId = "test-session";
        String agentName = "test-agent";
        //validate doesn't exist
        assertNull(sessionManagementService.get(sessionId));
        //save
        AgentSession agentSession = sessionManagementService.save(sessionId, agentName, data);
        //check
        assertTrue(agentSession.getData().containsKey("hello"));
        assertNotNull(sessionManagementService.get(sessionId));
        assertNotNull(sessionManagementService.get(sessionId, agentName));
        agentSession = sessionManagementService.get(sessionId, agentName);
        assertTrue(agentSession.getData().containsKey("hello"));
        assertEquals(agentSession.getAgentName(),agentName);
        assertNotNull(agentSession.getData());
    }

    @Test
    public void testSaveProcess() throws Exception {
        //init
        SessionManagementService sessionManagementService = new InMemorySessionManagementService();
        //base
        String sessionId = "test-session";
        String agentName = "test-agent";        
        //create a map
        Map<String,Object> data = new HashMap<>();
        data.put("hello","world");
        //create a session
        AgentSession agentSession = sessionManagementService.save(sessionId,agentName,data);
        //validate
        assertNotNull(agentSession.getData());
    }

    @Test
    public void testUpdate() throws Exception {
        //create the base object
        SessionManagementService sessionManagementService = new InMemorySessionManagementService();
        //test with the creation of the simplest save
        Map<String,Object> data = Collections.singletonMap("hello","world");
        String sessionId = "test-session";
        String agentName = "test-agent";
        //validate doesn't exist
        assertNull(sessionManagementService.get(sessionId));
        //save
        sessionManagementService.save(sessionId, agentName, data);
        //check
        assertNotNull(sessionManagementService.get(sessionId));
        assertNotNull(sessionManagementService.get(sessionId, agentName));
        AgentSession agentSession = sessionManagementService.get(sessionId, agentName);
        assertTrue(agentSession.getData().containsKey("hello"));
        assertEquals(agentSession.getAgentName(),agentName);
        //cehck the status
        assertEquals(agentSession.getStatus(),SessionManagementService.RUNNING); //default initial status
        //upsave
        agentSession.setStatus(SessionManagementService.FINISHED);
        //save
        sessionManagementService.save(agentSession);
        //retrieve and check
        agentSession = sessionManagementService.get(sessionId, agentName);
        //check status
        assertEquals(agentSession.getStatus(),SessionManagementService.FINISHED);
    }

    @Test
    public void testOrdering() throws Exception {
        //create the initial agent
        //create the base object
        SessionManagementService sessionManagementService = new InMemorySessionManagementService();
        //test with the creation of the simplest save
        Map<String,Object> data = Collections.singletonMap("hello","world");
        String sessionId = "test-session";
        String agentName = "test-agent";
        //validate doesn't exist
        assertNull(sessionManagementService.get(sessionId));
        //save
        sessionManagementService.save(sessionId, agentName, data);
        //check
        assertNotNull(sessionManagementService.get(sessionId));
        assertNotNull(sessionManagementService.get(sessionId, agentName));
        AgentSession agentSession = sessionManagementService.get(sessionId, agentName);
        assertTrue(agentSession.getData().containsKey("hello"));
        assertEquals(agentSession.getAgentName(),agentName);
        //check the order
        assertEquals(agentSession.getOrder(),0); //default order
        //add another agent to the session
        String secondAgentName = agentName + "_1";
        sessionManagementService.save(sessionId, secondAgentName, data);
        agentSession = sessionManagementService.get(sessionId, secondAgentName);
        assertEquals(agentSession.getAgentName(),secondAgentName);
        //check the order
        assertEquals(agentSession.getOrder(),1); //default order
        //get the highest
        agentSession = sessionManagementService.getHighestOrderAgentSession(sessionId);
        assertEquals(agentSession.getAgentName(),secondAgentName);
    }

    @Test
    public void testNullHighestOrder() throws Exception {
        SessionManagementService sessionManagementService = new InMemorySessionManagementService();
        String sessionId = "test-session";
        assertThrows(IllegalArgumentException.class,() -> {
            sessionManagementService.getHighestOrderAgentSession(sessionId);
        });
    }

    @Test
    public void testGetLastCompleted() throws Exception {
        //setup
        SessionManagementService sessionManagementService = new InMemorySessionManagementService();

        AgentSession agentSession = null;
        Map<String,Object> data = Collections.singletonMap("hello","world");
        String sessionId = "test-session";
        String targetAgent = "test-agent_finished";

        //add 3 sessions with only the 1st one completed
        for (int i=0;i<3;i++) {
            if (i == 0) {
                sessionManagementService.save(sessionId, targetAgent, data);
                agentSession = sessionManagementService.get(sessionId, targetAgent);
                agentSession.setStatus(SessionManagementService.FINISHED);
                sessionManagementService.save(agentSession);
            } else {
                String agentName = "test-agent_" + i;
                sessionManagementService.save(sessionId, agentName, data);
            } //end if            
        }
        //now should have 3 agents and only targetAgent is finished
        //check highest orders
        agentSession = sessionManagementService.getHighestOrderAgentSession(sessionId);
        assertTrue(agentSession.getOrder() == 2);
        //get the last completed
        agentSession = sessionManagementService.getLastCompletedSession(sessionId);
        assertTrue(agentSession.getOrder() == 0); //first one
    }

    @Test
    public void testGetNothingCompleted() throws Exception {
        //setup
        SessionManagementService sessionManagementService = new InMemorySessionManagementService();

        AgentSession agentSession = null;
        Map<String,Object> data = Collections.singletonMap("hello","world");
        String sessionId = "test-session";

        //add 3 sessions with only the 1st one completed
        for (int i=0;i<3;i++) {
            String agentName = "test-agent_" + i;
            sessionManagementService.save(sessionId, agentName, data);
        }
        //now should have 3 agents and only targetAgent is finished
        //check highest orders
        agentSession = sessionManagementService.getHighestOrderAgentSession(sessionId);
        assertTrue(agentSession.getOrder() == 2);
        //get the last completed
        agentSession = sessionManagementService.getLastCompletedSession(sessionId);
        assertNull(agentSession);
    }
}
