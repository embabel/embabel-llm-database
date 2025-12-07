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

import jakarta.persistence.*
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * model representation
 */
@Entity
@EntityListeners(AuditingEntityListener::class)
data class Model (
    val name: String,

    @Id
    val id: String,

    @ElementCollection
    var tags: List<String>?,
    val knowledgeCutoff: LocalDate?,
    val releaseDate: LocalDate?,
    val parameterCount: Long,

    @ManyToOne(cascade = [CascadeType.ALL])
    var organization: Organization?,
    val multiModal: Boolean,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "model_model_providers",
        joinColumns = [JoinColumn(name = "model_id")],
        inverseJoinColumns = [JoinColumn(name = "model_providers_id")]
    )
    var modelProviders: MutableList<ModelProvider> = mutableListOf(),

    @Column(length = 1048576)
    val description: String,

    @LastModifiedDate
    var lastModifiedAt: LocalDateTime? = null
)
