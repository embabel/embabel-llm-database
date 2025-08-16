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
package com.embabel.database.server.controller

import com.embabel.database.core.repository.AiModelRepository;
import com.embabel.common.ai.model.ModelMetadata

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

import org.slf4j.LoggerFactory

@RestController
@RequestMapping("/api/v1/models")
class AiModelRepositoryController {

    private val logger = LoggerFactory.getLogger(AiModelRepositoryController::class.java)

    @Autowired
    lateinit var aiModelRepository: AiModelRepository

    @GetMapping
    fun getAll(): ResponseEntity<List<ModelMetadata>> {
        val models =  aiModelRepository.findAll()
        return if (models.isEmpty()) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(models)
        }
    }

    @PostMapping
    fun save(@RequestBody model: ModelMetadata): ModelMetadata {
        aiModelRepository.save(model)
        return model //send the same thing back TODO add identifier
    }

    @GetMapping("/search/findByName")
    fun getByName(@RequestParam("name") name: String): List<ModelMetadata>? {
        return aiModelRepository.findByName(name) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND,"Model not found")
    }

    @GetMapping("/search/findByNameAndProvider")
    fun getByName(@RequestParam("name") name: String, @RequestParam("provider") provider: String): ModelMetadata? {
        return aiModelRepository.findByNameAndProvider(name,provider) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND,"Model not found")
    }

    @GetMapping("/lastUpdated")
    fun getLastUpdated(): LocalDateTime {
        return aiModelRepository.lastUpdated()
    }

    @GetMapping("/search/findByTask")
    fun getByTask(@RequestParam("task") task: String): List<ModelMetadata>? {
        return aiModelRepository.findByTask(task) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND,"Models not found")
    }
}
