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

import com.embabel.database.core.repository.ModelRepository
import com.embabel.database.core.repository.domain.Model
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/models")
class ModelRepositoryController {

    private val logger = LoggerFactory.getLogger(ModelRepositoryController::class.java)

    @Autowired
    lateinit var modelRepository: ModelRepository

    @GetMapping
    fun getAll(): ResponseEntity<List<Model>> {
        val models =  modelRepository.findAll()
        return if (models.isEmpty()) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(models)
        }
    }

    @PostMapping
    fun save(@RequestBody model: Model): Model {
        modelRepository.save(model)
        return model //send the same thing back TODO add identifier
    }

    @GetMapping("/search/findByName")
    fun getByName(@RequestParam("name") name: String): List<Model>? {
        return modelRepository.findByName(name)
    }
//
//    @GetMapping("/search/findByNameAndProvider")
//    fun getByName(@RequestParam("name") name: String, @RequestParam("provider") provider: String): Model? {
//        return modelRepository.findByNameAndProvider(name,provider) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND,"Model not found")
//    }
//
    @GetMapping("/lastUpdated")
    fun getLastUpdated(): LocalDateTime {
        return modelRepository.lastUpdated()
    }

    @GetMapping("/count")
    fun getCount(): Map<String,Int> {
        val count = modelRepository.count()
        return mapOf("count" to count)
    }
//
//    @GetMapping("/search/findByNameContains")
//    fun getNameContains(@RequestParam("contains") name: String): List<Model>? {
//        return modelRepository.findByNameContains(name) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND,"No matching model found")
//    }

    @GetMapping("/search/findByTags")
    fun getByTags(@RequestParam("tags") tags: List<String>?): List<Model>? {
        if (tags.isNullOrEmpty()) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST,"No tags passed")
        } //end if
        return modelRepository.findByTags(*tags.toTypedArray()) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND,"No matching model found")
    }

    @GetMapping("/{modelId}")
    fun getById(@PathVariable modelId: String): Model? {
        return modelRepository.findById(modelId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND,"No model found for ID")
    }

//    @GetMapping("/search/findByProvider")
//    fun getByProvider(@RequestParam("provider") provider: String): List<Model>? {
//        return modelRepository.findByProvider(provider) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND,"No matching models found")
//    }
}
