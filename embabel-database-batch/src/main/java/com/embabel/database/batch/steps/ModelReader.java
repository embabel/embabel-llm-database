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
package com.embabel.database.batch.steps;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.Queue;

public class ModelReader implements ItemReader<String> {

    private static final Log logger = LogFactory.getLog(ModelReader.class);

    private Queue<String> queue;

    @Value("${embabel.database.source.llmstats.model:https://api.zeroeval.com/leaderboard/models}")
    String modelUrl;

    @Autowired
    RestClient restClient;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.queue = (Queue<String>) stepExecution.getJobExecution().getExecutionContext().get("newModelList");
    }

    @Override
    public String read() {
        if (queue == null || queue.isEmpty()) {
            logger.info("nothing in the queue");
            return null;//done;
        }
        //get the model id
        String modelId = queue.poll();
        //retrieve the json
        String json = restClient.get()
            .uri(modelUrl + "/" + modelId)
            .retrieve()
            .toEntity(String.class)
            .getBody();
        //clean
        json = cleanJson(json);
        //pass on
        return json;
    }


    private static final String BENCHMARK_RANKINGS = "benchmark_rankings";
    private static final String COMPARISON_MODEL = "comparison_model";
    private static final String BENCHMARKS = "benchmarks";

    String cleanJson(String json) {
        String clean = json;
        try {
            //convert to a map
            Map<String,Object> map = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
            //remove
            map.remove(BENCHMARK_RANKINGS);
            map.remove(COMPARISON_MODEL);
            map.remove(BENCHMARKS);
            //convert back
            clean = objectMapper.writeValueAsString(map);
        }
        catch (Exception e) {
            logger.error("unable to clean",e);
        }
        return clean;
    }
}
