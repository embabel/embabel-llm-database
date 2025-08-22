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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LlmLeaderboardTagParser implements TagParser {

    static final Log logger = LogFactory.getLog(LlmLeaderboardTagParser.class);

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
    public List<String> getTags(Map<String, Object> attributes) {
        //structure is a series of input true/false and output true/false
        List<String> tags = new ArrayList<>();
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
            if (attributes.get(attribute).toString().equals(Boolean.TRUE.toString().toLowerCase())) {
                matches.put(attributeMap.get(attribute),true);
            } else {
                matches.put(attributeMap.get(attribute),false);
            } //end if            
        } //end for

        // now we have a map of matches, time to check which ones it maps too
        // have a single set of true/falses that could map to a number of different model tasks
        // can map the "inputs" and "outputs" separately to get either side of the tag and present multiple combinations
        for (Map<String,Object> category : tasks) {
            //get the exact match
            String exactMatch = getExactMatchTag(category, matches);
            if (exactMatch != null) {
                tags.add(exactMatch);
            } else {
                //now we have "sub" tags
                //this is where an input and an output are true but not ALL of the inputs and outpus match
                //inputs determine tag, not outputs (restrictions on what can be uploaded)
                //process inputs to limit the options
                int inputCounts = 0;
                int outputCounts = 0;
                //limitations of the tag are what can be uploaded, and the expected outputs
                //an image-text-to-text can just also be text-to-text and image-to-text, but it can't be text-to-image
                int trueOutputs = 0;
                int trueInputs = 0;
                boolean noMatch = false;
                //need to check how output matches
                for (Map.Entry<String, Object> entry : category.entrySet()) {
                    String key = entry.getKey().toLowerCase();
                    Object value = entry.getValue();
                    Object matchValue = matches.get(entry.getKey());

                    // Normalize values once
                    boolean isTrue = Boolean.parseBoolean(String.valueOf(value));
                    boolean matchIsTrue = matchValue != null && Boolean.parseBoolean(String.valueOf(matchValue));

                    if (key.contains(OUTPUT)) {
                        if (isTrue && matchIsTrue) {
                            trueOutputs++;
                        } else if (isTrue && (matchValue == null || matchIsTrue)) {
                            noMatch = true;
                        }
                    } else if (key.contains(INPUT) && isTrue && matchIsTrue) {
                        trueInputs++;
                    } //end if
                }
                if (noMatch) {
                    continue; //done here
                }

                for (String input : INPUTS) {
                    //check if there are ANY matches
                    if (matches.get(input).toString().equalsIgnoreCase(Boolean.TRUE.toString().toLowerCase())) {
                        inputCounts++;
                    } //end if
                }//end for
                //check 
                if (inputCounts <= 0) {
                    //no matches, skip
                    continue;
                } //end if
                //check the outputs
                for (String output : OUTPUTS) {
                    //check if there are ANY matches
                    if (matches.get(output).toString().equalsIgnoreCase(Boolean.TRUE.toString().toLowerCase())) {
                        outputCounts++;
                    } //end if
                }//end for 
                if (outputCounts <= 0) {
                    //no matches, skip
                    continue;
                }//end if
                //match counts
                if (trueInputs >= 1 && trueOutputs >= 1) {
                    //can use this tone
                    tags.add(category.get(TAG_LABEL).toString());
                } //end if
            } //end if
        } //end for
        //return
        return tags;
    }

    String getExactMatchTag(Map<String,Object> category,Map<String,Object> matches) {
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
            return category.get(TAG_LABEL).toString();
        }
        return null;
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
