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
public interface CategoryParser {

    static final Log logger = LogFactory.getLog(CategoryParser.class);
    
    public static final String RESOURCE_LOCATION = "classpath:/resources/data/task_types.json";

    String getCategory(Map<String,String> attributes);

    default List<Map<String,Boolean>> getCategories(ObjectMapper objectMapper,String resourceLocation) {
        List<Map<String,Boolean>> categories = new ArrayList<>();
        //load from the classpath
        try (InputStream input = this.getClass().getResourceAsStream(resourceLocation)) {
            if (input == null) {
                throw new IllegalArgumentException("couldn't load task types");
            }
            categories = objectMapper.readValue(input, new TypeReference<List<Map<String,Boolean>>>(){});
        } catch (Exception e) {
            logger.error("error retrieveing categories",e);
        }
        
        return categories;
    }
}
