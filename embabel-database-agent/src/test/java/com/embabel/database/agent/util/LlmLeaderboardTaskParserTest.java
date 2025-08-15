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

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LlmLeaderboardTaskParserTest {
    
    @Test
    public void testGetCategories() {
        ObjectMapper objectMapper = new ObjectMapper();
        LlmLeaderboardTaskParser parser = new LlmLeaderboardTaskParser();
        List<Map<String,Object>> list = parser.getTasks(objectMapper, TaskParser.RESOURCE_LOCATION);
        assertNotNull(list);
        assertFalse(list.isEmpty());
    }

    @Test
    void testGetCategory() throws Exception {
        //json
        String model_json = "{\n" + //
                        "    \"model_provider_id\": 398,\n" + //
                        "    \"model_id\": \"claude-3-5-haiku-20241022\",\n" + //
                        "    \"provider_id\": \"anthropic\",\n" + //
                        "    \"deprecated_at\": null,\n" + //
                        "    \"input_cents_per_million_tokens\": 100,\n" + //
                        "    \"output_cents_per_million_tokens\": 500,\n" + //
                        "    \"quantization\": null,\n" + //
                        "    \"max_input_tokens\": 200000,\n" + //
                        "    \"max_output_tokens\": 200000,\n" + //
                        "    \"throughput\": 100.0,\n" + //
                        "    \"latency\": 0.3,\n" + //
                        "    \"feature_web_search\": false,\n" + //
                        "    \"feature_function_calling\": true,\n" + //
                        "    \"feature_structured_output\": true,\n" + //
                        "    \"feature_code_execution\": false,\n" + //
                        "    \"feature_batch_inference\": true,\n" + //
                        "    \"feature_finetuning\": false,\n" + //
                        "    \"input_modality_text\": true,\n" + //
                        "    \"input_modality_image\": false,\n" + //
                        "    \"input_modality_audio\": false,\n" + //
                        "    \"input_modality_video\": false,\n" + //
                        "    \"output_modality_text\": true,\n" + //
                        "    \"output_modality_image\": false,\n" + //
                        "    \"output_modality_audio\": false,\n" + //
                        "    \"output_modality_video\": false,\n" + //
                        "    \"created_at\": \"2025-07-19T19:49:17.073101+00:00\",\n" + //
                        "    \"updated_at\": \"2025-07-19T19:49:17.073101+00:00\",\n" + //
                        "    \"provider_model_id_used\": \"claude-3-5-haiku-20241022\",\n" + //
                        "    \"model_name\": \"Claude 3.5 Haiku\",\n" + //
                        "    \"organization_id\": \"anthropic\"\n" + //
                        "  }";
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> map = objectMapper.readValue(model_json,new TypeReference<Map<String,Object>>(){});
        //setup the object
        LlmLeaderboardTaskParser parser = new LlmLeaderboardTaskParser();
        ReflectionTestUtils.setField(parser, "objectMapper", objectMapper);
        String expectedCategory = "text-to-text";
        //get category
        String result = parser.getTask(map);
        //validate
        assertEquals(expectedCategory, result);

    }
}
