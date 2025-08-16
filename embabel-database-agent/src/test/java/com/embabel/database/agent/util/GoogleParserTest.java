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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.embabel.common.ai.model.ModelMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GoogleParserTest {

    @Test
    void testJsonStringParse() throws Exception {
        //load up the json string
        String json;
        String json_path = "json/google.response.json";
        //load from test resources
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(json_path)) {
            if (is == null) {
                throw new IllegalArgumentException("test resource not found");
            }//end if
            try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name())) {
                json = scanner.useDelimiter("\\A").next();
            }
        }
        //setup the injection parts
        ObjectMapper objectMapper = new ObjectMapper();
        GoogleParser googleParser = new GoogleParser();
        GoogleTaskParser googleTaskParser = new GoogleTaskParser();
        ReflectionTestUtils.setField(googleParser, "objectMapper", objectMapper);
        ReflectionTestUtils.setField(googleParser, "googleTaskParser", googleTaskParser);
        //execute
        List<ModelMetadata> models = googleParser.parse(json);
        //check
        assertNotNull(models);
        assertTrue(models.size() > 0);
        
    }
}