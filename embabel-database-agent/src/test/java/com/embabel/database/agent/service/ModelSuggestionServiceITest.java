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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import com.embabel.agent.api.common.autonomy.AgentInvocation;
import com.embabel.agent.core.AgentPlatform;
import com.embabel.database.agent.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(classes={ModelSuggestionServiceITest.class, AgentConfigurationSupport.class}, properties = {
        "spring.ai.bedrock.aws.region=us-east-1",
        "spring.ai.model.chat=ollama",
        "aws.accessKeyId=key",
        "aws.secretAccessKey=secret"
})
public class ModelSuggestionServiceITest {

    private static final Log logger = LogFactory.getLog(ModelSuggestionServiceITest.class);

    @Autowired
    ModelSuggestionService modelSuggestionService;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    AgentPlatform agentPlatform;


    @Test
    void testGetProviderSuggestions() throws Exception {
        //load up the repository for testing
        AgentInvocation<LocalDateTime> modelLoader = AgentInvocation.builder(agentPlatform).build(LocalDateTime.class);
        modelLoader.invoke(Collections.emptyMap());
        // String userInputText = "find a model that can extract text from an image";
        // String userInputText = "I want a model that will create an image from text";      
        String userInputText = "I want a model that can create images";
        //invoke the service
        String sessionId = modelSuggestionService.getProviderSuggestions(userInputText);
        assertNotNull(sessionId);
        Map<String,Object> results = null;
        //wait for complete
        while (true) {
            results = modelSuggestionService.getResponse(sessionId);
            if (results != null) {
                break;//end loop
            } //end if
            //wait again
            Thread.sleep(200);
        } //end while
        //get the sessionid
        assertNotNull(results.get("result"));
    }

}
