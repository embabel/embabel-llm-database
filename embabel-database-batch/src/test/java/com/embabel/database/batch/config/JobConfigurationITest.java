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

import com.embabel.database.core.repository.ModelRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes={JobConfigurationITest.class,JobConfigurationSupport.class}, properties = {
        "spring.ai.bedrock.aws.region=us-east-1",
        "spring.ai.model.chat=ollama",
        "embabel.models.default-llm=us.anthropic.claude-3-5-sonnet-20240620-v1:0"
})
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
            Thread.sleep(1000);
            jobExecution = jobExplorer.getJobExecution(jobExecutionid);
            jobExecution.getStepExecutions().forEach(stepExecution -> {
                if (stepExecution.getExecutionContext().containsKey("currentCount")) {
                    logger.info("current count " + stepExecution.getExecutionContext().get("currentCount"));
                }
            });
            logger.info("post check");
        } //end while
        //complete
        //check
        assertFalse(modelRepository.findAll().isEmpty());
    }

}
