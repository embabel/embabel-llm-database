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

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.embabel.agent.api.common.OperationContext;
import com.embabel.common.ai.model.ModelMetadata;
import com.embabel.database.core.repository.AiModelRepository;
import com.embabel.database.core.repository.LlmModelMetadata;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class AiModelRepositoryAgentTest {

    @Test
    void testMaintainCatalog() throws Exception {
        LocalDate dateStamp = LocalDate.now();
        LocalDateTime matchTime = LocalDateTime.now();
        String modelName = "model-0";
        String providerName = "provider-0";
        //setup a single model
        LlmModelMetadata singleModel = new LlmModelMetadata(UUID.randomUUID().toString(),modelName, providerName, dateStamp, null, 1l,Collections.singletonList("task"),"test",0l,"modelName");
        //setup the repo
        AiModelRepository aiModelRepository = mock(AiModelRepository.class);
        when(aiModelRepository.lastUpdated()).thenReturn(matchTime);
        //setup the list
        List<ModelMetadata> models = new ArrayList<>();
        models.add(singleModel);
        ListModelMetadata listModelMetadata = new ListModelMetadata(models);
        OperationContext operationContext = mock(OperationContext.class);//stub
        AiModelRepositoryAgent aiModelRepositoryAgent = new AiModelRepositoryAgent();
        //inject
        ReflectionTestUtils.setField(aiModelRepositoryAgent, "aiModelRepository", aiModelRepository);
        //invoke
        LocalDateTime response = aiModelRepositoryAgent.maintainCatalog(listModelMetadata,operationContext);
        //validate invocations
        verify(aiModelRepository,times(1)).saveAll(any());
        assertEquals(matchTime, response);
    }


    @Test
    void testNeedsRefresh() throws Exception {
        //set dates
        LocalDateTime matchTime = LocalDateTime.now().minusDays(2);
        AiModelRepository aiModelRepository = mock(AiModelRepository.class);
        when(aiModelRepository.lastUpdated()).thenReturn(matchTime);
        when(aiModelRepository.findAll()).thenReturn(Collections.emptyList());
        //setup
        AiModelRepositoryAgent aiModelRepositoryAgent = new AiModelRepositoryAgent();
        //inject
        ReflectionTestUtils.setField(aiModelRepositoryAgent, "aiModelRepository", aiModelRepository);
        ReflectionTestUtils.setField(aiModelRepositoryAgent, "delay", 24l);
        //invoke with empty and late date
        assertTrue(aiModelRepositoryAgent.needsRefresh());        
        // set the date now
        matchTime = LocalDateTime.now();
        when(aiModelRepository.lastUpdated()).thenReturn(matchTime);
        // but the repository is still empty so should still return true
        assertTrue(aiModelRepositoryAgent.needsRefresh());
        // now set the repository with values and the time now
        LocalDate dateStamp = LocalDate.now();
        String modelName = "model-0";
        String providerName = "provider-0";
        //setup a single model
        LlmModelMetadata singleModel = new LlmModelMetadata(UUID.randomUUID().toString(),modelName, providerName, dateStamp, null, 1l,Collections.singletonList("task"),"test",0l,"modelName");
        List<ModelMetadata> models = new ArrayList<>();
        models.add(singleModel);
        //set the trigger
        when(aiModelRepository.findAll()).thenReturn(models);
        //invoke
        assertFalse(aiModelRepositoryAgent.needsRefresh());//shouldn't need a refresh
    }
}
