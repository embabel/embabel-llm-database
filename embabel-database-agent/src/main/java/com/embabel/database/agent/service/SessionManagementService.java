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

import com.embabel.database.agent.domain.SessionContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SessionManagementService {

    private final List<SessionContext> sessions;

    public SessionManagementService() {
        this.sessions = new ArrayList<>();
    }

    public SessionContext findById(String sessionId) {
        Optional<SessionContext> sessionContext = sessions.stream()
                .filter(session -> session.sessionid().equalsIgnoreCase(sessionId))
                .findFirst();
        //end if
        return sessionContext.orElse(null);
    }

    public void save(SessionContext sessionContext) {
        //add
        sessions.add(sessionContext);
    }

    public void deleteAll() {
        sessions.clear();
    }
}
