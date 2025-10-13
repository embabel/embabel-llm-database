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
package com.embabel.database.agent.domain;

import java.util.Map;

public class AgentSession {
    
    private String sessionId;

    private String agentName;

    private String status;

    private int order;

    private Map<String,Object> data;

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentName() {
        return this.agentName;
    }    

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return this.order;
    }    

    public void setData(Map<String,Object> data) {
        this.data = data;
    }

    public Map<String,Object> getData() {
        return this.data;
    }
}
