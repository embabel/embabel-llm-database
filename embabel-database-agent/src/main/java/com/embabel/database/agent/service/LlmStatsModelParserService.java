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

import com.embabel.agent.api.common.autonomy.AgentInvocation;
import com.embabel.database.core.repository.ModelRepository;
import com.embabel.database.core.repository.domain.Model;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.*;
import java.util.stream.Collectors;

public class LlmStatsModelParserService  implements ModelParserService{

    private static final Log logger = LogFactory.getLog(LlmStatsModelParserService.class);

    private static final String MODELS = "models";
    private static final String MODEL_ID = "model_id";

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    RestClient restClient;

    @Value("${embabel.database.source.llmstats.list:https://api.zeroeval.com/leaderboard/models/list}")
    String modelListUrl;

    @Value("${embabel.database.source.llmstats.model:https://api.zeroeval.com/leaderboard/models}")
    String modelUrl;

    @Autowired
    AgentInvocation<Model> agentInvocation;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${embabel.database.test:false}")
    boolean testCircuit = false;

    @Value("${embabel.database.rate-limit:true}")
    boolean ratelimited = true; //default is to rate limit requests

    @Override
    public void loadModels() {
        //get the list fo models
        List<Map<String, Object>> organizationList = restClient.get()
                .uri(modelListUrl)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<Map<String, Object>>>() {
                })
                .getBody();
        //go through the list
        List<String> modelIds = organizationList.stream()
                .map(org -> (List<Map<String, Object>>) org.get(MODELS))
                .flatMap(List::stream).map(model -> {
                    Object id = model.get(MODEL_ID);
                    return id != null ? id.toString() : null;
                })
                .filter(Objects::nonNull)
                .filter(modelId -> modelRepository.findById(modelId) == null)
                .collect(Collectors.toList());
        logger.info("count of model Ids " + modelIds.size());
        //stream list to retrieve the json for each
        try {
            modelIds.forEach(modelId -> {
                String json = restClient.get()
                        .uri(modelUrl + "/" + modelId)
                        .retrieve()
                        .toEntity(String.class)
                        .getBody();
                //clean the json
                json = cleanJson(json);
                //pass to an agent for transformation
                Model model = agentInvocation.invoke(Collections.singletonMap("json", json)); //TODO externalize
                //check
                if (model != null) {
                    modelRepository.save(model);//save
                } //end if
                //check
                if (testCircuit) {
                    throw new BreakException(); //exit break
                } //end if
                //rate limit
                if (ratelimited) {
                    try {
                        Thread.sleep(500);//wait
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }//end if
            });
        } catch (BreakException e) {
            logger.warn("running in test mode",e);
        }
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

    static class BreakException extends RuntimeException {}
}
