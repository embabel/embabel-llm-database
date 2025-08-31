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
package com.embabel.database.server.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.embabel.agent.core.AgentProcess;
import com.embabel.common.ai.model.ModelMetadata;
import com.embabel.common.ai.model.PerTokenPricingModel;
import com.embabel.database.agent.domain.TagList;
import com.embabel.database.core.repository.AiModelRepository;
import com.embabel.database.core.repository.LlmModelMetadata;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class ModelSuggestionServiceTest {

    String[] tags = new String[]{"image-to-text","image-text-to-text","text-to-image","text-to-image-text","text-to-video","video-text-to-text","audio-to-text", "text-to-audio","audio-to-audio","image-text-to-video","text-to-text","audio-video-image-text-to-text","text-to-embedding" };


    @Test
    void testGetModels() throws Exception {
        //provider
        String provider = "sambanova";
        String requestId = UUID.randomUUID().toString();

        Map<String,Object> processStub = new HashMap<>();

        List<Object> objects = new ArrayList<>();
        List<String> tags = Collections.singletonList("image-to-text");
        TagList tagList = new TagList(tags);
        objects.add(tagList);

        AgentProcess agentProcess = mock(AgentProcess.class);
        when(agentProcess.getObjects()).thenReturn(objects);

        processStub.put("process",agentProcess);

        var today = LocalDate.now();
        //stub object
        var pricing = new PerTokenPricingModel(15.0,75.0);
        var llmModel = new LlmModelMetadata(UUID.randomUUID().toString(),"gpt-4",provider,today,pricing,10000l,tags,"test",0l,"test");

        List<ModelMetadata> models = new ArrayList<>();
        models.add(llmModel);

        RequestRegistry.INSTANCE.getProcessMap().put(requestId,processStub);

        AiModelRepository aiModelRepository = mock(AiModelRepository.class);
        when(aiModelRepository.findByProvider(any())).thenReturn(models);


        ModelSuggestionService modelSuggestionService = new ModelSuggestionService();
        ReflectionTestUtils.setField(modelSuggestionService, "aiModelRepository", aiModelRepository);

        List<ModelMetadata> results = modelSuggestionService.getModels(provider,requestId);
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertTrue(results.get(0).getName().equalsIgnoreCase("gpt-4"));
    }
}
