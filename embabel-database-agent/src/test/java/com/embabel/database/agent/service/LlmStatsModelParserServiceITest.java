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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes={LlmStatsModelParserServiceITest.class, AgentConfigurationSupport.class}, properties = {
        "spring.ai.bedrock.aws.region=us-east-1",
        "spring.ai.model.chat=none",
        "embabel.models.default-llm=us.anthropic.claude-3-5-sonnet-20240620-v1:0",
        "embabel.database.test=true"
})
@ActiveProfiles("no-auto-load")
public class LlmStatsModelParserServiceITest {

    @Autowired
    ModelParserService modelParserService;

    @Autowired
    ModelRepository modelRepository;

    @Test
    public void testLoadModels() {
        //invoking the method should
        //1. get a list of all models from llm-stats.com
        //2. check if any of the models are 'new' (check by model_id)
        //3. if they ARE new, pass the JSON string to the agent to convert to a model
        //4. persist the model in the repository
        //baseline
        assertTrue(modelRepository.findAll().isEmpty());
        //invoke
        modelParserService.loadModels();
        //validate by checking the number of models in the repository
        assertFalse(modelRepository.findAll().isEmpty());
    }
}
