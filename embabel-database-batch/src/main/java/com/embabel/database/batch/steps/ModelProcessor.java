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
