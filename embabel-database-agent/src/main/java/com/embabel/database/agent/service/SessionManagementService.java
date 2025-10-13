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

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.embabel.database.agent.domain.AgentSession;

public interface SessionManagementService {

    static final String RUNNING = "started";
    static final String FINISHED = "finished";

    AgentSession save(AgentSession agentSession);

    Map<String,AgentSession> get(String sessionId);

    AgentSession get(String sessionId,String agentName);

    void delete(String sessionId);

    AgentSession get(String sessionId,int order);

    int count();

    default AgentSession save(String sessionId,String agentName,Map<String,Object> data) {
        AgentSession agentSession = new AgentSession();
        agentSession.setSessionId(sessionId);
        agentSession.setAgentName(agentName);
        if (data == null) {
            agentSession.setData(new HashMap<>());
        } else {
            agentSession.setData(data);
        } //end if
        agentSession.setStatus(RUNNING);
        //save
        return save(agentSession);
    }

    default AgentSession getHighestOrderAgentSession(String sessionId) {
        //get all the agents
        Map<String,AgentSession> session = get(sessionId);
        if (session == null) {
            throw new IllegalArgumentException("session does not exist");
        } //end if
        //iterate
        Optional<AgentSession> highest = session.entrySet()
            .stream()
            .max(Comparator.comparing(entry -> entry.getValue().getOrder()))
            .map(Map.Entry::getValue);
        if (highest.isPresent()) {
            return highest.get();
        } //end if
        return null;//default
    }

    default AgentSession getLastCompletedSession(String sessionId) {
        //get the highest order 
        AgentSession agentSession = getHighestOrderAgentSession(sessionId);
        if (agentSession == null) { //null --> exit (problem)
            throw new IllegalArgumentException("session not found");
        } //end if
        //check status
        if (agentSession.getStatus().equals(FINISHED)) { //done
            return agentSession;
        }//end if        
        //not finished, need to find the one that is
        if (agentSession.getOrder() > 0) { //there's more than 1
            int targetOrder = agentSession.getOrder() - 1;
            while (true) {                
                if (targetOrder < 0) {
                    //none of these agents are finished
                    break;
                } //end if
                //retrieve by order
                agentSession = get(sessionId, targetOrder);
                if (agentSession != null && agentSession.getStatus().equals(FINISHED)) {
                    //this is the one
                    return agentSession;
                } else if (agentSession == null) {
                    //got a problem
                    break;
                } //end if
                targetOrder = agentSession.getOrder() - 1;
            } //end while
        } //end if        
        return null;
    }
}
