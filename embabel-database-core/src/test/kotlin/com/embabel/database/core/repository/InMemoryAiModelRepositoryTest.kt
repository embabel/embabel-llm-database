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


import com.embabel.common.ai.model.AiModel
import com.embabel.common.ai.model.DefaultOptionsConverter
import com.embabel.common.ai.model.Llm
import com.embabel.common.ai.model.LlmMetadata
import com.embabel.common.ai.model.ModelMetadata
import com.embabel.common.ai.model.ModelType
import com.embabel.common.ai.model.PerTokenPricingModel
import com.embabel.common.ai.model.PricingModel
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.mockk.every
import io.mockk.mockk
import java.io.File
import java.time.LocalDate
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.model.ChatResponse
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.embedding.EmbeddingModel

//time conversion
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InMemoryAiModelRepositoryTest {

    private lateinit var repository: InMemoryAiModelRepository
    private val model1 = LlmModelMetadata("modelA", "providerX")
    private val model2 = LlmModelMetadata("modelB", "providerY")
    private val model3 = LlmModelMetadata("modelA", "providerY")

    val dataDirectory = File("./data")

    @BeforeEach
    fun setUp() {
        repository = InMemoryAiModelRepository(listOf(model1, model2))
    }

    @AfterAll
    fun cleanUp() {
       if (dataDirectory.exists()) {
            deleteDirectoryRecursively(dataDirectory)
        }
    }

    fun deleteDirectoryRecursively(directory: File) {
        directory.listFiles()?.forEach { file ->
            if (file.isDirectory) {
                deleteDirectoryRecursively(file)
            } else {
                file.delete()
            }
        }
        directory.delete()
    }

    @Test
    fun `findByName returns correct models`() {
        val result = repository.findByName("modelA")
        assertNotNull(result)
        assertEquals(1, result!!.size)
        assertEquals(model1, result[0])
    }

    @Test
    fun `findAll returns all models`() {
        val result = repository.findAll()
        assertEquals(2, result.size)
        assertTrue(result.containsAll(listOf(model1, model2)))
    }

    @Test
    fun `save adds new model`() {
        repository.save(model3)
        val allModels = repository.findAll()
        assertEquals(3, allModels.size)
        assertTrue(allModels.contains(model3))
    }

    @Test
    fun `saveAll adds multiple models`() {
        repository.saveAll(listOf(model3))
        val allModels = repository.findAll()
        assertEquals(3, allModels.size)
        assertTrue(allModels.contains(model3))
    }

    @Test
    fun `lastUpdated updates after save`() {
        val before = repository.lastUpdated()
        Thread.sleep(10) // Ensure time difference
        repository.save(model3)
        val after = repository.lastUpdated()
        assertTrue(after.isAfter(before))
    }

    @Test
    fun `findByNameAndProvider returns correct model`() {
        repository.save(model3)
        val result = repository.findByNameAndProvider("modelA", "providerY")
        assertEquals(model3, result)
    }

    @Test
    fun `findByName returns empty list for unknown name`() {
        val result = repository.findByName("unknown")
        assertTrue(result!!.isEmpty())
    }

    @Test
    fun `findByNameAndProvider returns null for unknown combination`() {
        val result = repository.findByNameAndProvider("modelA", "providerZ")
        assertNull(result)
    }

    //simple testing
    @Test
    fun testSaveAndRetrieve() {
        //timestamp
        val today = LocalDate.now()
        //stub object
        val emptyChatModel = object : ChatModel {
            override fun call(prompt: Prompt): ChatResponse {
                throw NotImplementedError("This is a dummy ChatModel")
            }
        }
        //create an instance
        val llmInstance = Llm(
            name = "blah",
            provider = "OpenBlah",
            model = emptyChatModel,
            optionsConverter = DefaultOptionsConverter,
            knowledgeCutoffDate = today
        )
        //instantiate
        val repository = InMemoryAiModelRepository()
        //check
        assertTrue(repository.findAll().isEmpty())
        //save
        repository.save(llmInstance)
        //check again
        assertFalse(repository.findAll().isEmpty())
    }

    @Test
    fun testDataSave() {
        //save a single model and check that it's turned up in the data location
        //timestamp
        val today = LocalDate.now()
        //stub object
        val pricing = PerTokenPricingModel(
                usdPer1mInputTokens = 15.0,
                usdPer1mOutputTokens = 75.0,
            )
        val llmInstance = LlmModelMetadata(
            name = "gpt-4",
            provider = "OpenAI",
            knowledgeCutoffDate = today,
            pricingModel = pricing,
            size = 170_000_000_000 // 170B parameters
        )
        //instantiate
        val repository = InMemoryAiModelRepository()
        //reset the file
        repository.archive()
        //check
        assertTrue(repository.findAll().isEmpty())
        //save
        repository.save(llmInstance)
        //check again
        assertFalse(repository.findAll().isEmpty())
        //manually trigger a file flush
        repository.flushToFile()
        //now validate location
        val objectMapper = jacksonObjectMapper()
            .registerKotlinModule()
            .registerModule(JavaTimeModule())
        val file = File("./data/models.json")
        val jsonString = file.readText(Charsets.UTF_8)
        //get the object
        val models: List<Map<String,Any>> = objectMapper.readValue(jsonString)
        //convert
        val modelList = repository.mapToList(models)
        //validate
        assertNotNull(modelList)
        assertFalse(modelList.isEmpty())
    }

    @Test
    fun testDataRestore() {
        //dump a model
        //timestamp
        val today = LocalDate.now()
        //stub object
        val pricing = PerTokenPricingModel(
                usdPer1mInputTokens = 15.0,
                usdPer1mOutputTokens = 75.0,
            )
        val llmInstance = LlmModelMetadata(
            name = "gpt-4",
            provider = "OpenAI",
            knowledgeCutoffDate = today,
            pricingModel = pricing,
            size = 170_000_000_000 // 170B parameters
        )
        //instantiate
        var repository = InMemoryAiModelRepository()
        //check
        assertTrue(repository.findAll().isEmpty())
        //save
        repository.save(llmInstance)
        //check again
        assertFalse(repository.findAll().isEmpty())
        //manually trigger a file flush
        repository.flushToFile()
        //reset the repository
        repository = InMemoryAiModelRepository()
        //check
        assertTrue(repository.findAll().isEmpty())
        //load
        repository.load()
        //check
        assertFalse(repository.findAll().isEmpty())
    }


    @Test
    fun testTags() {
        val model = LlmModelMetadata(
            name = "test-model",
            provider = "test-provider",
            knowledgeCutoffDate = LocalDate.of(2025, 1, 1),
            size = 12345L,
            tags = listOf("nlp", "chatbot"), // 2 tags!
            source = "unit-test"
        )
        //instantiate
        var repository = InMemoryAiModelRepository()
        //save
        repository.save(model)
        //retrieve
        var results = repository.findByTags("nlp")
        assertNotNull(results)
        assertTrue(results!!.any { it is LlmModelMetadata && it.name == "test-model"})
        //try missing tag
        results = repository.findByTags("other-tag")
        assertNull(results)
        //try multiple
        results = repository.findByTags("nlp","chatbot")
        assertNotNull(results)
        assertTrue(results!!.any { it is LlmModelMetadata && it.name == "test-model"})
    }
}
