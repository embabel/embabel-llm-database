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
package com.embabel.database.server.controller;

import com.embabel.database.core.repository.ModelRepository;
import com.embabel.database.core.repository.domain.Model;
import com.embabel.database.server.config.DefaultConfig;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(properties = {
        "spring.ai.model.chat=ollama",
        "embabel.models.default-llm=us.anthropic.claude-3-5-sonnet-20240620-v1:0"
})
@Import(DefaultConfig.class)
public class ModelRepositoryControllerITest {
    
    MockMvc mockMvc;

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    ModelRepositoryController modelRepositoryController;

    @BeforeEach
    public void beforeTest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(modelRepositoryController).build();
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get("/api/v1/models"))
            .andExpect(status().isNotFound()); //no data
        //setup a single model
        Model model = new Model("name","id", List.of("tags"),null,null,1l,null,false,null,"description", LocalDateTime.now());
        //save
        modelRepository.save(model);
        //try the mock again
        mockMvc.perform(get("/api/v1/models"))
            .andExpect(status().isOk()); //simple is ok (200)        
    }

}
