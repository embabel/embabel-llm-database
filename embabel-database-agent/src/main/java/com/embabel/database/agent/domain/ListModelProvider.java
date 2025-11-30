package com.embabel.database.agent.domain;

import com.embabel.database.core.repository.domain.ModelProvider;

import java.util.List;

public record ListModelProvider(List<ModelProvider> modelProviders) {

    public ListModelProvider {
        if (modelProviders == null || modelProviders.isEmpty()) {
            throw new IllegalArgumentException("incorrectly constructed");
        }
    }

}
