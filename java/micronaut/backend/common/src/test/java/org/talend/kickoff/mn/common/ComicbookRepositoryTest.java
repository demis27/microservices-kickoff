package org.talend.kickoff.mn.common;

import com.mongodb.reactivestreams.client.MongoClient;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class ComicbookRepositoryTest {

    @Inject MongoClient mongoClient;

    @Inject ComicbookRepository repository;

    @Test void list() throws Exception {
        assertEquals(0, repository.list().blockingGet().size());
    }

}