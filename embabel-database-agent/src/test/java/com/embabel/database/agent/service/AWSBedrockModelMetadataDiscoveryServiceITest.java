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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import com.embabel.common.ai.model.ModelMetadata;
import com.embabel.database.agent.util.AWSBedrockParser;
import com.embabel.database.agent.util.AWSBedrockTaskParser;
import com.embabel.database.agent.util.ModelMetadataParser;
import com.embabel.database.agent.util.TaskParser;
import com.embabel.database.core.repository.LlmModelMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrock.BedrockClient;

/**
 * To run this test you need;
 * - AWS Access ID
 * - AWS Secret Key
 * 
 * AWS Role used in testing is AmazonBedrockReadOnly
 * 
 * Example command
 * mvn clean test -Dtest=com.embabel.database.agent.service.AWSBedrockModelMetadataDiscoveryServiceITest -Daws.region=us-east-1 -Daws.accessKeyId=[your key] -Daws.secretAccessKey=[your secret] -pl embabel-database-agent
 */


@SpringBootTest(classes={AWSBedrockModelMetadataDiscoveryServiceITest.class,AWSBedrockModelMetadataDiscoveryServiceITest.TestConfig.class})
@ActiveProfiles("aws")
public class AWSBedrockModelMetadataDiscoveryServiceITest {

    private static final Log logger = LogFactory.getLog(AWSBedrockModelMetadataDiscoveryServiceITest.class);

    @Autowired
    AWSBedrockModelMetadataDiscoveryService awsBedrockModelMetadataDiscoveryService;

    @Test
    void testRetrieveModelMetadata() {
        //invoke
        List<ModelMetadata> results = awsBedrockModelMetadataDiscoveryService.retrieveModelMetadata();
        //test
        assertNotNull(results);
        assertFalse(results.isEmpty());
    }


    @TestConfiguration
    public static class TestConfig {

        @Bean
        public Region region(@Value("${aws.region}") String region) {
            return Region.of(region);
        }

        @Bean
        public AwsBasicCredentials awsBasicCredentials(@Value("${aws.accessKeyId}") String awsAccessKey,@Value("${aws.secretAccessKey}") String awsSecretAccessKey) {
            return AwsBasicCredentials.create(awsAccessKey, awsSecretAccessKey);
        }

        @Bean
        public BedrockClient bedrockClient(AwsBasicCredentials awsBasicCredentials, Region region) {
            return BedrockClient.builder()
                    .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                    .region(region)
                    .build();
        }

        @Bean
        public ModelMetadataParser modelMetadataParser() {
            return new AWSBedrockParser();
        }

        @Bean
        public TaskParser awsBedrockTaskParser() {
            return new AWSBedrockTaskParser();
        }        

        @Bean
        public AWSBedrockModelMetadataDiscoveryService awsBedrockModelMetadataDiscoveryService(ModelMetadataParser modelMetadataParser, BedrockClient bedrockClient, Region region) {
            return new AWSBedrockModelMetadataDiscoveryService(modelMetadataParser, bedrockClient, region);
        }

        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }

    }
}
