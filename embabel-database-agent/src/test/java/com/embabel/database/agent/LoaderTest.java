package com.embabel.database.agent;

import com.embabel.database.core.repository.InMemoryModelRepository;
import com.embabel.database.core.repository.ModelRepository;
import com.embabel.database.core.repository.domain.Model;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoaderTest {

    @Test
    void testLoad() throws Exception {
        //init
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new KotlinModule.Builder().build())
                .registerModule(new JavaTimeModule());
        ModelRepository modelRepository = new InMemoryModelRepository();
        //load from the json
        InputStream inputStream = this.getClass().getResourceAsStream("/json/dump.json");
        List<Model> models = objectMapper.readValue(inputStream,new TypeReference<List<Model>>(){});
        //check
        assertTrue(models.size() > 0);
        assertTrue(modelRepository.findAll().isEmpty());
        //now insert
        modelRepository.saveAll(models);
        assertFalse(modelRepository.findAll().isEmpty());
        //query
        assertFalse(modelRepository.findAllProviders().isEmpty());

    }
}
