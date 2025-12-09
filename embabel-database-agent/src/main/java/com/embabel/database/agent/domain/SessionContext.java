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

import com.embabel.agent.core.Blackboard;

/**
 * holder for session
 * @param sessionid
 * @param providers
 */
public record SessionContext(String sessionid, Providers providers, Blackboard blackboard, String prompt) {

    public SessionContext {
        if (sessionid == null
                || sessionid.isEmpty()
                || providers == null
                || blackboard == null
                || prompt == null
                || prompt.isEmpty()) {
            throw new IllegalArgumentException("incorrectly constructed");
        }
    }
}
