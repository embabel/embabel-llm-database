package com.embabel.database.agent.service;

import com.embabel.database.agent.AgentConfigurationSupport;
import com.embabel.database.core.repository.ModelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes={BedrockModelParserServiceITest.class, AgentConfigurationSupport.class}, properties = {
        "spring.ai.bedrock.aws.region=us-east-1",
        "spring.ai.model.chat=none",
        "embabel.models.default-llm=us.anthropic.claude-3-5-sonnet-20240620-v1:0",
        "embabel.database.test=true"
})
@ActiveProfiles("no-auto-load")
public class BedrockModelParserServiceITest {

    @Autowired
    ModelParserService bedrockModelParserService;

    @Autowired
    ModelRepository modelRepository;

    @Test
    void testLoadModels() {
        //check before
        assertTrue(modelRepository.findAll().isEmpty());
        //invoke
        bedrockModelParserService.loadModels();
        //check after
        assertFalse(modelRepository.findAll().isEmpty());
    }
}
