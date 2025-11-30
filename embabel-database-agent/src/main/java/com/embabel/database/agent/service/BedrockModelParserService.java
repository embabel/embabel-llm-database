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

import com.embabel.database.core.repository.ModelRepository;
import com.embabel.database.core.repository.domain.Model;
import com.embabel.database.core.repository.domain.ModelProvider;
import com.embabel.database.core.repository.domain.Organization;
import com.embabel.database.core.repository.domain.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.services.bedrock.BedrockClient;
import software.amazon.awssdk.services.bedrock.model.FoundationModelSummary;
import software.amazon.awssdk.services.bedrock.model.ModelModality;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class BedrockModelParserService implements ModelParserService {

    private static final String PROVIDER_ID = "bedrock";
    private static final String PROVIDER_NAME = "Bedrock";
    private static final String PROVIDER_WEBSITE = "https://aws.amazon.com/bedrock/";
    private static final String VIDEO = "video";
    private static final String AUDIO = "audio";
    private static final String IMAGE = "image";

    @Autowired
    BedrockClient bedrockClient;

    @Autowired
    ModelRepository modelRepository;

    @Override
    public void loadModels() {
        //use the SDK to retrieve all the models
        List<Model> models = bedrockClient.listFoundationModels(r -> {})
                .modelSummaries()
                .stream()
                .filter(foundationModelSummary -> !foundationModelSummary.outputModalities().contains(ModelModality.EMBEDDING))
                .flatMap(foundationModelSummary -> Stream.of(parse(foundationModelSummary)))
                .toList();
        modelRepository.saveAll(models);
    }

    Model parse(FoundationModelSummary foundationModelSummary) {
        //convert
        String modelId = foundationModelSummary.modelId();
        String modelName = foundationModelSummary.modelName();
        String organizationName = foundationModelSummary.providerName(); //this is analoguous to org
        //convert the modalities
        StringBuilder modalityTag = new StringBuilder();
        modalityTag.append(modalityConversion(foundationModelSummary.inputModalities()));
        modalityTag.append("-to-");
        modalityTag.append(modalityConversion(foundationModelSummary.outputModalities()));
        List<String> modalities = List.of(modalityTag.toString());

        boolean multiModal = (modalityTag.toString()
                    .toLowerCase()
                    .contains(VIDEO)
                || modalityTag.toString()
                    .toLowerCase()
                    .contains(IMAGE)
                || modalityTag.toString()
                    .toLowerCase()
                    .contains(AUDIO));

        //build the provider Object
        Provider provider = null;
        Optional<Provider> existingProvider = modelRepository.findAllProviders()
                .stream()
                .filter(p -> p.getName().equalsIgnoreCase(PROVIDER_NAME))
                .findFirst();
        //create the provider stub
        provider = existingProvider.orElseGet(() -> new Provider(PROVIDER_ID, PROVIDER_NAME, PROVIDER_WEBSITE));
        ModelProvider modelProvider = new ModelProvider(PROVIDER_ID,provider,0.0,0.0,modalities,false);//stub

        //build the organization object
        Organization organization = null;
        Optional<Organization> existingOrganization = modelRepository.findAllOrganizations()
                .stream()
                .filter(org -> org.getName().equalsIgnoreCase(organizationName))
                .findFirst();
        organization = existingOrganization.orElseGet(() -> new Organization(orgIdGenerator(organizationName),organizationName,"")); //TODO --> use an agent to clean up
        //dates
        LocalDate knowledgeCutOffDate = LocalDate.parse("1970-01-01");
        LocalDate releaseDate = knowledgeCutOffDate;
        //components of a model
        return new Model(modelName,modelId,modalities,knowledgeCutOffDate,releaseDate,0L,organization,multiModal,List.of(modelProvider),"");
    }

    String orgIdGenerator(String orgName) {
        return orgName.toLowerCase().replace(" ", "-");
    }

    String modalityConversion(List<ModelModality> modalities) {
        StringBuilder builder = new StringBuilder();
        modalities.forEach(modelModality -> {
            if (!builder.isEmpty()) {
                builder.append("-");
            }
            builder.append(modelModality.name().toLowerCase());
        });
        return builder.toString();
    }

}
