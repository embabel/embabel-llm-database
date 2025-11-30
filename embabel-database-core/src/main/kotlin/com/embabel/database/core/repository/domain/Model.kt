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
package com.embabel.database.core.repository.domain

import java.time.LocalDate

/**
 * model representation
 */
data class Model (
    val name: String,
    val id: String,
    var tags: List<String>?,
    val knowledgeCutoff: LocalDate?,
    val releaseDate: LocalDate?,
    val parameterCount: Long,
    var organization: Organization?,
    val multiModal: Boolean,
    var modelProviders: List<ModelProvider>?,
    val description: String
)
