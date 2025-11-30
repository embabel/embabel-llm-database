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
package com.embabel.database.batch.config;

import com.embabel.agent.api.common.autonomy.AgentInvocation;
import com.embabel.agent.config.annotation.EnableAgents;
import com.embabel.agent.core.AgentPlatform;
import com.embabel.database.agent.service.*;
import com.embabel.database.core.repository.InMemoryModelRepository;
import com.embabel.database.core.repository.ModelRepository;
import com.embabel.database.core.repository.domain.Model;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestClient;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrock.BedrockClient;

@TestConfiguration
@ComponentScan(basePackageClasses = {
        com.embabel.database.agent.ModelParserAgent.class,
        com.embabel.database.batch.config.JobConfiguration.class,
        com.embabel.database.batch.config.BatchConfiguration.class
})
@EnableAgents
@EnableAutoConfiguration
public class JobConfigurationSupport {

    @Bean
    Region region(@Value("${aws.region}") String region) {
        return Region.of(region);
    }

    @Bean
    AwsBasicCredentials awsBasicCredentials(@Value("${aws.accessKeyId}") String awsAccessKey, @Value("${aws.secretAccessKey}") String awsSecretAccessKey) {
        return AwsBasicCredentials.create(awsAccessKey, awsSecretAccessKey);
    }

    @Bean
    BedrockClient bedrockClient(AwsBasicCredentials awsBasicCredentials, Region region) {
        return BedrockClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .region(region)
                .build();
    }

    @Bean
    ModelRepository modelRepository() {
        return new InMemoryModelRepository();
    }

    @Bean
    ModelParserService llmStatsModelParserService() {
        return new LlmStatsModelParserService();
    }

    @Bean
    ModelParserService bedrockModelParserService() {
        return new BedrockModelParserService();
    }

    @Bean
    RestClient restClient() {
        return RestClient.builder().build();
    }

    @Bean
    AgentInvocation<Model> agentInvocation(AgentPlatform agentPlatform) {
        return AgentInvocation.builder(agentPlatform).build(Model.class);
    }

}
