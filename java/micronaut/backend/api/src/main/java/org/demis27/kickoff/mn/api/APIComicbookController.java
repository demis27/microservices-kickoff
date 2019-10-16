package org.demis27.kickoff.mn.api;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.demis27.kickoff.mn.common.Comicbook;
import org.demis27.kickoff.mn.common.ComicbookClient;

import java.util.List;

@Controller("/api/v1/comicbooks/")
public class APIComicbookController {

    private final ComicbookClient client;

    public APIComicbookController(ComicbookClient client) {
        this.client = client;
    }

    @Get(value = "/", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<Comicbook>> list() {
        return client.list();
    }
}