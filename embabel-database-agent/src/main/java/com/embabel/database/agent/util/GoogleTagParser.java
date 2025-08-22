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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GoogleTagParser implements TagParser {

    private List<Map<String,Object>> tasks;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public List<String> getTags(Map<String, Object> attributes) {
        //init
        String modelTask = null;
        //load up the options
        if (tasks == null || tasks.isEmpty()) {
            tasks = this.getTasks(objectMapper, RESOURCE_LOCATION);
        } //end if       
        //google response structure is
        /*
            "supportedGenerationMethods": [
                "generateContent",
                "countTokens",
                "createCachedContent"
            ]
         */
        Map<String,Object> matches = new HashMap<>();
        //convert that into the boolean map
        // List<String> methods = (List<String>) attributes.get("supportedGenerationMethods");
        // for (String method : methods) {
        if (attributes.get("name").toString().contains("embedding")) {
            //embedding model
            return Collections.singletonList("text-to-embedding"); //TODO make a bit more elegant and flexible
        } else if (attributes.get("name").toString().contains("image")) {
            //return image
            return Collections.singletonList("text-to-image-text"); //TODO make a bit more flexible
        } else {
            return Collections.singletonList("audio-video-image-text-to-text"); //TODO make a bit more flexible        
        } //end if
    }
    
}
