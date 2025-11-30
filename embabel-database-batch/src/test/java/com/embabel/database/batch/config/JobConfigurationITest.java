package com.embabel.database.batch.config;

import com.embabel.database.core.repository.ModelRepository;
import com.embabel.database.core.repository.domain.Model;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.FileOutputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes={JobConfigurationITest.class,JobConfigurationSupport.class}, properties = {
        "spring.ai.bedrock.aws.region=us-east-1",
        "spring.ai.model.chat=ollama",
        "embabel.models.default-llm=us.anthropic.claude-3-5-sonnet-20240620-v1:0"
})
@ActiveProfiles("no-auto-load")
public class JobConfigurationITest {

    private static final Log logger = LogFactory.getLog(JobConfigurationITest.class);

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    JobExplorer jobExplorer;

    @Autowired
    Job job;

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testJobRun() throws Exception {
        //check model Repo before
        assertTrue(modelRepository.findAll().isEmpty());

        JobParameters jobParameters = new JobParameters();//none to set
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        long jobExecutionid = jobExecution.getId();
        //poll
        while (jobExecution.getStatus().isRunning()) {
            //wait
            Thread.sleep(5000);
            jobExecution = jobExplorer.getJobExecution(jobExecutionid);
        }
        //complete
        //check
        assertFalse(modelRepository.findAll().isEmpty());

    }

}
