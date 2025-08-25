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

import com.embabel.common.ai.model.ModelMetadata
import com.embabel.common.ai.model.ModelType
import org.springframework.ai.model.Model
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.embedding.EmbeddingModel
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * A repository for accessing configured AI models.
 *
 * This interface abstracts the source of AI models, which are typically defined
 * in application configuration. It provides methods for discovering and retrieving
 * models by name, type, or by a "default" designation.
 *
 * Implementations of this interface are responsible for loading and managing the
 * lifecycle of the AiModel instances.
 */
interface AiModelRepository {

    /**
     * Saves an AI model to the repository.
     *
     * @param model The [AiModel] instance to add.
     */
    fun save(model: ModelMetadata)

    /**
     * Saves a list of AI models to the repository.
     *
     * @param models The list of [AiModel] instances to add.
     */
    fun saveAll(models: List<ModelMetadata>)

    /**
     * Finds a single AI model by its unique name.
     *
     * @param name The name of the model (e.g., "gpt-4o", "text-embedding-3-small").
     * @return The corresponding [AiModel] or `null` if no model with that name is found.
     */
    fun findByName(name: String): List<ModelMetadata>?

    /**
     * Returns a list of all available AI models configured in the system.
     *
     * @return A list of all [AiModel] instances.
     */
    fun findAll(): List<ModelMetadata>

    /**
     * Finds all AI models of a specific type.
     *
     * @param type The [ModelType] to filter by (LLM or EMBEDDING).
     * @return A list of [AiModel] instances matching the specified type.
     */
    fun findAllByType(type: ModelType): List<ModelMetadata> = findAll().filter { it.type == type }

    /**
     * A convenience method to find all Large Language Models (LLMs).
     *
     * @return A list of all LLM [AiModel] instances.
     */
    fun findAllLlmModels(): List<ModelMetadata> = findAllByType(ModelType.LLM)

    /**
     * A convenience method to find all Embedding models.
     *
     * @return A list of all Embedding [AiModel] instances.
     */
    fun findAllEmbeddingModels(): List<ModelMetadata> = findAllByType(ModelType.EMBEDDING)

    /**
     * Keeps track of the last time a repository was updated
     *
     * @return LocalDateTime instance representing last update
     */
    fun lastUpdated(): LocalDateTime

    /**
     *
     * @param name The name of the model (e.g., "gpt-4o", "text-embedding-3-small").
     * @param provider The name of the model provider (e.g., "Anthropic").
     * @return The corresponding [ModelMetadata] or `null` if no model with that name is found.
     */
    fun findByNameAndProvider(name: String,provider: String): ModelMetadata?

    /**
     * function to do a "contains" search
     * @param name
     * @return A list of results if found
     */
    fun findByNameContains(name: String): List<ModelMetadata>?

    /**
     * simple function to clean up
     */
    fun deleteAll()

    /**
     * enable searching on tag type
     *
     * @param task The name of the task (e.g., "text-to-text").
     * @return List of ModelMetadata or `null`
     */
    fun findByTags(vararg tags: String): List<ModelMetadata>?


    /**
     * return the size of the model repository
     *
     * @return Int of the list size
     */
    fun count(): Int


    /**
     * get a model by the model Id
     * @param modelId
     * @return the model
     */
    fun get(modelId: String): ModelMetadata?
}
