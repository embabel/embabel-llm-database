package com.embabel.database.agent.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LlmStatsModelParserTest {

    //parse the modality
    @Test
    void testChooseTag() throws Exception {
        //create a map
        /*
        "modalities": {
        "input": {
          "text": true,
          "image": true,
          "audio": true,
          "video": true
        },
        "output": {
          "text": true,
          "image": false,
          "audio": false,
          "video": false
        }
      }
         */
        String modalitiesString = "{\"modalities\": {\n" +
                "        \"input\": {\n" +
                "          \"text\": true,\n" +
                "          \"image\": true,\n" +
                "          \"audio\": true,\n" +
                "          \"video\": true\n" +
                "        },\n" +
                "        \"output\": {\n" +
                "          \"text\": true,\n" +
                "          \"image\": false,\n" +
                "          \"audio\": false,\n" +
                "          \"video\": false\n" +
                "        }\n" +
                "      }\n" +
                "    }";
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> modalities = objectMapper.readValue(modalitiesString, new TypeReference<Map<String, Object>>() { });

        //now test
        String tag = new LlmStatsModelParser().chooseTag(modalities);
        assertEquals("audio-video-image-text-to-text",tag);

    }
}
