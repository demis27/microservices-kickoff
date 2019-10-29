package org.demis27.kickoff.mn.person;

import com.mongodb.reactivestreams.client.MongoClient;
import io.micronaut.test.annotation.MicronautTest;
import org.demis27.kickoff.mn.common.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class PersonRepositoryTest {

    @Inject MongoClient mongoClient;

    @Inject PersonRepository repository;

    @AfterEach
    void removeAllData() throws Exception {
        repository.list().blockingGet().stream().forEach(person -> repository.delete(person.getId()));
    }

    @Test void list() throws Exception {
        assertEquals(0, repository.list().blockingGet().size());
        create("John", "Byrne");
        assertEquals(1, repository.list().blockingGet().size());
    }

    @Test void get() throws Exception {
        Person person = create("John", "Byrne");
        assertEquals(person.getFirstname(), repository.get(person.getId()).blockingGet().getFirstname());
        assertEquals(person.getLastname(), repository.get(person.getId()).blockingGet().getLastname());
    }

    @Test void create() throws Exception {
        Person created = create("John", "Byrne");
        assertEquals("John", created.getFirstname());
        assertEquals("Byrne", created.getLastname());
    }

    @Test void update() throws Exception {
        Person person = create("John", "Byrne");
        person.setFirstname("Steve");
        person.setLastname("Ditko");
        Person updated = repository.update(person).blockingGet();
        assertEquals(updated.getFirstname(), person.getFirstname());
        assertEquals(updated.getLastname(), person.getLastname());
        assertEquals(updated.getId(), person.getId());
    }

    @Test void delete() throws Exception {
        Person person = create("John", "Byrne");
        repository.delete(person.getId());
        assertNull(repository.get(person.getId()).blockingGet());
    }

    private Person create(String firstName, String lastName) {
        Person person = new Person();
        person.setFirstname(firstName);
        person.setLastname(lastName);
        return repository.create(person).blockingGet();
    }

}