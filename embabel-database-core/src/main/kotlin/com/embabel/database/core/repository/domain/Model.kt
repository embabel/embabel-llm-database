package com.embabel.database.core.repository.domain

import java.time.LocalDate

/**
 * model representation
 */
data class Model (
    val name: String,
    val id: String,
    var tags: List<String>,
    val knowledgeCutoff: LocalDate?,
    val releaseDate: LocalDate?,
    val parameterCount: Long,
    var organization: Organization?,
    val multiModal: Boolean,
    var modelProviders: List<ModelProvider>?,
    val description: String
)