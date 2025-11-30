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
package com.embabel.database.core.repository.util;

import com.embabel.database.core.repository.InMemoryModelRepository;
import com.embabel.database.core.repository.ModelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {com.embabel.database.core.repository.InMemoryModelRepositoryTest.class,ModelRepositoryLoaderITest.TestConfig.class})
@ComponentScan(basePackages = "com.embabel.database.core.repository")
@EnableAutoConfiguration
public class ModelRepositoryLoaderITest {

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    ModelRepositoryLoader modelRepositoryLoader;

    @Test
    void testLoad() throws Exception {
        //check existing values
        assertTrue(modelRepository.findAll().isEmpty());
        //load
        modelRepositoryLoader.loadFromFile("./json/export.json");
        //check again
        assertFalse(modelRepository.findAll().isEmpty());
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        ModelRepository modelRepository() {
            return new InMemoryModelRepository();
        }
    }
}
