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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.embabel.database.agent.domain.AgentSession;

public class InMemorySessionManagementService implements SessionManagementService {

    Map<String,Map<String,AgentSession>> sessionMap;

    public InMemorySessionManagementService() {
        this.sessionMap = Collections.synchronizedMap(new HashMap<>());
    }

    public int count() {
        return sessionMap.size();
    }

    public AgentSession save(AgentSession agentSession) {
        //agentsession should have everything needed
        String sessionId = agentSession.getSessionId();
        if (agentSession.getSessionId() == null || agentSession.getAgentName() == null) {
            throw new IllegalArgumentException("No session Id passed");
        } //end if
        Map<String,AgentSession> sessions = new HashMap<>();
        if (sessionMap.containsKey(sessionId)) {
            sessions = sessionMap.get(sessionId);
            //now handle the order
            if (!sessions.containsKey(agentSession.getAgentName())) {
                //new --> set order
                int newOrderId = getHighestOrderAgentSession(sessionId).getOrder() + 1;
                agentSession.setOrder(newOrderId);
            } //end if
        } //end if
        //process
        sessions.put(agentSession.getAgentName(),agentSession);        
        sessionMap.put(sessionId,sessions);
        //return
        return agentSession;
    }

    public Map<String,AgentSession> get(String sessionId) {
        return sessionMap.get(sessionId);
    }

    public void delete(String sessionId) {
        sessionMap.remove(sessionId);
    }

    public AgentSession get(String sessionId, String agentName) {
        if (sessionMap.containsKey(sessionId)) {
            Map<String,AgentSession> map = sessionMap.get(sessionId);
            if (map.containsKey(agentName)) {
                return map.get(agentName);
            } //end if
        } //end if
        return null; //nothing matches
    }

    public AgentSession get(String sessionId, int order) {
        //get all the agent sessions
        Map<String,AgentSession> sessions = sessionMap.get(sessionId);
        if (sessions == null) throw new IllegalArgumentException("session not found " + sessionId);
        Optional<AgentSession> agentSession = sessions.entrySet()
            .stream()
            .filter(entry -> order == entry.getValue().getOrder())
            .map(Map.Entry::getValue)
            .findFirst();
        if (agentSession.isPresent()) {
            return agentSession.get();
        }//end if
        return null;//default return
        
    }

}
