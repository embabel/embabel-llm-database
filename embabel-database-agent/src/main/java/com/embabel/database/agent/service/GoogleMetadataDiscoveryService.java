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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

import com.embabel.common.ai.model.ModelMetadata;
import com.embabel.database.agent.util.ModelMetadataParser;

public class GoogleMetadataDiscoveryService implements ModelMetadataDiscoveryService {

    @Value("${google.models.base-url:https://generativelanguage.googleapis.com}")
    private String googleModelsBaseUrl;

    @Value("${google.models.url:/v1/models?key=}")
    private String googleModelsUrl;

    @Value("${google.models.api-key}")
    private String apiKey;

    private ModelMetadataParser googleModelMetadataParser;

    private WebClient webClient;

    public GoogleMetadataDiscoveryService() {}

    public GoogleMetadataDiscoveryService(ModelMetadataParser googleModelMetadataParser) {
        this.googleModelMetadataParser = googleModelMetadataParser;
    }

    @Override
    public List<ModelMetadata> retrieveModelMetadata() {
        // init
        List<ModelMetadata> listModelMetadata = new ArrayList<>();   
        //retrieve the objects from the endpoint
        //build the final urls
        String url = googleModelsUrl + apiKey;
        //test
        if (webClient == null) {
            webClient = WebClient.builder().baseUrl(googleModelsBaseUrl).build();
        } //end if
        String json = webClient.get()
                        .uri(url)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
        //convert to n accessible object
        listModelMetadata = googleModelMetadataParser.parse(json);
        //return
        return listModelMetadata;
    }

}