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
import com.embabel.common.ai.model.PricingModel;
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.fasterxml.jackson.core.type.TypeReference
import org.springframework.ai.model.Model
import org.springframework.ai.embedding.EmbeddingModel
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import org.slf4j.LoggerFactory


/**
 * An in-memory implementation of the [AiModelRepository].
 *
 * This repository is initialized with a static list of models and stores them
 * in a HashMap for efficient retrieval. It is well-suited for testing or for
 * applications with a fixed, programmatically-defined set of AI models.
 *
 * The "default" LLM and Embedding models are determined by their order in the
 * list provided to the constructor. The first model of type `LLM` will be the default
 * LLM, and the first of type `EMBEDDING` will be the default embedding model.
 *
 * @param allModels The complete list of AiModel instances to be managed by this repository.
 */
class InMemoryAiModelRepository(allModels: List<ModelMetadata> = emptyList()) : AiModelRepository {

    private val logger = LoggerFactory.getLogger(InMemoryAiModelRepository::class.java)

    companion object {
        private const val MODEL_FILE: String = "models.json"
        private const val MODEL_FILE_PATH: String = "./data"
    }

    /**
     * A list of all models.
     */
    // private val modelsByName: MutableMap<String, ModelMetadata> = allModels.associateBy { it.name }.toMutableMap()
    private val models: MutableList<ModelMetadata> = allModels.toMutableList()

    /**
     * simple local date time hold
     */
    private var updatedTimestamp: LocalDateTime = LocalDateTime.now()


    override fun findByName(name: String): List<ModelMetadata>? {
        return models.filter { it.name == name }
    }

    override fun findAll(): List<ModelMetadata> {
        return models
    }

    override fun save(model: ModelMetadata) {
        models.add(model)
        //update timestamp
        updatedTimestamp = LocalDateTime.now()
    }

    override fun saveAll(models: List<ModelMetadata>) {
        for (model in models) {
            save(model)
        }//end for
        //save
        flushToFile()
        //update timestamp
        updatedTimestamp = LocalDateTime.now()
    }

    override fun lastUpdated(): LocalDateTime {
        return updatedTimestamp
    }

    override fun findByNameAndProvider(name: String,provider: String): ModelMetadata? {
        return models.firstOrNull { it.name == name && it.provider == provider }
    }

    override fun deleteAll() {
        models.clear()
    }

    override fun findByTags(vararg tags: String): List<ModelMetadata>? {
        val results = models.filterIsInstance<LlmModelMetadata>()
                            .filter { model -> model.tags?.any { it in tags } == true }
        return if (results.isEmpty()) null else results
    }

    override fun count(): Int {
        return models.size
    }

    override fun findByNameContains(name: String): List<ModelMetadata>? {
        return models.filter { it.name.contains(name,ignoreCase = true) }
    }

    override fun get(modelId: String): ModelMetadata? {
        return models.find { it.modelId == modelId }
    }

    //serialize the current list to json
    fun flushToFile() {
        // Create and configure the ObjectMapper
        val objectMapper = jacksonObjectMapper().registerKotlinModule()
            .registerModule(JavaTimeModule())
        // Serialize the object to JSON string
        // val jsonString = Json.encodeToString(models)
        val jsonString = objectMapper.writeValueAsString(models)
        // Ensure the 'data' directory exists
        val dir = File(MODEL_FILE_PATH)
        if (!dir.exists()) dir.mkdirs()
        // Write the JSON string to the file in the designated directory
        File(dir, MODEL_FILE).writeText(jsonString)
    }

    //function to archive the current state
    fun archive() {
        //timestamp
        val timestamp = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm")
        val fileTimestamp = "." + timestamp.format(formatter)
        //move the file
        val current = File(MODEL_FILE_PATH,MODEL_FILE)
        val archiveFile = File(MODEL_FILE_PATH,MODEL_FILE + fileTimestamp)
        current.renameTo(archiveFile)
    }

    //run on startup to load from a local data file
    fun load() {
        //init objectmapper
        val objectMapper = jacksonObjectMapper()
            .registerKotlinModule()
            .registerModule(JavaTimeModule())
        val file = File(MODEL_FILE_PATH,MODEL_FILE)
        // Check if the file exists before reading
        if (!file.exists()) {
            // handle non-existence
            logger.warn("No model file to load from")
            return
        } //end if
        val jsonString = file.readText(Charsets.UTF_8)
        //get the object
        val modelsFromFile: List<Map<String,Any>> = objectMapper.readValue(jsonString,object : TypeReference<List<Map<String, Any>>>() {})
        //convert
        val modelList = mapToList(modelsFromFile)
        //now write
        models.clear() //reset
        models.addAll(modelList)
    }

    //TODO remove this when updated ModelMetadata serialization
    fun mapToList(
        maps: List<Map<String, Any>>
    ): List<LlmModelMetadata> {
        return maps.mapNotNull { map ->
            val name = map["name"] as? String ?: return@mapNotNull null
            val provider = map["provider"] as? String ?: return@mapNotNull null

            val knowledgeCutoffDate = when (val dateValue = map["knowledgeCutoffDate"]) {
                is LocalDate -> dateValue
                is String -> runCatching { LocalDate.parse(dateValue) }.getOrNull()
                else -> null
            }

            // Parse pricingModel using various supported keys/shapes
            val pricingModel = when (val pm = map["pricingModel"]) {
                is PricingModel -> pm

                is String -> when (pm) {
                    "ALL_YOU_CAN_EAT" -> PricingModel.ALL_YOU_CAN_EAT
                    else -> null // Unknown string, adjust here if you have enums or named types
                }

                is Map<*, *> -> {
                    // Per-token pricing: expects keys usdPerInputToken and usdPerOutputToken (Double or String)
                    val perTokenInput = pm["usdPerInputToken"]
                    val perTokenOutput = pm["usdPerOutputToken"]
                    if (perTokenInput != null && perTokenOutput != null) {
                        val inUsd = (perTokenInput as? Number)?.toDouble()
                            ?: (perTokenInput as? String)?.toDoubleOrNull()
                        val outUsd = (perTokenOutput as? Number)?.toDouble()
                            ?: (perTokenOutput as? String)?.toDoubleOrNull()
                        if (inUsd != null && outUsd != null) {
                            PricingModel.usdPerToken(inUsd, outUsd)
                        } else null
                    }
                    // Per-1M-token pricing: expects keys usdPer1MInputTokens and usdPer1MOutputTokens
                    else {
                        val perMInput = pm["usdPer1MInputTokens"]
                        val perMOutput = pm["usdPer1MOutputTokens"]
                        if (perMInput != null && perMOutput != null) {
                            val inUsd = (perMInput as? Number)?.toDouble()
                                ?: (perMInput as? String)?.toDoubleOrNull()
                            val outUsd = (perMOutput as? Number)?.toDouble()
                                ?: (perMOutput as? String)?.toDoubleOrNull()
                            if (inUsd != null && outUsd != null) {
                                PricingModel.usdPer1MTokens(inUsd, outUsd)
                            } else null
                        } else null
                    }
                }

                else -> null
            }

            val size = when (val sizeValue = map["size"]) {
                is Long -> sizeValue
                is Int -> sizeValue.toLong()
                is String -> sizeValue.toLongOrNull()
                else -> null
            }

            LlmModelMetadata(
                name = name,
                provider = provider,
                knowledgeCutoffDate = knowledgeCutoffDate,
                pricingModel = pricingModel,
                size = size
            )
        }
    }
}
