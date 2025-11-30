package com.embabel.database.core.repository

import com.embabel.database.core.repository.domain.Model
import com.embabel.database.core.repository.domain.Organization
import com.embabel.database.core.repository.domain.Provider

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
}