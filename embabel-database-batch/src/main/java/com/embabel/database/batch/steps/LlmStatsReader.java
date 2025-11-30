package com.embabel.database.batch.steps;

import com.embabel.database.core.repository.ModelRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class LlmStatsReader implements Tasklet {

    private static final Log logger = LogFactory.getLog(LlmStatsReader.class);

    private static final String MODELS = "models";
    private static final String MODEL_ID = "model_id";

    @Autowired
    RestClient restClient;

    @Value("${embabel.database.source.llmstats.list:https://api.zeroeval.com/leaderboard/models/list}")
    String modelListUrl;

    @Autowired
    ModelRepository modelRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<String> newModelIds = restClient.get()
                .uri(modelListUrl)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<Map<String, Object>>>() { })
                .getBody()
                .stream()
                .map(org -> (List<Map<String, Object>>) org.get(MODELS))
                .flatMap(List::stream).map(model -> {
                    Object id = model.get(MODEL_ID);
                    return id != null ? id.toString() : null;
                })
                .filter(Objects::nonNull)
                .filter(modelId -> modelRepository.findById(modelId) == null)
                .collect(Collectors.toList());
        //check the list
        if (newModelIds.isEmpty()) {
            logger.info("No new model ids");
            //stop the job
            chunkContext.getStepContext().getStepExecution().setExitStatus(ExitStatus.STOPPED);
            chunkContext.getStepContext().getStepExecution().setTerminateOnly();//done
            return RepeatStatus.FINISHED;
        } //end if
        logger.info("New model ids " + newModelIds.size());
        //convert to a queue

        //set into the context
        contribution.getStepExecution()
                .getJobExecution()
                .getExecutionContext()
                .put("newModelList",new LinkedList<>(newModelIds));
        //done
        return RepeatStatus.FINISHED;
    }

}
