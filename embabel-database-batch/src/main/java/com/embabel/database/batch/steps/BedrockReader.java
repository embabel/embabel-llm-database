package com.embabel.database.batch.steps;

import com.embabel.database.agent.service.ModelParserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

public class BedrockReader implements Tasklet {

    private static final Log logger = LogFactory.getLog(BedrockReader.class);

    @Autowired
    ModelParserService bedrockModelParserService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        logger.info("starting to process bedrock models");
        bedrockModelParserService.loadModels();
        logger.info("finished models");
        return RepeatStatus.FINISHED;
    }
}
