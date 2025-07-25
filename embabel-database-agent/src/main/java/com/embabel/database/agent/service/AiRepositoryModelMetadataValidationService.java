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

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.embabel.common.ai.model.ModelMetadata;
import com.embabel.database.core.repository.AiModelRepository;
import com.embabel.database.core.repository.LlmModelMetadata;

@Service
public class AiRepositoryModelMetadataValidationService implements ModelMetadataValidationService {

    private static final Log logger = LogFactory.getLog(AiRepositoryModelMetadataValidationService.class);

    AiModelRepository aiModelRepository;

    public AiRepositoryModelMetadataValidationService(AiModelRepository aiModelRepository) {
        this.aiModelRepository = aiModelRepository;
    }

    @Override
    public List<ModelMetadata> validate(List<ModelMetadata> models) {
        //retrieve the models from the repository
        List<ModelMetadata> existingModels = aiModelRepository.findAll();
        //check size
        if (existingModels.size() <= 0) {
            //write them back as there is none
            logger.info("adding all");
            aiModelRepository.saveAll(models);
            return models;
        }//end if
        //there are models --> need to loop and see if they've changed
        //loop through the "new" models and check
        List<ModelMetadata> filteredExistingModels = models.stream()
            .filter(model -> existingModels.stream()
                .anyMatch(existingModel -> ((LlmModelMetadata) existingModel).compareTo((LlmModelMetadata) model) != 0))
            .collect(Collectors.toList());
        //now we have the list of "filtered" models
        return filteredExistingModels;
    }
    
}
