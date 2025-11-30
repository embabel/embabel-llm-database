package com.embabel.database.agent.domain;

public record ModelSuggestion(String message,ListModelMetadata listModelMetadata) {

    public ModelSuggestion {
        if (message == null || message.isEmpty() || listModelMetadata == null) {
            throw new IllegalArgumentException("incorrectly constructed");
        }
    }
}
