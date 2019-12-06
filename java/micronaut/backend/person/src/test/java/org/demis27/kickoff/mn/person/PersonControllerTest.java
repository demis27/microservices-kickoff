package org.demis27.kickoff.mn.person;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import org.demis27.kickoff.mn.common.Person;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@MicronautTest class PersonControllerTest {

    @Inject PersonProducer personProducer;

    @Inject EmbeddedServer server;

    @Inject @Client("/person/v1/persons") HttpClient client;

    @MockBean(PersonProducer.class)
    public PersonProducer personProducer() {
        return mock(PersonProducer.class);
    }

    @Test void emptyList() throws Exception {
        String retrieve = client.toBlocking().retrieve(HttpRequest.GET(""), String.class);
        assertNotNull(retrieve);
        assertEquals("[]", retrieve);
    }

    @Test void crud() throws Exception {
        // create
        Person Person = new Person();
        Person.setFirstname("Clark");
        Person retrieve = client.toBlocking().retrieve(HttpRequest.POST("/", Person), Person.class);
        verify(personProducer, times(1)).sendPerson(anyString(), any(Person.class));

        assertNotNull(retrieve);
        assertNotNull(retrieve.getId());
        assertEquals(Person.getFirstname(), retrieve.getFirstname());

        // read
        String id = retrieve.getId();
        retrieve = client.toBlocking().retrieve(HttpRequest.GET("/" + id), org.demis27.kickoff.mn.common.Person.class);
        assertPersonEquals(Person, retrieve, id);

        // read list
        List<Person> retrieves =
                client.toBlocking().retrieve(HttpRequest.GET("/"), Argument.of(List.class, Person.class));
        assertEquals(1, retrieves.size());
        retrieve = retrieves.get(0);
        assertPersonEquals(Person, retrieve, id);

        // Update
        Person.setFirstname("Bruce");
        Person.setId(id);
        retrieve = client.toBlocking().retrieve(HttpRequest.PUT("/" + id, Person), Person.class);
        verify(personProducer, times(2)).sendPerson(anyString(), any(Person.class));
        assertPersonEquals(Person, retrieve, id);

        // Read again
        // read
        retrieve = client.toBlocking().retrieve(HttpRequest.GET("/" + id), Person.class);
        assertPersonEquals(Person, retrieve, id);

        // delete
        client.retrieve(HttpRequest.DELETE("/" + id));
        retrieve = client.toBlocking().retrieve(HttpRequest.GET("/" + id), Person.class);
        assertPersonEquals(Person, retrieve, id);
    }

    private void assertPersonEquals(Person Person, Person retrieve, String id) {
        assertNotNull(retrieve);
        assertEquals(id, retrieve.getId());
        assertEquals(Person.getFirstname(), retrieve.getFirstname());
    }
}
