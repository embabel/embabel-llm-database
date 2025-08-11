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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.embabel.common.ai.model.ModelMetadata;

@Service
public class ModelMetadataService {
    
    private static final Log logger = LogFactory.getLog(ModelMetadataService.class);

    @Autowired
    ApplicationContext applicationContext;

    public List<ModelMetadata> retrieveModelMetadata() {
        //get all the beans that are discoveryservice beans
        Map<String,ModelMetadataDiscoveryService> discoveryServices = applicationContext.getBeansOfType(ModelMetadataDiscoveryService.class);
        //init
        List<ModelMetadata> modelMetadata = new ArrayList<>();
        for (ModelMetadataDiscoveryService modelMetadataDiscoveryService : discoveryServices.values()) {
            logger.info("Invoking " + modelMetadataDiscoveryService.getClass().getName());
            try {
                //invoke
                List<ModelMetadata> results = modelMetadataDiscoveryService.retrieveModelMetadata();
                //add
                modelMetadata.addAll(results);
            } catch (Exception e) {
                logger.error("Error invoking",e);
            }
        } //end for
        return modelMetadata;
    }

}
