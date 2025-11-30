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
package com.embabel.database.batch.listener;

import com.embabel.database.core.repository.ModelRepository;
import com.embabel.database.core.repository.domain.Model;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileOutputStream;
import java.util.List;

public class ChunkDumpListener implements ChunkListener {

    private static final Log logger = LogFactory.getLog(ChunkDumpListener.class);

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    ObjectMapper objectMapper;

    String location = "./dump.json";

    @Override
    public void afterChunk(ChunkContext context) {
        //dump the current model repository
        try {
            //write out
            List<Model> models = modelRepository.findAll();
            objectMapper.registerModule(new JavaTimeModule())
                    .registerModule(new KotlinModule.Builder()
                            .build())
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(new FileOutputStream(location),models);
        } catch (Exception e) {
            logger.error("error writing out",e);
        }
    }
}
