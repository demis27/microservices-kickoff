package org.demis27.kickoff.mn.common;

import com.mongodb.reactivestreams.client.MongoClient;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@MicronautTest
class ComicbookRepositoryTest {

    @Inject MongoClient mongoClient;

    @Inject ComicbookRepository repository;

    @AfterEach
    void removeAllData() throws Exception {
        repository.list().blockingGet().stream().forEach(comicbook -> repository.delete(comicbook.getId()));
    }

    @Test void list() throws Exception {
        assertEquals(0, repository.list().blockingGet().size());
        create("Amazing Spider-Man");
        assertEquals(1, repository.list().blockingGet().size());
    }

    @Test void get() throws Exception {
        Comicbook comicbook = create("Avengers West-Coast");
        assertEquals(comicbook.getName(), repository.get(comicbook.getId()).blockingGet().getName());
    }

    @Test void create() throws Exception {
        Comicbook created = create("Black Panther");
        assertEquals("Black Panther", created.getName());
    }

    @Test void update() throws Exception {
        Comicbook comicbook = create("Avengers West-Coast");
        comicbook.setName("Avengers");
        Comicbook updated = repository.update(comicbook).blockingGet();
        assertEquals(updated.getName(), comicbook.getName());
        assertEquals(updated.getId(), comicbook.getId());
    }

    @Test void delete() throws Exception {
        Comicbook comicbook = create("Avengers West-Coast");
        repository.delete(comicbook.getId());
        assertNull(repository.get(comicbook.getId()).blockingGet());
    }

    private Comicbook create(String name) {
        Comicbook comicbook = new Comicbook();
        comicbook.setName(name);
        return repository.create(comicbook).blockingGet();
    }
}