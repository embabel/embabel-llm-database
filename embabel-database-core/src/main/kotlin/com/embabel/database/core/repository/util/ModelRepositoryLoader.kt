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
package com.embabel.database.core.repository.util

import com.embabel.database.core.repository.ModelService
import com.embabel.database.core.repository.domain.Model
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.InputStream

@Component
class ModelRepositoryLoader @Autowired constructor(
    private val objectMapper: ObjectMapper,
    private val modelService: ModelService
) {

    fun loadFromFile(path: String) {
        val resourceStream: InputStream = this::class.java.classLoader.getResourceAsStream(path)
            ?: throw IllegalArgumentException("Resource not found: $path")
        val models: List<Model> = objectMapper.readValue(resourceStream)
//        modelRepository.saveAll(models)
        models.forEach { model -> modelService.saveModel(model) }
        resourceStream.close() //tidy up
    }

}
