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
package com.embabel.database.core.repository

import com.embabel.database.core.repository.domain.Model
import com.embabel.database.core.repository.domain.Organization
import com.embabel.database.core.repository.domain.Provider
import java.time.LocalDateTime

interface ModelRepository {

    fun save(model: Model)

    fun saveAll(models: List<Model>)

    fun findByName(name: String): List<Model>

    fun findAll(): List<Model>

    fun findByMultiModal(): List<Model>

    fun count(): Int

    fun findById(modelId: String): Model?

    fun findAllProviders(): List<Provider>?

    fun findAllOrganizations(): List<Organization>?

    fun findByTags(vararg tags: String): List<Model>?

    fun deleteAll()

    fun lastUpdated(): LocalDateTime

    fun findByNameAndProvider(name: String, provider: String): Model?

    fun findByNameContains(name: String): List<Model>?

    fun findByProvider(provider: String): List<Model>?
}
