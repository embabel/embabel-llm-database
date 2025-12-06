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
package com.embabel.database.core.repository;

import com.embabel.database.core.repository.domain.Model;
import com.embabel.database.core.repository.domain.ModelProvider;
import com.embabel.database.core.repository.domain.Organization;
import com.embabel.database.core.repository.domain.Provider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes={JpaModelRepositoryITest.class, JpaModelRepositoryITest.TestConfig.class})
public class JpaModelRepositoryITest {

    @Autowired
    ModelRepository jpaModelRepository;

    @BeforeEach
    void before() {
        ((JpaRepository<?, ?>) jpaModelRepository).deleteAll();
    }

    @Test
    void testSave() throws Exception {
        assertTrue(jpaModelRepository.findAll().isEmpty());

        //add a model
        Provider provider = new Provider("id","provider","blah");
        ModelProvider modelProvider = new ModelProvider("id",provider,0.0,0.0,List.of("tags"),false);
        Model model = new Model("test","test", List.of("strings"), LocalDate.now(), LocalDate.now(),1l,null,false,List.of(modelProvider),"blah", LocalDateTime.now());
        //save
        jpaModelRepository.save(model);
        //test now
        assertFalse(jpaModelRepository.findAll().isEmpty());
    }

    @Test
    void testLastUpdated() throws Exception {
        assertTrue(jpaModelRepository.findAll().isEmpty());
        //add a model
        Provider provider = new Provider("id","provider","blah");
        ModelProvider modelProvider = new ModelProvider("id",provider,0.0,0.0,List.of("tags"),false);

        //setup timestamp
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(1);

        Model model = new Model("test","test", List.of("strings"), LocalDate.now(), LocalDate.now(),1l,null,false,List.of(modelProvider),"blah", localDateTime);
        //save
        jpaModelRepository.save(model);
        //test now
        assertFalse(jpaModelRepository.findAll().isEmpty());
        //test date
        LocalDateTime auditTimestamp = jpaModelRepository.lastUpdated();
        assertTrue(auditTimestamp.isAfter(localDateTime));
    }

    @Test
    void testFindAllProviders() {
        assertTrue(jpaModelRepository.findAll().isEmpty());
        //add a model
        Provider provider = new Provider("id","provider","blah");
        ModelProvider modelProvider = new ModelProvider("id",provider,0.0,0.0,List.of("tags"),false);
        Model model = new Model("test","test", List.of("strings"), LocalDate.now(), LocalDate.now(),1l,null,false,List.of(modelProvider),"blah", LocalDateTime.now());
        //save
        jpaModelRepository.save(model);
        //test now
        assertFalse(jpaModelRepository.findAll().isEmpty());
        //get all the providers
        List<Provider> providers = jpaModelRepository.findAllProviders();
        assertNotNull(providers);
        assertFalse(providers.isEmpty());
        assertEquals(1, providers.size());
        assertEquals("id", providers.getFirst().getId());
    }

    @Test
    void testFindAllOrganizations() {
        assertTrue(jpaModelRepository.findAll().isEmpty());
        //add a model
        Provider provider = new Provider("id","provider","blah");
        ModelProvider modelProvider = new ModelProvider("id",provider,0.0,0.0,List.of("tags"),false);
        Organization organization = new Organization("id","name","website");
        Model model = new Model("test","test", List.of("strings"), LocalDate.now(), LocalDate.now(),1l,organization,false,List.of(modelProvider),"blah", LocalDateTime.now());
        //save
        jpaModelRepository.save(model);
        //test now
        assertFalse(jpaModelRepository.findAll().isEmpty());
        //get all the orgs
        List<Organization> organizations = jpaModelRepository.findAllOrganizations();
        assertNotNull(organizations);
        assertFalse(organizations.isEmpty());
        assertEquals(1,organizations.size());
        assertEquals("id",organizations.getFirst().getId());
    }

    @Test
    void testFindByProvider() throws Exception {
        assertTrue(jpaModelRepository.findAll().isEmpty());
        //add a model
        Provider provider = new Provider("id","provider","blah");
        ModelProvider modelProvider = new ModelProvider("id",provider,0.0,0.0,List.of("tags"),false);
        Organization organization = new Organization("id","name","website");
        Model model = new Model("test","test", List.of("strings"), LocalDate.now(), LocalDate.now(),1l,organization,false,List.of(modelProvider),"blah", LocalDateTime.now());
        //save
        jpaModelRepository.save(model);
        //test now
        assertFalse(jpaModelRepository.findAll().isEmpty());

        List<Model> models = jpaModelRepository.findByProvider("provider");
        assertNotNull(models);
        assertFalse(models.isEmpty());
        assertEquals("test",models.getFirst().getId());
        //try a bogus one
        models = jpaModelRepository.findByProvider("does not exist");
        assertNotNull(models);
        assertTrue(models.isEmpty());
    }

    @Test
    void testFindByNameAndProvider() throws Exception {
        assertTrue(jpaModelRepository.findAll().isEmpty());
        //add a model
        Provider provider = new Provider("id","provider","blah");
        ModelProvider modelProvider = new ModelProvider("id",provider,0.0,0.0,List.of("tags"),false);
        Organization organization = new Organization("id","name","website");
        Model model = new Model("test","test", List.of("strings"), LocalDate.now(), LocalDate.now(),1l,organization,false,List.of(modelProvider),"blah", LocalDateTime.now());
        //save
        jpaModelRepository.save(model);
        //test now
        assertFalse(jpaModelRepository.findAll().isEmpty());
        //query
        model = jpaModelRepository.findByNameAndProvider("test","provider");
        assertNotNull(model);
        assertEquals("test",model.getName());
        //now do a negative
        model = jpaModelRepository.findByNameAndProvider("test","does not exist");
        assertNull(model);
    }

    @TestConfiguration
    @ComponentScan(basePackages = {"com.embabel.database.core.repository"})
    @EnableAutoConfiguration
    static class TestConfig {

        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }

    }

}
