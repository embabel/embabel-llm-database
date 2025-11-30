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

import com.embabel.database.agent.AgentConfigurationSupport;
import com.embabel.database.core.repository.ModelRepository;
import com.embabel.database.core.repository.domain.Model;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.FileOutputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes={CombinedServiceITest.class, AgentConfigurationSupport.class}, properties = {
        "spring.ai.bedrock.aws.region=us-east-1",
        "spring.ai.model.chat=none",
        "embabel.models.default-llm=us.anthropic.claude-3-5-sonnet-20240620-v1:0"
})
@ActiveProfiles("no-auto-load")
public class CombinedServiceITest {

    private static final Log logger = LogFactory.getLog(CombinedServiceITest.class);

    @Autowired
    ModelParserService bedrockModelParserService;

    @Autowired
    ModelParserService llmStatsModelParserService;

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testBoth() {
        //check
        assertTrue(modelRepository.findAll().isEmpty());
        //invoke the LLM one first
        llmStatsModelParserService.loadModels();
        //check again
        assertFalse(modelRepository.findAll().isEmpty());
        //get count
        int baseline = modelRepository.count();
        //invoke bedrock
        bedrockModelParserService.loadModels();
        //get second count
        int afterCount = modelRepository.count();
        assertTrue(afterCount > baseline);
        //dump out
        try {
            List<Model> models = modelRepository.findAll();
            objectMapper.registerModule(new JavaTimeModule())
                    .registerModule(new KotlinModule.Builder()
                            .build())
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(new FileOutputStream("./dump.json"),models);
        }
        catch (Exception e) {
            logger.error(e);
        }

    }
}
