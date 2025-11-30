package com.embabel.database.agent.service;

import com.embabel.common.ai.model.ModelMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LlmStatsModelMetadataDiscoveryService implements ModelMetadataDiscoveryService {

    private static final Log logger = LogFactory.getLog(LlmStatsModelMetadataDiscoveryService.class);

    @Value("${embabel.provider.repository:https://api.zeroeval.com/leaderboard/models")
    private String baseUrl;

//    @Autowired
    RestClient restClient;

    @Autowired
    ObjectMapper objectMapper;

    public LlmStatsModelMetadataDiscoveryService() {
        restClient = RestClient.builder().build();
    }

    @Override
    public List<ModelMetadata> retrieveModelMetadata() {
        ResponseEntity<List<Map<String,Object>>> response = restClient.get()
                .uri(baseUrl + "/list")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<Map<String,Object>>>() {});

        if (response.hasBody()) {
            //extract
            List<String> modelIds = new ArrayList<>();
            //convert
            response.getBody().forEach(rawModel -> {
                //get the "models" key
                List<Map<String,Object>> modelList = (List<Map<String, Object>>) rawModel.get("models");
                modelList.forEach(m -> {
                    //this model_id is what is then pulled from the endpoint
                    modelIds.add(m.get("model_id").toString());
                });
            });
            //how many do we have?
            logger.info(modelIds.size());
            //now get just one
            ResponseEntity<Map<String,Object>> model = restClient.get()
                    .uri(baseUrl + "/" + modelIds.get(0))
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<Map<String,Object>>() {});

            if (model.hasBody()) {
                //dump it out
                try {
                    String modelJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(model.getBody());
                    logger.info(modelJson);
                }
                catch (Exception e) {
                    logger.error(e);
                }
            }
        }
        //retrieve
        return List.of();
    }
}
