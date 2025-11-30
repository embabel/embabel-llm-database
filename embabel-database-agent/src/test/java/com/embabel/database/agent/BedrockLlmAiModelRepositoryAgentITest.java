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
import com.embabel.agent.api.common.autonomy.DefaultAgentInvocation;
import com.embabel.agent.config.annotation.EnableAgents;
import com.embabel.agent.core.*;
import com.embabel.agent.testing.integration.EmbabelMockitoIntegrationTest;
import com.embabel.agent.testing.integration.IntegrationTestUtils;
import com.embabel.database.agent.service.*;
import com.embabel.database.core.repository.AiModelRepository;
import com.embabel.database.core.repository.InMemoryAiModelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrock.BedrockClient;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes={
        BedrockLlmAiModelRepositoryAgentITest.class,
        AgentConfigurationSupport.class
}, properties = {
        "spring.ai.bedrock.aws.region=us-east-1",
        "spring.ai.model.chat=ollama",
        "aws.accessKeyId=key",
        "aws.secretAccessKey=secret"
})
@ActiveProfiles("no-auto-load")
public class BedrockLlmAiModelRepositoryAgentITest extends EmbabelMockitoIntegrationTest {

    private static final Log logger = LogFactory.getLog(BedrockLlmAiModelRepositoryAgentITest.class);

    @Autowired
    AiModelRepository aiModelRepository;

    AgentInvocation<LocalDateTime> agentInvocation;

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
