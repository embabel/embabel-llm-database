package com.embabel.database.agent.domain;

import java.util.List;

public record ModelProviders(String message, List<String> providers) {

    public ModelProviders {
        if (message == null || message.isEmpty()
                || providers == null || providers.isEmpty()) {
            throw new IllegalArgumentException("incorrectly constructed");
        }
    }
}
