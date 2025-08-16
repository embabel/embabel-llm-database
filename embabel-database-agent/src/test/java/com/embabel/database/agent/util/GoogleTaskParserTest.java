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

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GoogleTaskParserTest {
    

    @Test
    void testGetCategory() throws Exception {
        //load up a json
        String model_json = "{\n" + //
                        "      \"name\": \"models/gemini-1.5-pro-002\",\n" + //
                        "      \"version\": \"002\",\n" + //
                        "      \"displayName\": \"Gemini 1.5 Pro 002\",\n" + //
                        "      \"description\": \"Stable version of Gemini 1.5 Pro, our mid-size multimodal model that supports up to 2 million tokens, released in September of 2024.\",\n" + //
                        "      \"inputTokenLimit\": 2000000,\n" + //
                        "      \"outputTokenLimit\": 8192,\n" + //
                        "      \"supportedGenerationMethods\": [\n" + //
                        "        \"generateContent\",\n" + //
                        "        \"countTokens\",\n" + //
                        "        \"createCachedContent\"\n" + //
                        "      ],\n" + //
                        "      \"temperature\": 1,\n" + //
                        "      \"topP\": 0.95,\n" + //
                        "      \"topK\": 40,\n" + //
                        "      \"maxTemperature\": 2\n" + //
                        "    }";
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> map = objectMapper.readValue(model_json,new TypeReference<Map<String,Object>>(){});
        //setup parser
        GoogleTaskParser googleTaskParser = new GoogleTaskParser();
        ReflectionTestUtils.setField(googleTaskParser, "objectMapper", objectMapper);
        String expectedCategory = "audio-video-image-text-to-text";
        //invoke
        String result = googleTaskParser.getTask(map);
        //validate
        assertEquals(expectedCategory,result);
    }

    @Test
    void testEmbedding() throws Exception {
        //load up a json
        String model_json = "{\n" + //
                        "      \"name\": \"models/text-embedding-004\",\n" + //
                        "      \"version\": \"004\",\n" + //
                        "      \"displayName\": \"Text Embedding 004\",\n" + //
                        "      \"description\": \"Obtain a distributed representation of a text.\",\n" + //
                        "      \"inputTokenLimit\": 2048,\n" + //
                        "      \"outputTokenLimit\": 1,\n" + //
                        "      \"supportedGenerationMethods\": [\n" + //
                        "        \"embedContent\"\n" + //
                        "      ]\n" + //
                        "    }";
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> map = objectMapper.readValue(model_json,new TypeReference<Map<String,Object>>(){});
        //setup parser
        GoogleTaskParser googleTaskParser = new GoogleTaskParser();
        ReflectionTestUtils.setField(googleTaskParser, "objectMapper", objectMapper);
        String expectedCategory = "text-to-embedding";
        //invoke
        String result = googleTaskParser.getTask(map);
        //validate
        assertEquals(expectedCategory,result);
    }  
    
    @Test
    void testImage() throws Exception {
        //load up a json
        String model_json = "{\n" + //
                        "      \"name\": \"models/gemini-2.0-flash-preview-image-generation\",\n" + //
                        "      \"version\": \"2.0\",\n" + //
                        "      \"displayName\": \"Gemini 2.0 Flash Preview Image Generation\",\n" + //
                        "      \"description\": \"Gemini 2.0 Flash Preview Image Generation\",\n" + //
                        "      \"inputTokenLimit\": 32768,\n" + //
                        "      \"outputTokenLimit\": 8192,\n" + //
                        "      \"supportedGenerationMethods\": [\n" + //
                        "        \"generateContent\",\n" + //
                        "        \"countTokens\"\n" + //
                        "      ],\n" + //
                        "      \"temperature\": 1,\n" + //
                        "      \"topP\": 0.95,\n" + //
                        "      \"topK\": 64,\n" + //
                        "      \"maxTemperature\": 2\n" + //
                        "    }";
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> map = objectMapper.readValue(model_json,new TypeReference<Map<String,Object>>(){});
        //setup parser
        GoogleTaskParser googleTaskParser = new GoogleTaskParser();
        ReflectionTestUtils.setField(googleTaskParser, "objectMapper", objectMapper);
        String expectedCategory = "text-to-image-text";
        //invoke
        String result = googleTaskParser.getTask(map);
        //validate
        assertEquals(expectedCategory,result);
    }      

}
