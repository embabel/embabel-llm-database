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

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AWSBedrockTaskParser implements TaskParser {

    public static final String INPUT_MODALITY_KEY = "inputModalities";
    public static final String OUTPUT_MODALITY_KEY = "outputModalities";

    private static final String INPUT_TEXT_VALUE = "TEXT";
    private static final String INPUT_IMAGE_VALUE = "IMAGE";
    private static final String INPUT_EMBEDDING_VALUE = "EMBEDDING";
    private static final String OUTPUT_TEXT_VALUE = "TEXT";
    private static final String OUTPUT_IMAGE_VALUE = "IMAGE";
    private static final String OUTPUT_EMBEDDING_VALUE = "EMBEDDING";    

    static final String[] INPUTS = {INPUT_TEXT_VALUE,INPUT_IMAGE_VALUE,INPUT_EMBEDDING_VALUE};
    static final String[] OUTPUTS = {OUTPUT_TEXT_VALUE,OUTPUT_IMAGE_VALUE,OUTPUT_EMBEDDING_VALUE};

    private List<Map<String,Object>> tasks;

    @Autowired    
    ObjectMapper objectMapper;

    @Override
    public String getTask(Map<String, Object> attributes) {
        String modelCategory = null;
        //load the categories
        if (tasks == null || tasks.isEmpty()) {
            tasks = this.getTasks(objectMapper, RESOURCE_LOCATION);
        } //end if
        //map contains 2 keys "inputModalities" and "outputModalities"
        //values of which correspond to either inputText, inputImage, outputText, outputImage (true)
        Map<String,Object> matches = new HashMap<>();
        List<String> inputValues = (List<String>) attributes.get(INPUT_MODALITY_KEY);
        List<String> outputValues = (List<String>) attributes.get(OUTPUT_MODALITY_KEY);
        for (String value : INPUTS) {
            //loop
            for (String inputValue : inputValues) {
                if (inputValue.equalsIgnoreCase(value)) {
                    //got a match --> convert
                    matches.put(getKey(value, true),true);
                } //end if
            } //end if
        } //end for
        for (String value : OUTPUTS) {
            //loop
            for (String outputValue : outputValues) {
                if (outputValue.equalsIgnoreCase(value)) {
                    //got a match --> convert
                    matches.put(getKey(value, false),true);
                } //end if
            } //end if
        } //end for        
        //now we have a matches map --> add padding values
        for (String key : KEYS) {
            if (!matches.keySet().contains(key)) {
                matches.put(key,false);//set to default of 'false'
            }//end if
        } //end for
        //now loop and check
        for (Map<String,Object> task : tasks) {
            int matchedCount = 0;
            //check
            for (String key : matches.keySet()) {
                //go through the matches key and check
                if (task.get(key).toString().equalsIgnoreCase(matches.get(key).toString())) {
                    matchedCount++;
                } //end if
            } //end for
            if (matchedCount == MATCH_COUNT) {
                //have a winner
                modelCategory = task.get("Classification").toString();
                break;        
            } //end if
        } //end for
        return modelCategory;
    }
    
    String getKey(String value,boolean input) {
        if (input) {
            if (value.equalsIgnoreCase(INPUT_IMAGE_VALUE)) {
                return INPUT_IMAGE;
            } else {
                return INPUT_TEXT;
            } //end if
        } else {
            if (value.equalsIgnoreCase(OUTPUT_IMAGE_VALUE)) {
                return OUTPUT_IMAGE;
            } else {
                return OUTPUT_TEXT;
            } //end if            
        }
    }
}
