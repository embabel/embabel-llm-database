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
package com.embabel.database.agent.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LlmLeaderboardTaskParser implements TaskParser {

    static final Log logger = LogFactory.getLog(LlmLeaderboardTaskParser.class);

    private static final String INPUT_TEXT_ATTRIBUTE = "input_modality_text";
    private static final String INPUT_IMAGE_ATTRIBUTE = "input_modality_image";
    private static final String INPUT_AUDIO_ATTRIBUTE = "input_modality_audio";
    private static final String INPUT_VIDEO_ATTRIBUTE = "input_modality_video";
    private static final String OUTPUT_TEXT_ATTRIBUTE = "output_modality_text";
    private static final String OUTPUT_IMAGE_ATTRIBUTE = "output_modality_image";
    private static final String OUTPUT_AUDIO_ATTRIBUTE = "output_modality_audio";
    private static final String OUTPUT_VIDEO_ATTRIBUTE = "output_modality_video";    

    private static final String[] ATTRIBUTES = {INPUT_TEXT_ATTRIBUTE,INPUT_IMAGE_ATTRIBUTE,INPUT_AUDIO_ATTRIBUTE,INPUT_VIDEO_ATTRIBUTE,OUTPUT_TEXT_ATTRIBUTE,OUTPUT_IMAGE_ATTRIBUTE,OUTPUT_AUDIO_ATTRIBUTE,OUTPUT_VIDEO_ATTRIBUTE};

    private List<Map<String,Object>> tasks;

    private Map<String,String> attributeMap;

    @Autowired    
    ObjectMapper objectMapper;

    @Override
    public String getTask(Map<String, Object> attributes) {
        //structure is a series of input true/false and output true/false
        String modelCategory = null;
        //load the categories
        if (tasks == null || tasks.isEmpty()) {
            tasks = this.getTasks(objectMapper, RESOURCE_LOCATION);
        } //end if
        //load
        if (attributeMap == null || attributeMap.isEmpty()) {
            loadMap();
        } //end if
        Map<String,Object> matches = new HashMap<>();
        for (String attribute : ATTRIBUTES) {
            if (!attributes.keySet().contains(attribute)) {
                logger.error("missing attribute " + attribute);
                throw new IllegalArgumentException("missing attribute " + attribute);
            } //end if
            //get the value
            if (attributes.get(attribute).toString().equals("true")) {
                matches.put(attributeMap.get(attribute),true);
            } else {
                matches.put(attributeMap.get(attribute),false);
            } //end if            
        } //end for
        // now we have a map of matches, time to check which one
        for (Map<String,Object> category : tasks) {
            int matchedCount = 0;
            //check
            for (String key : matches.keySet()) {
                //go through the matches key and check
                if (category.get(key).toString().equalsIgnoreCase(matches.get(key).toString())) {
                    matchedCount++;
                } //end if
            } //end for
            if (matchedCount == MATCH_COUNT) {
                //have a winner
                modelCategory = category.get("Classification").toString();
                break;        
            } //end if
        } //end for

        return modelCategory;
    }
    
    void loadMap() {
        attributeMap = new HashMap<>();
        attributeMap.put(INPUT_TEXT_ATTRIBUTE,INPUT_TEXT);
        attributeMap.put(INPUT_IMAGE_ATTRIBUTE,INPUT_IMAGE);
        attributeMap.put(INPUT_AUDIO_ATTRIBUTE,INPUT_AUDIO);
        attributeMap.put(INPUT_VIDEO_ATTRIBUTE,INPUT_VIDEO);
        attributeMap.put(OUTPUT_TEXT_ATTRIBUTE,OUTPUT_TEXT);
        attributeMap.put(OUTPUT_IMAGE_ATTRIBUTE,OUTPUT_IMAGE);
        attributeMap.put(OUTPUT_AUDIO_ATTRIBUTE,OUTPUT_AUDIO);
        attributeMap.put(OUTPUT_VIDEO_ATTRIBUTE,OUTPUT_VIDEO);       
    }
}
