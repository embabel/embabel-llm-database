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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.embabel.common.ai.model.ModelMetadata;
import com.embabel.database.core.repository.AiModelRepository;
import com.embabel.database.core.repository.InMemoryAiModelRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;



public class HuggingFaceUpdateServiceTest {
    
    private static final Log logger = LogFactory.getLog(HuggingFaceUpdateServiceTest.class);

    // @Test
    void testGetModels() throws Exception {
        HuggingFaceUpdateService huggingFaceUpdateService = new HuggingFaceUpdateService();
        List<Map<String,Object>> results = huggingFaceUpdateService.getModels();
        assertNotNull(results);
        logger.info(results.size());
    }

    // @Test
    void testLoadModels() {
        //check if the file exists
        File location = new File("./data","hf.models.json");
        assertFalse(location.exists());
        HuggingFaceUpdateService huggingFaceUpdateService = new HuggingFaceUpdateService();
        ObjectMapper objectMapper = new ObjectMapper();
        ReflectionTestUtils.setField(huggingFaceUpdateService, "objectMapper", objectMapper);
        // invoke the load
        huggingFaceUpdateService.loadModels();
        //check location now exists
        assertTrue(location.exists());
    }

    @Test
    void testUpdateModels() throws Exception {
        //load up the json representation into the maps
        ObjectMapper objectMapper = new ObjectMapper();
        InMemoryAiModelRepository repository = new InMemoryAiModelRepository();
        repository.load();
        List<ModelMetadata> models = repository.findAll();
        //now invoke the HF mechanism
        HuggingFaceUpdateService huggingFaceUpdateService = new HuggingFaceUpdateService();
        ReflectionTestUtils.setField(huggingFaceUpdateService, "objectMapper", objectMapper);
        //load
        huggingFaceUpdateService.updateModels(models);
    }

    @Test
    void testGetReadme() throws Exception {
        String repoId = "openai/gpt-oss-20b";
        String readme = new HuggingFaceUpdateService().getReadme(repoId);
        assertNotNull(readme);
        assertTrue(readme.contains("models were trained on our"));
    }
}
