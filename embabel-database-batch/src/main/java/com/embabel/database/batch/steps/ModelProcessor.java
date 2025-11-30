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
package com.embabel.database.batch.steps;

import com.embabel.agent.api.common.autonomy.AgentInvocation;
import com.embabel.database.core.repository.domain.Model;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

public class ModelProcessor implements ItemProcessor<String, Model> {

    @Autowired
    AgentInvocation<Model> agentInvocation;

    @Override
    public Model process(@NotNull String json) throws Exception {
        //invoke the agent
        return agentInvocation.invoke(Collections.singletonMap("json",json));
    }
}
