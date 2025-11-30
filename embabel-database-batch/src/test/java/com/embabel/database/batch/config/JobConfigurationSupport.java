package com.embabel.database.batch.config;

import com.embabel.agent.api.common.autonomy.AgentInvocation;
import com.embabel.agent.config.annotation.EnableAgents;
import com.embabel.agent.core.AgentPlatform;
import com.embabel.database.agent.service.*;
import com.embabel.database.agent.util.LlmLeaderboardTagParser;
import com.embabel.database.agent.util.LlmStatsModelMetadataParser;
import com.embabel.database.agent.util.ModelMetadataParser;
import com.embabel.database.agent.util.TagParser;
import com.embabel.database.core.repository.AiModelRepository;
import com.embabel.database.core.repository.InMemoryAiModelRepository;
import com.embabel.database.core.repository.InMemoryModelRepository;
import com.embabel.database.core.repository.ModelRepository;
import com.embabel.database.core.repository.domain.Model;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
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
    AgentManagementService agentManagementService() {
        return new AgentManagementService();
    }

    @Bean
    SessionManagementService sessionManagementService() {
        return new InMemorySessionManagementService();
    }

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

    @Bean
    AiModelRepository aiModelRepository() {
        return new InMemoryAiModelRepository();
    }

    @Bean
    TagParser tagParser() {
        return new LlmLeaderboardTagParser();
    }

    @Bean
    ModelMetadataParser modelMetadataParser(ObjectMapper objectMapper, TagParser tagParser) {
        return new LlmStatsModelMetadataParser(objectMapper,tagParser);
    }

}
