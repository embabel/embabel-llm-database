package com.embabel.database.batch.config;

import com.embabel.database.batch.listener.ChunkDumpListener;
import com.embabel.database.batch.steps.LlmStatsReader;
import com.embabel.database.batch.steps.ModelProcessor;
import com.embabel.database.batch.steps.ModelReader;
import com.embabel.database.batch.steps.ModelWriter;
import com.embabel.database.core.repository.domain.Model;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobConfiguration {

    int chunkSize = 10;

    @Bean
    public Job parserAgentJob(JobRepository jobRepository, Step readModels, Step parseModel) {
        return new JobBuilder("parserAgentJob",jobRepository) //TODO externalize name
                .start(readModels) //get the list of new models
                .next(parseModel) //process them
                .listener(chunkDumpListener()) //do periodic chunks out
                .build();
    }

    @Bean
    Step parseModel(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("processNewModel",jobRepository).<String, Model>chunk(chunkSize,platformTransactionManager)
                .reader(modelReader())
                .processor(modelProcessor())
                .writer(modelWriter())
                .build();
    }

    @Bean
    Step readModels(JobRepository jobRepository,PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("readModels", jobRepository)
                .tasklet(llmStatsReader(),platformTransactionManager)
                .build();
    }

    @Bean
    @StepScope
    ModelReader modelReader() {
        return new ModelReader();
    }

    @Bean
    @StepScope
    ModelProcessor modelProcessor() {
        return new ModelProcessor();
    }

    @Bean
    @StepScope
    ModelWriter modelWriter() {
        return new ModelWriter();
    }

    @Bean
    @StepScope
    ChunkDumpListener chunkDumpListener() {
        return new ChunkDumpListener();
    }

    @Bean
    @StepScope
    LlmStatsReader llmStatsReader() {
        return new LlmStatsReader();
    }
}
