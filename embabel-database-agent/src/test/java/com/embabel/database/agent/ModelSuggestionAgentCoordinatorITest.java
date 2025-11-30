package com.embabel.database.agent;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes={ModelProviderSuggestionAgentITest.class, AgentConfigurationSupport.class}, properties = {
        "spring.ai.bedrock.aws.region=us-east-1",
        "spring.ai.model.chat=ollama",
        "aws.accessKeyId=key",
        "aws.secretAccessKey=secret"
})
public class ModelSuggestionAgentCoordinatorITest {
    private static final Log logger = LogFactory.getLog(ModelSuggestionAgentCoordinatorITest.class);

    void testHasProvider() {

    }
}
