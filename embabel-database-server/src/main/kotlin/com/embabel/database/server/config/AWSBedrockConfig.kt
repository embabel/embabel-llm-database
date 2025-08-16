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
package com.embabel.database.server.config

import jakarta.annotation.PostConstruct

import com.embabel.common.ai.model.ModelMetadata
import com.embabel.database.agent.service.AWSBedrockModelMetadataDiscoveryService
import com.embabel.database.agent.service.ModelMetadataDiscoveryService
import com.embabel.database.agent.util.AWSBedrockParser
import com.embabel.database.agent.util.AWSBedrockTaskParser
import com.embabel.database.agent.util.ModelMetadataParser
import com.embabel.database.agent.util.TaskParser

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.bedrock.BedrockClient

@Profile("aws")
@Configuration
class AWSBedrockConfig {

    //AWS region
    @Bean
    fun region(@Value("\${aws.region}")region: String):Region  {
        return Region.of(region);
    }

    //AWS credentials
    @Bean
    fun awsBasicCredentials(@Value("\${aws.accessKeyId}") awsAccessKey: String,@Value("\${aws.secretAccessKey}") awsSecretAccessKey: String): AwsBasicCredentials {
        return AwsBasicCredentials.create(awsAccessKey, awsSecretAccessKey);
    }

    //AWS Bedrock client
    @Bean
    fun bedrockClient(awsBasicCredentials: AwsBasicCredentials, region: Region): BedrockClient {
        return BedrockClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .region(region)
                .build();
    }

    //AWS task Parser
    @Bean
    fun awsBedrockTaskParser(): TaskParser {
        return AWSBedrockTaskParser()
    }

    //AWS model parser
    @Bean
    fun awsModelMetadataParser(): ModelMetadataParser {
        return AWSBedrockParser()
    }

    //AWS discovery service
    @Bean
    fun awsModelMetadataDiscoveryService(awsModelMetadataParser: ModelMetadataParser, bedrockClient: BedrockClient, region: Region): ModelMetadataDiscoveryService {
        return AWSBedrockModelMetadataDiscoveryService(awsModelMetadataParser,bedrockClient,region)
    }

    @Value("\${aws.region}")
    private lateinit var regionString: String


    @PostConstruct
    fun init() {
        // set a system property to fix region discovery error
        System.setProperty("aws.region",regionString)
    }

}
