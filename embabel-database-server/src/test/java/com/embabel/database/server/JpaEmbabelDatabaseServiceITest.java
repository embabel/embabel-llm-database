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
package com.embabel.database.server;

import com.embabel.database.agent.ModelParserAgent;
import com.embabel.database.server.config.JpaConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "spring.ai.bedrock.aws.region=us-east-1",
        "spring.ai.model.chat=ollama",
        "embabel.models.default-llm=us.anthropic.claude-3-5-sonnet-20240620-v1:0"
})
@AutoConfigureMockMvc
@ActiveProfiles("jpa")
@Import(JpaConfig.class)
public class JpaEmbabelDatabaseServiceITest {

    private static final Log logger = LogFactory.getLog(JpaEmbabelDatabaseServiceITest.class);

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void testStartup() throws Exception {
        assertTrue(applicationContext.getBeanDefinitionCount() > 0);
        //check for the explicit bean
        assertNotNull(applicationContext.getBean(ModelParserAgent.class));
        //process
        String response = mockMvc.perform(get("/api/v1/platform-info/agents"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        //validate the response
        assertNotNull(response);
        assertFalse(response.isEmpty());
        //now process and check for the expected exposures
        List<Map<String,Object>> agents = objectMapper.readValue(response,new TypeReference<List<Map<String,Object>>>(){});
        //check count --> should be 1
        assertFalse(agents.isEmpty(), "agents exist");
    }

    @Test
    void testEndpointProvider() throws Exception {

        //get all the agents
        String agentString = mockMvc.perform(get("/api/v1/platform-info/agents"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Map<String,Object>> agents = objectMapper.readValue(agentString,new TypeReference<List<Map<String,Object>>>(){});
        for (Map<String,Object> agent : agents) {
            logger.info(agent.get("name"));
        } //end for
    }

}
