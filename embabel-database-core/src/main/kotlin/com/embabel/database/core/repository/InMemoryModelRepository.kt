package com.embabel.database.core.repository

import com.embabel.database.core.repository.domain.Model
import com.embabel.database.core.repository.domain.ModelProvider
import com.embabel.database.core.repository.domain.Organization
import com.embabel.database.core.repository.domain.Provider
import com.fasterxml.jackson.databind.ObjectMapper

import java.time.LocalDateTime

class InMemoryModelRepository(models: List<Model> = emptyList()) : ModelRepository {

    private val models: MutableList<Model> = models.toMutableList()

    private var updatedTimestamp: LocalDateTime = LocalDateTime.now()

    override fun save(model: Model) {
        models.add(model)
        //update timestamp
        updatedTimestamp = LocalDateTime.now()
    }

    override fun saveAll(models: List<Model>) {
        for (model in models) {
            save(model)
        }
        //update timestamp
        updatedTimestamp = LocalDateTime.now()
    }

    override fun findByName(name: String): List<Model> {
        return models.filter { it.name.contains(name, ignoreCase = true) }
    }

    override fun findAll(): List<Model> {
        return models
    }

    override fun findByMultiModal(): List<Model> {
        return models.filter { it.multiModal }
    }

    override fun count(): Int {
        return models.size
    }

    override fun findById(modelId: String): Model? {
        return models.find { it.id == modelId }
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


}