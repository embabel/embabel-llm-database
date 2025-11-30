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

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import com.embabel.agent.api.common.autonomy.AgentInvocation;
import com.embabel.database.agent.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

// import com.embabel.agent.config.annotation.AgentPlatform;
import com.embabel.agent.autoconfigure.platform.AgentPlatformAutoConfiguration;
import com.embabel.agent.config.annotation.EnableAgentMcpServer;
import com.embabel.agent.config.annotation.EnableAgents;
import com.embabel.agent.core.ActionInvocation;
import com.embabel.agent.core.Agent;
import com.embabel.agent.core.AgentPlatform;
import com.embabel.agent.core.AgentProcess;
import com.embabel.agent.core.AgentProcessStatusCode;
import com.embabel.agent.core.ProcessOptions;
import com.embabel.agent.core.support.DefaultAgentPlatform;
import com.embabel.database.agent.util.TagParser;
import com.embabel.database.agent.util.LlmLeaderboardTagParser;
import com.embabel.database.agent.util.LlmLeaderboardParser;
import com.embabel.database.agent.util.ModelMetadataParser;
import com.embabel.database.core.repository.AiModelRepository;
import com.embabel.database.core.repository.InMemoryAiModelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrock.BedrockClient;


@SpringBootTest(classes={AiModelRepositoryAgentITest.class, AgentConfigurationSupport.class}, properties = {
        "spring.ai.bedrock.aws.region=us-east-1",
        "spring.ai.model.chat=ollama",
        "aws.accessKeyId=key",
        "aws.secretAccessKey=secret"
})
@ActiveProfiles("no-auto-load")
public class AiModelRepositoryAgentITest {

    private static final Log logger = LogFactory.getLog(AiModelRepositoryAgentITest.class);
    
    private static String responseString = "{\"models\":[{\"name\":\"llama3.1:8b\",\"model\":\"llama3.1:8b\",\"modified_at\":\"2025-07-12T18:55:41.604224865Z\",\"size\":4920753328,\"digest\":\"46e0c10c039e019119339687c3c1757cc81b9da49709a3b3924863ba87ca666e\",\"details\":{\"parent_model\":\"\",\"format\":\"gguf\",\"family\":\"llama\",\"families\":[\"llama\"],\"parameter_size\":\"8.0B\",\"quantization_level\":\"Q4_K_M\"}}]}";

    static final WireMockServer wireMockServer = new WireMockServer(options().dynamicPort()); //dynamic port

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

    AgentInvocation<LocalDateTime> agentInvocation;

    @Autowired
    AiModelRepository aiModelRepository;

    @Autowired
    AgentPlatform agentPlatform;

    @Test
    void testAgentInvocation() throws Exception {
        //check
        assertTrue(aiModelRepository.findAll().isEmpty());
        //setup invocation
        agentInvocation = AgentInvocation.builder(agentPlatform).build(LocalDateTime.class);
        //invoke to get the latest update time
        LocalDateTime updated = agentInvocation.invoke(Collections.emptyMap());
        assertNotNull(updated);
        logger.info(" last run... " + updated);
        //now try again and time should be null
        updated = agentInvocation.invoke(Collections.emptyMap());
        assertNull(updated);
        logger.info("Not run a second time");
    }

}
