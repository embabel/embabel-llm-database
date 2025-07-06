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
package com.embabel.database.agent.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.embabel.common.ai.model.ModelMetadata;
import com.embabel.database.agent.util.ModelMetadataParser;

@Service
public class LlmLeaderboardModelMetadataDiscoveryService implements ModelMetadataDiscoveryService {

    private static final Log logger = LogFactory.getLog(LlmLeaderboardModelMetadataDiscoveryService.class);

    @Value("${embabel.provider.repository:https://github.com/JonathanChavezTamales/llm-leaderboard/}")
    private String url;

    ModelMetadataParser modelMetadataParser;    

    public LlmLeaderboardModelMetadataDiscoveryService(ModelMetadataParser modelMetadataParser) {
        this.modelMetadataParser = modelMetadataParser;
    }

    @Override
    public List<ModelMetadata> retrieveModelMetadata() {
        //init
        List<ModelMetadata> listModelMetadata = new ArrayList<>();
        //retrieve through Git
        // create a temporary directory
        try {
            Path tempDir = Files.createTempDirectory("git-clone-");
            // clone
            Git git = Git.cloneRepository()
                .setURI(url)
                .setDirectory(tempDir.toFile())
                .call();
            git.close();    
            //parse
            listModelMetadata = modelMetadataParser.parse(tempDir);
        }
        catch (Exception e) {
            logger.error("failed to clone",e);
        }
        //return
        return listModelMetadata;
    }
    
}
