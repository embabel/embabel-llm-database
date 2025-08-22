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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * interface for parsing the categories into a single string
 */
public interface TagParser {

    static final Log logger = LogFactory.getLog(TagParser.class);

    public static final int MATCH_COUNT = 8;
    
    public static final String INPUT = "input";
    public static final String OUTPUT = "output";
    public static final String TAG_LABEL = "tag";
    public static final String INPUT_TEXT = "inputText";
    public static final String INPUT_AUDIO = "inputAudio";
    public static final String INPUT_VIDEO = "inputVideo";
    public static final String INPUT_IMAGE = "inputImage";
    public static final String OUTPUT_TEXT = "outputText";
    public static final String OUTPUT_AUDIO = "outputAudio";
    public static final String OUTPUT_VIDEO = "outputVideo";
    public static final String OUTPUT_IMAGE = "outputImage";    

    public static final String[] KEYS = {INPUT_TEXT,INPUT_AUDIO,INPUT_VIDEO,INPUT_IMAGE,OUTPUT_TEXT,OUTPUT_AUDIO,OUTPUT_VIDEO,OUTPUT_IMAGE};
    public static final String[] INPUTS = {INPUT_TEXT,INPUT_AUDIO,INPUT_VIDEO,INPUT_IMAGE};
    public static final String[] OUTPUTS = {OUTPUT_TEXT,OUTPUT_AUDIO,OUTPUT_VIDEO,OUTPUT_IMAGE};

    public static final String RESOURCE_LOCATION = "/data/tag_types.json";

    List<String> getTags(Map<String,Object> attributes);

    /**
     * reusable helper
     * @param objectMapper
     * @param resourceLocation
     * @return
     */
    default List<Map<String,Object>> getTasks(ObjectMapper objectMapper,String resourceLocation) {
        List<Map<String,Object>> tasks = new ArrayList<>();
        //load from the classpath
        try (InputStream input = this.getClass().getResourceAsStream(resourceLocation)) {
            if (input == null) {
                throw new IllegalArgumentException("couldn't load task types");
            }
            tasks = objectMapper.readValue(input, new TypeReference<List<Map<String,Object>>>(){});
        } catch (Exception e) {
            logger.error("error retrieveing categories",e);
        }
        //return
        return tasks;
    }
}
