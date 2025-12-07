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

import com.embabel.database.core.repository.domain.Provider
import java.util.Optional

class InMemoryProviderRepository(providers: List<Provider> = emptyList()) : ProviderRepository{
    override fun save(provider: Provider) : Provider {
        //stub
        return Provider("id","name","website")
    }

    override fun existsById(id: String): Boolean {
        return true //stub
    }

    override fun findById(id: String): Optional<Provider> {
        return Optional.empty<Provider>()
    }
}
