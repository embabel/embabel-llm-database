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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import com.embabel.common.ai.model.ModelMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LlmLeaderboardParserTest {

    @Test
    void testConvertJsontoModelMetadata() throws Exception {
        String json;
        //load from test resources
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("json/anthropic.provider.json")) {
            if (is == null) {
                throw new IllegalArgumentException("test resource not found");
            }//end if
            try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name())) {
                json = scanner.useDelimiter("\\A").next();
            }
        }

        ObjectMapper objectMapper =new ObjectMapper();
        ModelMetadataParser parser = new LlmLeaderboardParser(objectMapper);
        List<ModelMetadata> models = parser.parse(json);
        
        assertNotNull(models);
        assertFalse(models.isEmpty());
    }


    @Test
    void testGitCloneToJsonModelMetadata() throws Exception {
        //use the local copy instead of cloning
        Path tempDir = Paths.get(this.getClass().getResource("/llm-leaderboard").toURI());

        //send to the method to parse
        ObjectMapper objectMapper =new ObjectMapper();
        ModelMetadataParser parser = new LlmLeaderboardParser(objectMapper);
        List<ModelMetadata> models = parser.parse(tempDir);        

        assertNotNull(models);
        assertFalse(models.isEmpty());  
        assertTrue(models.size() > 50);// more than 50 in the list
    }

}
