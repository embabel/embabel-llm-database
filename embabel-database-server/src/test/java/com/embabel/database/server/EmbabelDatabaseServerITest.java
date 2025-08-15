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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.embabel.agent.core.Agent;
import com.embabel.agent.core.AgentPlatform;
import com.embabel.agent.core.AgentProcess;
import com.embabel.agent.core.AgentProcessStatusCode;
import com.embabel.agent.core.ProcessOptions;
import com.embabel.agent.core.Verbosity;
// import com.embabel.agent.mcpserver.PerGoalToolCallbackProvider;
import com.embabel.agent.web.rest.AgentProcessStatus;
import com.embabel.database.agent.AiModelRepositoryAgent;
import com.embabel.database.core.repository.AiModelRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.spec.McpSchema.ListToolsResult;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("ollama")
public class EmbabelDatabaseServerITest {

    private static final Log logger = LogFactory.getLog(EmbabelDatabaseServerITest.class);

    private static String responseString = "{\"models\":[{\"name\":\"llama3.1:8b\",\"model\":\"llama3.1:8b\",\"modified_at\":\"2025-07-12T18:55:41.604224865Z\",\"size\":4920753328,\"digest\":\"46e0c10c039e019119339687c3c1757cc81b9da49709a3b3924863ba87ca666e\",\"details\":{\"parent_model\":\"\",\"format\":\"gguf\",\"family\":\"llama\",\"families\":[\"llama\"],\"parameter_size\":\"8.0B\",\"quantization_level\":\"Q4_K_M\"}}]}";

    static final WireMockServer wireMockServer = new WireMockServer(options().dynamicPort()); // dynamic port

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    AiModelRepository aiModelRepository;

    @BeforeAll
    public static void setup() {
        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/api/tags")) //ollama url for model listing
                .willReturn(WireMock.aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody(responseString)));
        wireMockServer.start();   
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        String baseUrl = "http://localhost:" + wireMockServer.port();
        registry.add("spring.ai.ollama.base-url", () -> baseUrl);
    }


    // "/api/v1/platform-info"
    /// /agents
    /// /goals"
    /// /actions
    /// /conditions
    /// /models
    /// /tool-groups

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @SuppressWarnings("unchecked")
    @Test
    void testStartup() throws Exception {
        assertTrue(applicationContext.getBeanDefinitionCount() > 0);
        //check for the explicit bean
        assertNotNull(applicationContext.getBean(AiModelRepositoryAgent.class));
        //process
        String response = mockMvc.perform(get("/api/v1/platform-info/agents"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        //validate the response
        assertNotNull(response);
        assertTrue(response.length() > 0);
        //now process and check for the expected exposures
        List<Map<String,Object>> agents = objectMapper.readValue(response,new TypeReference<List<Map<String,Object>>>(){});
        //check count --> should be 1
        assertTrue(agents.size() == 1,"agents count does not equal 1");
        //check name
        for (Map<String,Object> agent : agents) {
            //get the name
            assertEquals(agent.get("name").toString(),"AiModelRepositoryAgent");
            //get the actions
            List<Map<String,Object>> actions = (List<Map<String, Object>>) agent.get("actions");
            assertTrue(actions.size() == 3); //maintainCatalog, discoverModels and validateModels
            List<Map<String,Object>> conditions = (List<Map<String,Object>>) agent.get("conditions");
            assertTrue(conditions.size() == 2); //repository_needs_refresh and needsRefresh
        } //end for
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

        String processId = mockMvc.perform(post("/api/v1/agents/{agentName}","AiModelRepositoryAgent"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        //invoke this endpoint until the load is over --> "/api/v1/process/$id"
        while (true) {
            String reply = mockMvc.perform(get("/api/v1/process/{processId}",processId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
            //convert to a map
            Map<String,Object> replyMap = objectMapper.readValue(reply,new TypeReference<Map<String,Object>>(){});
            //get the id
            String status = replyMap.get("status").toString();
            if (!status.equalsIgnoreCase("RUNNING")) {
                break; //we're done
            }
            logger.info("id response: " + reply);
            //wait
            Thread.sleep(1000);// 1 second
        } //end while
    }
}
