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

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;

import com.embabel.database.core.repository.AiModelRepository;
import com.embabel.database.core.repository.InMemoryAiModelRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootTest(classes={InMemoryAiModelRepositoryLoaderITest.class,InMemoryAiModelRepositoryLoaderITest.TestConfig.class})
public class InMemoryAiModelRepositoryLoaderITest {

    private static final Log logger = LogFactory.getLog(InMemoryAiModelRepositoryLoaderITest.class);

    @Autowired
    AiModelRepository aiModelRepository;

    @Test
    void testLoadOnStartup() throws Exception {
        File dataDirectory = new File("./data");
        if (!dataDirectory.exists() && !dataDirectory.isDirectory()) {
            //no model to test against --> exit
            logger.warn("no ./data directory to test against");
            return;
        }//end if
        //check model
        File modelFile = new File("./data/model.json");
        if (!modelFile.exists() && !modelFile.isFile()) {
            //no model to test against --> exit
            logger.warn("no ./data/model.json file to test against");
            return;
        }//end if
        //validate loaded because the files exist
        assertFalse(aiModelRepository.findAll().isEmpty());
    }

    @TestConfiguration
    public static class TestConfig { 

        @Bean
        public AiModelRepository aiModelRepository() {
            return new InMemoryAiModelRepository();
        }
        
        @Bean
        public InMemoryAiModelRepositoryLoader inMemoryAiModelRepositoryLoader() {
            return new InMemoryAiModelRepositoryLoader();
        }

    }   
}