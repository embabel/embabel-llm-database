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

import java.util.*;

import com.embabel.agent.api.common.autonomy.AgentInvocation;
import com.embabel.agent.core.*;
import com.embabel.database.agent.domain.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.embabel.agent.domain.io.UserInput;

/**
 * service that coordinates agents to make model suggestions
 */
@Service
public class ModelSuggestionService {
    
    private static final Log logger = LogFactory.getLog(ModelSuggestionService.class);

    @Autowired
    AgentPlatform agentPlatform;

    @Autowired
    SessionManagementService sessionManagementService;

    public SessionContext getProviderSuggestions(String request) throws Exception {
        //return the session ID and trigger the process
        //establish a session
        String sessionId = UUID.randomUUID().toString();        
        //build the invocation
        AgentInvocation<Providers> agentInvocation = AgentInvocation.builder(agentPlatform).build(Providers.class);
        //run the process
        AgentProcess agentProcess = agentInvocation.run(Collections.singletonMap("userInput",new UserInput(request)));
        //wait until it's finished
        while (!agentProcess.getFinished()
                && !agentProcess.getStatus().name().equalsIgnoreCase("STUCK")) {
            logger.info(agentProcess.getStatus().name());
            Thread.sleep(500);//TODO externalize
        } //end while
        if (agentProcess.getStatus().name().equalsIgnoreCase("STUCK")) {
            //prematurely stopped
            logger.warn("stuck");
            return null;
        } //end if
        //store the process for later blackboard retrieval
        Blackboard blackboard = agentProcess.getProcessContext()
                .getBlackboard();
        //save against the session
        Object result = agentProcess.lastResult();
        if (result == null) {
            logger.info("No suggestions");
            return null;
        } //end if
        Providers providers = (Providers) result;
        SessionContext sessionContext = new SessionContext(sessionId,providers,blackboard,request);
        //add to the session management
        sessionManagementService.save(sessionContext);
        //return the results
        return sessionContext;
    }

    private static final String USERINPUT = "userInput";
    private static final String PREVIOUS_PROMPT = "previousPrompt";

    public ModelSuggestion getModelSuggestion(String provider,String sessionId) throws Exception {
        //get the previous session
        SessionContext sessionContext = sessionManagementService.findById(sessionId);
        //check
        if (sessionContext == null) {
            throw new IllegalArgumentException("session Id is invalid " + sessionId);
        } //end if
        //retrieve the blackboard
        Blackboard blackboard = sessionContext.blackboard();
        //build the process options
        ProcessOptions processOptions = ProcessOptions.builder().
                blackboard(blackboard)
                .build();
        //build the invocation
        AgentInvocation<ModelSuggestion> modelSuggestionAgentInvocation = AgentInvocation.builder(agentPlatform)
                .options(processOptions)
                .build(ModelSuggestion.class);
        //TODO clean up process for sessions
        //get the suggestion
        return modelSuggestionAgentInvocation.invoke(Map.of(USERINPUT,new UserInput(provider),PREVIOUS_PROMPT,sessionContext.prompt()));
    }
}
