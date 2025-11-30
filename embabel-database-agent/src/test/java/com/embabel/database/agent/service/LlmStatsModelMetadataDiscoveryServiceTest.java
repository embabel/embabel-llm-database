package com.embabel.database.agent.service;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LlmStatsModelMetadataDiscoveryServiceTest {

    @Test
    void testRetrieveModelMetadata() {
        LlmStatsModelMetadataDiscoveryService llmStatsModelMetadataDiscoveryService = new LlmStatsModelMetadataDiscoveryService();

        ReflectionTestUtils.setField(llmStatsModelMetadataDiscoveryService,"baseUrl","https://api.zeroeval.com/leaderboard/models");
        ReflectionTestUtils.setField(llmStatsModelMetadataDiscoveryService,"objectMapper",new ObjectMapper());

        llmStatsModelMetadataDiscoveryService.retrieveModelMetadata();
    }
}
