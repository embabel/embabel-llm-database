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
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.transaction.annotation.Transactional


@Transactional
class ModelService(
    private val modelRepository: ModelRepository,
    private val modelProviderRepository: ModelProviderRepository,
    private val providerRepository: ProviderRepository
) {

    private val logger: Log = LogFactory.getLog(ModelService::class.java)

    fun saveModel(model: Model): Model {
        // Load or create managed Providers first
        val managedProviders = model.modelProviders.associate { mp ->
            val providerId = mp.provider.id
            val managedProvider = providerRepository.findById(providerId)
                .orElseGet { providerRepository.save(mp.provider.copy()) }
            mp.id to modelProviderRepository.findById(mp.id)
                .orElseGet {
                    modelProviderRepository.save(mp.copy(provider = managedProvider))
                }
        }

        // Replace with managed instances in model
        val managedProvidersList = managedProviders.values.toList()
        model.modelProviders = ArrayList(managedProvidersList)

        return modelRepository.save(model)
    }

}
