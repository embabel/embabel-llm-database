package com.embabel.database.agent;

import com.embabel.agent.api.common.autonomy.AgentInvocation;
import com.embabel.agent.core.AgentPlatform;
import com.embabel.agent.core.AgentProcess;
import com.embabel.agent.domain.io.UserInput;
import com.embabel.database.agent.domain.ListModelMetadata;
import com.embabel.database.agent.domain.ModelProviders;
import com.embabel.database.agent.domain.TagList;
import com.embabel.database.core.repository.AiModelRepository;
import com.embabel.database.core.repository.LlmModelMetadata;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes={ModelSuggestionAgentITest.class, AgentConfigurationSupport.class}, properties = {
        "spring.ai.bedrock.aws.region=us-east-1",
        "spring.ai.model.chat=ollama",
        "aws.accessKeyId=key",
        "aws.secretAccessKey=secret"
})
public class SimpleModelSetITest {

    private static final Log logger = LogFactory.getLog(SimpleModelSetITest.class);

    @Autowired
    AiModelRepository aiModelRepository;

    @Autowired
    AgentPlatform agentPlatform;

    @Test
    void checkTags() throws Exception {
        //check how many unique tags there are
        List<String> tags = aiModelRepository.findAll()
                .stream()
                .filter(metadata -> ((LlmModelMetadata) metadata).getTags() != null)
                .flatMap(metadata -> ((LlmModelMetadata) metadata).getTags().stream())
                .distinct()
                .collect(Collectors.toList());
        logger.info("total tags " + tags.size());
        logger.info("total models " + aiModelRepository.findAll().size());
        //create a prompt and invoke
        String userInputText = "I want a model that will create an image from text";
        UserInput userInput = new UserInput(userInputText);

        //get the invocation
        AgentInvocation<ModelProviders> modelProvidersAgentInvocation = AgentInvocation.builder(agentPlatform).build(ModelProviders.class);
        //invoke
        AgentProcess agentProcess = modelProvidersAgentInvocation.run(Collections.singletonMap("userInput",userInput));
        while (!agentProcess.getFinished()) {
//
//        while (agentProcess.getStatus().equals(AgentProcessStatusCode.RUNNING) || agentProcess.getStatus().equals(AgentProcessStatusCode.WAITING)) {
//            //still waiting
            Thread.sleep(500);//wait 500ms
        }//end while

        Object result = agentProcess.lastResult();
        assertNotNull(result);
        ModelProviders modelProviders = (ModelProviders) result;
        logger.info(modelProviders.providers());
        ListModelMetadata listModelMetadata = agentProcess.getProcessContext().getBlackboard().last(ListModelMetadata.class);
        TagList tagList = agentProcess.getProcessContext().getBlackboard().last(TagList.class);
        logger.info("suggested tags " + tagList.tags().size());
        logger.info("filtered model size " + listModelMetadata.models().size());
    }
}
