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
import com.embabel.database.core.repository.domain.Organization
import com.embabel.database.core.repository.domain.Provider
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface JpaModelRepository: JpaRepository<Model, String>, ModelRepository {

    @Query("select max(m.lastModifiedAt) from Model m")
    override fun lastUpdated(): LocalDateTime

    @Query("SELECT DISTINCT mp.provider FROM Model m JOIN m.modelProviders mp")
    override fun findAllProviders(): List<Provider>?

    @Query("SELECT DISTINCT m.organization FROM Model m WHERE m.organization IS NOT NULL")
    override fun findAllOrganizations(): List<Organization>?

    @Query("SELECT DISTINCT m FROM Model m JOIN m.modelProviders mp WHERE mp.provider.name = :provider")
    override fun findByProvider(provider: String): List<Model>?

    @Query("SELECT m FROM Model m JOIN m.modelProviders mp WHERE m.name = :name AND mp.provider.name = :provider")
    override fun findByNameAndProvider(name: String, provider: String): Model?

    @Modifying
    @Query("DELETE FROM Model m")
    override fun reset()

    @Query("SELECT DISTINCT t FROM Model m JOIN m.tags t")
    override fun findAllDistinctTags(): List<String>

//    @Query("SELECT DISTINCT m FROM Model m WHERE :tags MEMBER OF m.tags")
//    override fun findByTags(@Param("tags") tags: Array<out String>): List<Model>

    @Query("SELECT DISTINCT m FROM Model m JOIN m.tags t WHERE t IN :tags")
    override fun findByTags(@Param("tags") tags: List<String>): List<Model>
}
