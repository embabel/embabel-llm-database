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
import java.util.Optional

class InMemoryModelRepository(models: List<Model> = emptyList()) : ModelRepository {

    private val models: MutableList<Model> = models.toMutableList()

    private var updatedTimestamp: LocalDateTime = LocalDateTime.now()

    override fun save(model: Model) {
        models.add(model)
        //update timestamp
        updatedTimestamp = LocalDateTime.now()
    }

    override fun findById(id: String): Optional<Model>? {
        return models.firstOrNull { it.id == id }?.let { Optional.of(it) }
    }

    override fun findByName(name: String): List<Model> {
        return models.filter { it.name.contains(name, ignoreCase = true) }
    }

    override fun findAll(): List<Model> {
        return models
    }

    override fun findByMultiModal(multiModal: Boolean): List<Model> {
        return models.filter { it.multiModal == multiModal }
    }


    override fun findAllProviders(): List<Provider>? {
        //get all the models
        //get the modelProviders
        val allProviders = models.flatMap { it.modelProviders ?: emptyList() }
        //build a unique list of providers
        val uniqueProviders = allProviders.distinctBy { it.provider.id }
        //return
        return uniqueProviders.map { it.provider }
    }

    override fun findAllOrganizations(): List<Organization>? {
        val allOrganizations = models.mapNotNull { it.organization }
        //unique
        val uniqueOrganizations = allOrganizations.distinctBy { it.id }
        //return
        return uniqueOrganizations;
    }

    override fun findByTags(vararg tags: String): List<Model>? {
        return models.filter {
            model -> model.tags?.any {
                tag -> tags.contains(tag) }
            ?: false }
    }

    override fun lastUpdated(): LocalDateTime {
        return updatedTimestamp
    }

    override fun findByNameAndProvider(
        name: String,
        provider: String
    ): Model? {
        return models.find { model ->
            model.name == name &&
                    model.modelProviders?.any { it.provider.name == provider } == true
        }
    }

    override fun findByNameContains(name: String): List<Model> {
        return models.filter { it.name.contains(name, ignoreCase = true) }
    }

    override fun findByProvider(provider: String): List<Model> {
        return models.filter { model ->
            model.modelProviders?.any {
                it.provider.name.equals(provider, ignoreCase = true)
            } == true
        }
    }

    override fun count(): Long {
        return models.size.toLong()
    }

    override fun reset() {
        models.clear()
    }

}
