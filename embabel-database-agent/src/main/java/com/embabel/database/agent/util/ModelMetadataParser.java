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

import java.nio.file.Path;
import java.util.List;

import com.embabel.common.ai.model.ModelMetadata;

public interface ModelMetadataParser {
    
    /**
     * parse a json string to a list of ModelMetadata
     * @param json
     * @return
     */
    List<ModelMetadata> parse(String json);


    /**
     * override to parse from a specific location
     * @param path
     * @return
     */
    List<ModelMetadata> parse(Path path);

}
