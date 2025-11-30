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

import com.embabel.database.core.repository.InMemoryModelRepository;
import com.embabel.database.core.repository.ModelRepository;
import com.embabel.database.core.repository.domain.Model;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoaderTest {

//    @Test
    void testLoad() throws Exception {
        //init
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new KotlinModule.Builder().build())
                .registerModule(new JavaTimeModule());
        ModelRepository modelRepository = new InMemoryModelRepository();
        //load from the json
        InputStream inputStream = this.getClass().getResourceAsStream("/json/dump.json");
        List<Model> models = objectMapper.readValue(inputStream,new TypeReference<List<Model>>(){});
        //check
        assertTrue(models.size() > 0);
        assertTrue(modelRepository.findAll().isEmpty());
        //now insert
        modelRepository.saveAll(models);
        assertFalse(modelRepository.findAll().isEmpty());
        //query
        assertFalse(modelRepository.findAllProviders().isEmpty());

    }
}
