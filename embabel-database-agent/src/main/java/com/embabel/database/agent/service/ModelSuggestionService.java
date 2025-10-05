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
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.embabel.agent.core.Agent;
import com.embabel.agent.core.AgentPlatform;
import com.embabel.agent.core.AgentProcess;
import com.embabel.agent.core.AgentProcessStatusCode;
import com.embabel.agent.core.ProcessOptions;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.database.agent.domain.AgentResult;
import com.embabel.database.agent.domain.ListModelMetadata;

import jakarta.annotation.PostConstruct;

/**
 * service that coordinates agents to make model suggestions
 */
@Service
public class ModelSuggestionService {
    
    private static Log logger = LogFactory.getLog(ModelSuggestionService.class);

    private static final String BLACKBOARD = "blackboard";
    private static final String LIST_MODEL_METADATA = "listModelMetadata";
    private static final String REQUEST = "request";
    private static final String RESULT = "result";
    private static final String SESSION_ID = "sessionId";
    private static final String USER_INPUT = "userInput";

    @Autowired
    AgentPlatform agentPlatform;

    //default order is ModelProviderSuggestionAgent (get providers that have models that match the criteria),
    //followed by the ModelSuggestionAgent for the selected provider and subsequent models
    @Value("${embabel.database.recommendation.agents:ModelProviderSuggestionAgent,ModelSuggestionAgent}")
    String[] agentNames;

    //TODO move to a broader session state
    Map<String,Map<String,Object>> sessionMap;

    @PostConstruct
    public void setup() {
        sessionMap = Collections.synchronizedMap(new HashMap<>()); //TODO enhance to externalize for robust sessions
    }

    public Map<String,Object> getProviderSuggestions(String request) throws Exception {
        //establish a session
        String sessionId = UUID.randomUUID().toString();        
        //add to the session
        Map<String,Object> sessionRecord = new HashMap<>();
        sessionRecord.put(REQUEST,request); //TODO externalize names
        //take in a description and coordinate with agents
        UserInput userInput = new UserInput(request);        
        //retrieve the first agent
        AgentResult agentResult = runAgent("ModelProviderSuggestionAgent",Collections.singletonMap(USER_INPUT, userInput));
        //add to the session
        sessionRecord.put(BLACKBOARD,agentResult.blackboard());
        sessionRecord.put(RESULT,agentResult.result());
        sessionMap.put(sessionId,sessionRecord);
        //return
        Map<String,Object> result = new HashMap<>();
        result.put(SESSION_ID,sessionId);
        result.put(RESULT,agentResult.result());
        return result;
    }

    public Map<String,Object> getModelOptions(String request,String sessionId) throws Exception {
        if (sessionId == null || sessionId.length() <= 0) {
            throw new IllegalArgumentException("no session id passed");
        }//end if
        //retrieve the session
        Map<String,Object> sessionRecord = sessionMap.get(sessionId);
        if (sessionRecord == null) {
            throw new IllegalArgumentException("no session id found"); //throws exception as really should abort
        } //end if
        //extract the blackboard
        Map<String,Object> blackboard = (Map<String, Object>) sessionRecord.get(BLACKBOARD);
        //build the map
        Map<String,Object> context = new HashMap<>();
        context.put(USER_INPUT,new UserInput(request));
        context.put(LIST_MODEL_METADATA,blackboard.get(ListModelMetadata.class.getSimpleName()));
        //now invoke
        AgentResult agentResult = runAgent("ModelSuggestionAgent",context);
        //return
        Map<String,Object> result = new HashMap<>();
        result.put(SESSION_ID,sessionId);
        result.put(RESULT,agentResult.result());
        return result;
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
        ProcessOptions.getDEFAULT();
        AgentProcess agentProcess = agentPlatform.createAgentProcess(agent, ProcessOptions.getDEFAULT(), parameters);
        String processId = agentProcess.getId();
        //run
        agentProcess.run();
        logger.info("started agent " + agent.getName() + " process Id " + processId);
        //wait
        while (agentProcess.getStatus().equals(AgentProcessStatusCode.RUNNING) || agentProcess.getStatus().equals(AgentProcessStatusCode.WAITING)) {
            Thread.sleep(500);//wait
        } //end while
        //now get the blackboard and results
        Object result = agentProcess.lastResult();
        //build the blackboard
        Map<String,Object> blackBoard = agentProcess.spawn()
            .getObjects()
            .stream()
            .collect(Collectors.toMap(
                obj -> obj.getClass().getSimpleName(),
                obj -> obj
            ));
        //build the record
        return new AgentResult(agentName, result.toString(), blackBoard);
    }
}
