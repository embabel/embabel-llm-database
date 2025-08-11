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

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.annotation.Condition;
import com.embabel.agent.api.common.OperationContext;
import com.embabel.common.ai.model.ModelMetadata;
import com.embabel.database.agent.service.ModelMetadataDiscoveryService;
import com.embabel.database.agent.service.ModelMetadataService;
import com.embabel.database.agent.service.ModelMetadataValidationService;
import com.embabel.database.core.repository.AiModelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * record to wrap the model list
 */
record ListModelMetadata(List<ModelMetadata> models) { }

@Agent(description = "Discovers and loads AI models from various sources")
public class AiModelRepositoryAgent {

    private static Log logger = LogFactory.getLog(AiModelRepositoryAgent.class);

    @Autowired
    AiModelRepository aiModelRepository;

    // @Autowired
    // ModelMetadataDiscoveryService modelMetadataDiscoveryService;

    @Autowired
    ModelMetadataService modelMetadataService;

    @Autowired
    ModelMetadataValidationService modelMetadataValidationService;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${embabel.repository.refresh.delay:24}") //delay in hours
    Long delay;

        
    @AchievesGoal(description = "Retrieves latest models and updates the repository")
    @Action
    public LocalDateTime maintainCatalog(ListModelMetadata newModels,OperationContext operationContext) {
        //TODO handle deprecation
        aiModelRepository.saveAll(newModels.models());//adds the new ones
        logger.info("finished saving models");
        return aiModelRepository.lastUpdated();
    }

    @Action(pre="repository_needs_refresh")
    public ListModelMetadata discoverModels() {
        logger.info("invoking discoverModels");
        //retrieves models
        List<ModelMetadata> models = modelMetadataService.retrieveModelMetadata();
        //build and return
        return new ListModelMetadata(models);
    }

    @Action(pre="have_models")
    public ListModelMetadata validateModels(ListModelMetadata listModelMetadata) {
        logger.info("invoking validateModels");
        //checks if the models are different
        List<ModelMetadata> models = modelMetadataValidationService.validate(listModelMetadata.models());
        //wrap and return
        return new ListModelMetadata(models);
    }

    @Condition(name="repository_needs_refresh")
    public boolean needsRefresh() {
        logger.info("invoking needsRefresh " + (aiModelRepository.lastUpdated().isBefore(LocalDateTime.now().minus(Duration.ofHours(delay))) || aiModelRepository.findAll().isEmpty()));
        return aiModelRepository.lastUpdated()
            .isBefore(LocalDateTime.now().minus(Duration.ofHours(delay))) || aiModelRepository.findAll().isEmpty();
    }
    
    @Condition(name="have_models")
    public boolean haveModel(ListModelMetadata listModelMetadata) {
         return !listModelMetadata.models().isEmpty(); //inverse response
    }
}

