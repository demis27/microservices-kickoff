package org.talend.kickoff.mn.comicbook;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
public class ComicbookControllerTest {

    @Inject
    EmbeddedServer server;

    @Inject @Client("/comicbook/v1/comicbooks")
    HttpClient client;

    @Test
    void emptyList() throws Exception {
        String retrieve = client.toBlocking().retrieve(HttpRequest.GET(""), String.class);
        assertNotNull(retrieve);
        assertEquals("[]", retrieve);
    }
}