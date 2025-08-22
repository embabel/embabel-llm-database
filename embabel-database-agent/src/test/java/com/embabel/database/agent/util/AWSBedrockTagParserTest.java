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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AWSBedrockTagParserTest {

    private static Log logger = LogFactory.getLog(LlmLeaderboardTagParserTest.class);

    @Test
    void testGetCategory() throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put(AWSBedrockTagParser.INPUT_MODALITY_KEY,Arrays.asList("TEXT"));
        map.put(AWSBedrockTagParser.OUTPUT_MODALITY_KEY,Arrays.asList("TEXT"));
        //setup
        ObjectMapper objectMapper = new ObjectMapper();
        AWSBedrockTagParser parser = new AWSBedrockTagParser();
        ReflectionTestUtils.setField(parser, "objectMapper", objectMapper);
        String expectedCategory = "text-to-text";
        //get category
        List<String> results = parser.getTags(map);
        //validate

        assertTrue(results.size() == 5);
        boolean found = false;
        for (String result : results) {
            if (result.equalsIgnoreCase(expectedCategory)) {
                found = true;
                break;
            } //end if
        } //end for
        assertTrue(found);
        //try again
        map = new HashMap<>();
        map.put(AWSBedrockTagParser.INPUT_MODALITY_KEY,Arrays.asList("TEXT","IMAGE"));
        map.put(AWSBedrockTagParser.OUTPUT_MODALITY_KEY,Arrays.asList("TEXT"));
        results = parser.getTags(map);
        //now test count
        assertTrue(results.size() == 6);
        found = false;
        for (String result : results) {
            if (result.equalsIgnoreCase(expectedCategory)) {
                found = true;
                break;
            } //end if
        } //end for
        assertTrue(found);        
    }
}
