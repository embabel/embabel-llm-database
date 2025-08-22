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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AWSBedrockTaskParserTest {
    

    // @Test
    void testGetCategory() throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put(AWSBedrockTagParser.INPUT_MODALITY_KEY,Collections.singletonList("TEXT"));
        map.put(AWSBedrockTagParser.OUTPUT_MODALITY_KEY,Collections.singletonList("TEXT"));
        //setup
        ObjectMapper objectMapper = new ObjectMapper();
        AWSBedrockTagParser parser = new AWSBedrockTagParser();
        ReflectionTestUtils.setField(parser, "objectMapper", objectMapper);
        String expectedCategory = "text-to-text";
        //get category
        List<String> result = parser.getTags(map);
        //validate
        assertEquals(expectedCategory, result.get(0));

    }
}
