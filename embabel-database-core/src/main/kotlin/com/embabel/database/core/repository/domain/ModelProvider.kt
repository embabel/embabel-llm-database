package com.embabel.database.core.repository.domain

/**
 * representation of a model provider
 */
data class ModelProvider (
    val id: String,
    val provider: Provider,
    val inputPerMillion: Double?,
    val outputPerMillion: Double?,
    val tags: List<String>,
    val deprecated: Boolean,
)