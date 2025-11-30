package com.embabel.database.core.repository;

import com.embabel.database.core.repository.domain.Model;
import com.embabel.database.core.repository.domain.ModelProvider;
import com.embabel.database.core.repository.domain.Provider;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryModelRepositoryTest {

    @Test
    void testFindAllProviders() {
        //build a provider
        Provider provider = new Provider("id","provider","blah");
        //build a modelProvider
        ModelProvider modelProvider = new ModelProvider("id",provider,0.0,0.0,List.of("tags"),false);
        //build a model
        Model model = new Model("test","test", List.of("strings"),LocalDate.now(), LocalDate.now(),1l,null,false,List.of(modelProvider),"blah");
        //save
        ModelRepository modelRepository = new InMemoryModelRepository();
        modelRepository.save(model);
        //check
        assertFalse(modelRepository.findAll().isEmpty());
        //try the method
        assertFalse(modelRepository.findAllProviders().isEmpty());
        assertEquals("id",modelRepository.findAllProviders().get(0).getId());
    }
}
