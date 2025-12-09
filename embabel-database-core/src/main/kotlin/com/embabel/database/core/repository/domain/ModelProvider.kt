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

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

/**
 * representation of a model provider
 */
@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["id", "provider_id"])
    ]
)
data class ModelProvider (
    @Id
    val id: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    val provider: Provider,
    val inputPerMillion: Double?,
    val outputPerMillion: Double?,
    val tags: List<String>,
    val deprecated: Boolean
) {
    @ManyToMany(mappedBy = "modelProviders")
    private val models: MutableList<Model> = mutableListOf()

    override fun equals(other: Any?): Boolean = other is ModelProvider && id == other.id

    override fun hashCode(): Int = id.hashCode()
}
