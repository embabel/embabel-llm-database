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
package com.embabel.database.batch.steps;

import com.embabel.database.core.repository.ModelRepository;
import com.embabel.database.core.repository.domain.Model;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class ModelWriter implements ItemWriter<Model> {

    @Autowired
    ModelRepository modelRepository;

    @Value("${embabel.database.batch.pause:true")
    boolean pause = true;

    @Override
    public void write(Chunk<? extends Model> chunk) throws Exception {
        chunk.getItems().forEach(c -> {
            modelRepository.save(c);
        });
        //inject a pause
        if (pause) {
            Thread.sleep(500);//half second pause
        }
    }
}
