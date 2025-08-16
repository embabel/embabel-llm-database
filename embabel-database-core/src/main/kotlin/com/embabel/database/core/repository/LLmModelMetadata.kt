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

import com.embabel.common.ai.model.LlmMetadata
import com.embabel.common.ai.model.ModelType;
import com.embabel.common.ai.model.PricingModel;

import java.time.LocalDate

//TODO add category
data class LlmModelMetadata (
    override val name: String,
    override val provider: String,
    override val knowledgeCutoffDate: LocalDate? = null,
    override val pricingModel: PricingModel? = null,
    val size: Long? = null,
    val task: String? = null
) : LlmMetadata  {

    override val type: ModelType
        get() = ModelType.LLM

    fun compareTo(other: LlmModelMetadata): Int {
        // Compare by size (nulls last, descending)
        val sizeCompare = compareValuesBy(this, other, { it.size?.unaryMinus() ?: Long.MIN_VALUE })
        if (sizeCompare != 0) return sizeCompare

        // Compare by knowledgeCutoffDate (nulls last, latest first)
        val dateCompare = compareValuesBy(this, other, { it.knowledgeCutoffDate?.toEpochDay()?.unaryMinus() ?: Long.MIN_VALUE })
        if (dateCompare != 0) return dateCompare

        // Compare by name (alphabetically)
        val nameCompare = this.name.compareTo(other.name)
        if (nameCompare != 0) return nameCompare

        // Compare by provider (alphabetically)
        return this.provider.compareTo(other.provider)
    }

    companion object {
        operator fun invoke(
            name: String,
            provider: String,
            knowledgeCutoffDate: LocalDate? = null,
            pricingModel: PricingModel? = null,
            size: Long? = null,
            task: String? = null
        ): LlmMetadata = LlmModelMetadata(name, provider, knowledgeCutoffDate, pricingModel, size, task)

        @JvmStatic
        @JvmOverloads
        fun create(
            name: String,
            provider: String,
            knowledgeCutoffDate: LocalDate? = null,
            pricingModel: PricingModel? = null,
            size: Long? = null,
            task: String? = null
        ): LlmMetadata = LlmModelMetadata(name, provider, knowledgeCutoffDate, pricingModel, size, task)
    }

}
