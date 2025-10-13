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

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.embabel.agent.core.Agent;
import com.embabel.agent.core.AgentPlatform;
import com.embabel.agent.core.AgentProcess;
import com.embabel.agent.core.AgentProcessStatusCode;
import com.embabel.agent.core.ProcessOptions;
import com.embabel.database.agent.domain.AgentResult;
import com.embabel.database.agent.domain.AgentSession;

public class AgentManagementService {
    
    private static Log logger = LogFactory.getLog(AgentManagementService.class);

    private static final String BLACKBOARD = "blackboard";
    private static final String RESULT = "result";

    @Autowired
    SessionManagementService sessionManagentService;

    @Autowired
    AgentPlatform agentPlatform;

    /**
     * run an agent and preserve the blackboard for it
     * @param sessionId
     * @param agentName
     * @param parameters
     * @throws Exception
     */
    @Async
    public void run(String sessionId, String agentName,Map<String,Object> parameters) throws Exception {
        //check if the session exists
        Map<String,AgentSession> sessionData = sessionManagentService.get(sessionId);
        if (sessionData != null) {
            //this is an existing session with data --> check if it has the same agent name
            if (sessionData.containsKey(agentName)) {
                throw new IllegalArgumentException("data for this session " + sessionId + " and this agent " + agentName + " already exists");
            } //end if
        } //end if
        AgentSession agentSession = sessionManagentService.save(sessionId, agentName, parameters);
        //start the agent
        AgentResult agentResult = runAgent(agentName,parameters);        
        //now process the result
        Map<String,Object> sessionRecord = agentSession.getData();
        sessionRecord.put(BLACKBOARD,agentResult.blackboard());
        sessionRecord.put(RESULT,agentResult.result());
        agentSession.setData(sessionRecord);
        agentSession.setStatus(SessionManagementService.FINISHED);
        //update the session
        sessionManagentService.save(agentSession);
        //done
        logger.info("Agent " + agentName + " session " + sessionId + " finished");        
    }

    /**
     * get the running status (binary = running or finished)
     * @param sessionId
     * @param agentName
     * @return
     * @throws Exception
     */
    public String getStatus(String sessionId, String agentName) throws Exception {
        //check if sessionId exists
        Map<String,AgentSession> session = sessionManagentService.get(sessionId);
        if (session == null) {
            throw new IllegalArgumentException("session doesn't exist");
        } else if (session.get(agentName) == null) {
            throw new IllegalArgumentException("agent doesn't exist for session");
        } //end if
        return session.get(agentName).getStatus();
    }

    /**
     * retrieve the results for a specific session and a specific agent name
     * regardless of status
     * @param sessionId
     * @param agentName
     * @return
     * @throws Exception
     */
    public Map<String,Object> getResults(String sessionId,String agentName) throws Exception {
        Map<String,AgentSession> session = sessionManagentService.get(sessionId);
        if (session == null) {
            throw new IllegalArgumentException("session doesn't exist");
        } else if (session.get(agentName) == null) {
            throw new IllegalArgumentException("agent doesn't exist for session");
        } //end if
        return session.get(agentName).getData();
    }

    /**
     * retrieve the last FINISHED agent results
     * supports additional agents running
     * returns null if nothing finished
     * @param sessionId
     * @return
     * @throws Exception
     */
    public Map<String,Object> getLastFinishedResults(String sessionId) throws Exception {
        AgentSession agentSession = sessionManagentService.getLastCompletedSession(sessionId);
        if (agentSession == null) {
            return null; //nothing finished
        } //end if
        //got the last, let's return just the results
        return agentSession.getData();        
    }

    /**
     * retrieve the last results only if ALL are finished
     * @param sessionId
     * @return
     * @throws Exception
     */
    public Map<String,Object> getLatestResults(String sessionId) throws Exception {
        AgentSession agentSession = sessionManagentService.getHighestOrderAgentSession(sessionId);
        if (agentSession == null) {
            return null; //nothing finished
        } //end if
        //got the last, let's return just the results
        return agentSession.getData();
    }

    /**
     * helper method
     */
    AgentResult runAgent(String agentName,Map<String,Object> parameters) throws Exception {
        //retrieve the agent by name
        Agent agent = null;
        Optional<Agent> optionalAgent = agentPlatform.agents()
            .stream()
            .filter(a -> a.getName().equalsIgnoreCase(agentName))
            .findFirst();
        if (optionalAgent.isPresent()) {
            agent = optionalAgent.get();
        } else {
            throw new IllegalArgumentException("unable to find agent " + agentName);
        } //end if
        //load
        AgentProcess agentProcess = agentPlatform.createAgentProcess(agent, ProcessOptions.DEFAULT, parameters);
        String processId = agentProcess.getId();
        //run
        logger.info("starting agent " + agent.getName() + " process Id " + processId);
        agentProcess.run();
        //wait
        while (agentProcess.getStatus().equals(AgentProcessStatusCode.RUNNING) || agentProcess.getStatus().equals(AgentProcessStatusCode.WAITING)) {
            Thread.sleep(500);//wait
        } //end while
        //now get the blackboard and results
        Object result = agentProcess.lastResult();
        logger.info("completed agent " + agent.getName() + " process Id " + processId);
        //build the blackboard
        Map<String,Object> blackBoard = agentProcess.spawn()
            .getObjects()
            .stream()
            .collect(Collectors.toMap(
                obj -> obj.getClass().getSimpleName(),
                obj -> obj
            ));
        logger.info("is blackboard empty? " + blackBoard.isEmpty());
        //build the record
        return new AgentResult(agentName, result.toString(), blackBoard);
    }

}
