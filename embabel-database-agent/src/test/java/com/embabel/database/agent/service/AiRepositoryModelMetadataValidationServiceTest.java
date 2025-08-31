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

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.embabel.common.ai.model.ModelMetadata;
import com.embabel.database.core.repository.AiModelRepository;
import com.embabel.database.core.repository.InMemoryAiModelRepository;
import com.embabel.database.core.repository.LlmModelMetadata;

public class AiRepositoryModelMetadataValidationServiceTest {
    

    @Test
    void testValidate() {
        LocalDate dateStamp = LocalDate.now();
        String modelName = "model-0";
        String providerName = "provider-0";
        //setup a single model
        LlmModelMetadata singleModel = new LlmModelMetadata(UUID.randomUUID().toString(),modelName, providerName, dateStamp, null, 1l,Collections.singletonList("task"),"test",0l,"modelName");
        //setup the repo
        AiModelRepository aiModelRepository = new InMemoryAiModelRepository();
        //save
        aiModelRepository.save(singleModel);
        //validate
        assertFalse(aiModelRepository.findAll().isEmpty());
        //create new model list
        List<ModelMetadata> newModels = new ArrayList<>();
        //add existing
        newModels.add(singleModel);
        //add a new one
        LlmModelMetadata newModel = new LlmModelMetadata(UUID.randomUUID().toString(),modelName + "-1", providerName, dateStamp, null, 1l,Collections.singletonList("task"),"test",0l,"modelName");
        newModels.add(newModel);
        //process
        ModelMetadataValidationService modelMetadataValidationService = new AiRepositoryModelMetadataValidationService(aiModelRepository);
        List<ModelMetadata> resultingModels = modelMetadataValidationService.validate(newModels);
        //check
        assertTrue(resultingModels.size() == 1);//should only be 1 record out
        //get the names
        assertFalse(resultingModels.get(0).getName().equals(modelName));
    }

    @Test
    void testInverse() {
        LocalDate dateStamp = LocalDate.now();
        //setup a single model
        LlmModelMetadata singleModel = new LlmModelMetadata(UUID.randomUUID().toString(),"model-0", "provider-0", dateStamp, null, 1l,Collections.singletonList("task"),"test",0l,"modelName");
        //setup the repo
        AiModelRepository aiModelRepository = new InMemoryAiModelRepository();
        //save
        aiModelRepository.save(singleModel);
        //validate
        assertFalse(aiModelRepository.findAll().isEmpty());
        //create new model list
        List<ModelMetadata> newModels = new ArrayList<>();
        //add existing
        newModels.add(singleModel);
        //process
        ModelMetadataValidationService modelMetadataValidationService = new AiRepositoryModelMetadataValidationService(aiModelRepository);
        List<ModelMetadata> resultingModels = modelMetadataValidationService.validate(newModels);
        //check
        assertTrue(resultingModels.isEmpty());
    }
}
