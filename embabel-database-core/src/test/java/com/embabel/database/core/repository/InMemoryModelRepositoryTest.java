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
package com.embabel.database.core.repository;

import com.embabel.database.core.repository.domain.Model;
import com.embabel.database.core.repository.domain.ModelProvider;
import com.embabel.database.core.repository.domain.Provider;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryModelRepositoryTest {

    @Test
    void testFindAllProviders() {
        //build a provider
        Provider provider = new Provider("id","provider","blah");
        //build a modelProvider
        ModelProvider modelProvider = new ModelProvider("id",provider,0.0,0.0,List.of("tags"),false);
        //build a model
        Model model = new Model("test","test", List.of("strings"),LocalDate.now(), LocalDate.now(),1l,null,false,List.of(modelProvider),"blah", LocalDateTime.now());
        //save
        ModelRepository modelRepository = new InMemoryModelRepository();
        modelRepository.save(model);
        //check
        assertFalse(modelRepository.findAll().isEmpty());
        //try the method
        assertFalse(modelRepository.findAllProviders().isEmpty());
        assertEquals("id",modelRepository.findAllProviders().get(0).getId());
    }
}
