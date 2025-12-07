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
import com.embabel.agent.domain.io.UserInput;
import com.embabel.database.agent.domain.ListModels;
import com.embabel.database.agent.domain.ModelProviders;
import com.embabel.database.agent.domain.TagList;
import com.embabel.database.core.repository.ModelRepository;
import com.embabel.database.core.repository.util.ModelRepositoryLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes={ModelProviderSuggestionAgentITest.class, AgentConfigurationSupport.class}, properties = {
        "spring.ai.bedrock.aws.region=us-east-1",
        "spring.ai.model.chat=ollama",
        "embabel.models.default-llm=us.anthropic.claude-3-5-sonnet-20240620-v1:0"
})
public class ModelProviderSuggestionAgentITest {

    private static final Log logger = LogFactory.getLog(ModelProviderSuggestionAgentITest.class);

    AgentInvocation<ModelProviders> agentInvocation;

    @Autowired
    AgentPlatform agentPlatform;

    @Autowired
    ModelProviderSuggestionAgent modelProviderSuggestionAgent;

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    ModelRepositoryLoader modelRepositoryLoader;

    @BeforeEach
    void before() {
        modelRepository.reset();
        //load
        modelRepositoryLoader.loadFromFile("./json/export.json");
    }

    @Test
    void testTags() throws Exception {
        //dump all the unique tags
        //get all the models
        int size = modelRepository.findAll().size();
        //tag
        String tag = "text-to-text";
        int resultSize = modelRepository.findByTags(tag).size();
        //check
        assertTrue(resultSize < size);
        //check multi tag options
        resultSize = modelRepository.findByTags(tag,"image-to-text").size();
        assertTrue(resultSize > 0);
        assertTrue(resultSize < size);
        modelRepository.findAll()
                .stream()
                .flatMap(model -> model.getTags().stream())
                .distinct()
                .forEach(t -> logger.info(t));
    }

    @Test
    void testGetProviders() throws Exception {
        //send in the desired model prompt
        //get a response that should be a set of providers to choose from
        agentInvocation = AgentInvocation.builder(agentPlatform).build(ModelProviders.class);
        //invoke
        UserInput userInput = new UserInput("I want a model that will transcribe video");
        ModelProviders providers = agentInvocation.invoke(Collections.singletonMap("userInput",userInput));
        //check
        assertNotNull(providers);
        logger.info(providers.message());
        logger.info(providers.providers());
    }

    @Test
    void testGetModelsByTag() throws Exception {
        String tag = "audio-video-image-text-to-text";

        TagList tagList = new TagList(List.of(tag));
        ListModels listModels = modelProviderSuggestionAgent.getModelsByTag(tagList);
        logger.info(listModels.models().size());

        int count = modelRepository.findByTags(tag).size();
        logger.info("direct " + count);

        //get all of them
        AtomicInteger atomicInteger = new AtomicInteger(0);
        modelRepository.findAll().stream().forEach(model -> {
            if (model.getTags().contains(tag)) {
                //increment counter
                atomicInteger.incrementAndGet();
            }
        });

        logger.info("found " + atomicInteger.get());
    }
}
