package com.embabel.database.core.repository

import com.embabel.database.core.repository.domain.Provider

interface ProviderRepository {

    fun save(model: Provider)

    fun saveAll(models: List<Provider>)

    fun findByName(name: String): List<Provider>

    fun findAll(): List<Provider>

    fun count(): Int

    fun findById(providerId: String): Provider?
}