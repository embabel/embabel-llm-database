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
package com.embabel.database.agent.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.embabel.common.ai.model.ModelMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.services.bedrock.model.FoundationModelSummary;

public class AWSBedrockParserTest {
    
    @Test
    void testParseList() throws Exception {
        String modelName = "model";
        String providerName = "provider";        
        //mock up a list of foundation model summaries
        FoundationModelSummary foundationModelSummary = FoundationModelSummary.builder().modelName(modelName).providerName(providerName).build();
        //add to a list
        List<FoundationModelSummary> foundationModelSummaryList = Collections.singletonList(foundationModelSummary);
        //invoke
        ObjectMapper objectMapper = new ObjectMapper();
        TaskParser taskParser = new AWSBedrockTaskParser();
        ReflectionTestUtils.setField(taskParser, "objectMapper", objectMapper);
        AWSBedrockParser parser = new AWSBedrockParser();
        ReflectionTestUtils.setField(parser, "taskParser", taskParser);
        List<ModelMetadata> results = parser.parse(foundationModelSummaryList);
        //check
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(modelName,results.get(0).getName());
        assertEquals(providerName,results.get(0).getProvider());
    }

    @Test
    void testParseJson() throws Exception {
        assertNull(new AWSBedrockParser().parse("hello"));
    }

    @Test
    void testParsePath() throws Exception {
        assertNull(new AWSBedrockParser().parse(Path.of("http://www.example.com")));
    }    
}
