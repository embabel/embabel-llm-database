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
package com.embabel.database.agent;

import com.embabel.agent.api.common.autonomy.AgentInvocation;
import com.embabel.agent.core.AgentPlatform;
import com.embabel.database.core.repository.ModelRepository;
import com.embabel.database.core.repository.domain.Model;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

//set env variables for AWS keys
@SpringBootTest(classes={ModelParserAgentITest.class, AgentConfigurationSupport.class}, properties = {
        "spring.ai.bedrock.aws.region=us-east-1",
        "spring.ai.model.chat=ollama",
        "embabel.models.default-llm=us.anthropic.claude-3-5-sonnet-20240620-v1:0"
})
@ActiveProfiles("no-auto-load")
public class ModelParserAgentITest {

    private static final Log logger = LogFactory.getLog(ModelParserAgentITest.class);

    @Autowired
    AgentPlatform agentPlatform;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ModelRepository modelRepository;

    @Test
    void testModelParserAgent() throws Exception {
        //load up the json into a string
        String json;
        String json_path = "json/llm-stats-model-jamba-1.5-large.json";
        //load from test resources
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(json_path)) {
            if (is == null) {
                throw new IllegalArgumentException("test resource not found");
            }//end if
            try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name())) {
                json = scanner.useDelimiter("\\A").next();
            }
        }
        //load up the agent
        AgentInvocation<Model> agentInvocation = AgentInvocation.builder(agentPlatform).build(Model.class);

        //strip out the benchmarks form the model
        Map<String,Object> map = objectMapper.readValue(json,new TypeReference<Map<String,Object>>(){});

        logger.info("before " + map.keySet().size());

        //remove
        map.remove("benchmark_rankings");
        map.remove("comparison_model");
        map.remove("benchmarks");

        logger.info("after " + map.keySet().size());

        //serialize
        String clean = objectMapper.writeValueAsString(map);

        //fire away...
        Model model = agentInvocation.invoke(Collections.singletonMap("json",clean));
        //now validate if it has bits...

        assertEquals("Jamba 1.5 Large",model.getName());
        assertFalse(model.getMultiModal());
        assertNotNull(model.getModelProviders());
        assertFalse(model.getModelProviders().isEmpty());
        assertNotNull(model.getOrganization());
        assertFalse(model.getTags().isEmpty());

        //validate the providers
        model.getModelProviders().forEach(modelProvider -> {
            logger.info("provider name " + modelProvider.getProvider().getName());
        });

        //save
        modelRepository.save(model);

        //try invoking a second time
        model = agentInvocation.invoke(Collections.singletonMap("json",clean));
        //should be null
        assertNull(model);
    }
}
