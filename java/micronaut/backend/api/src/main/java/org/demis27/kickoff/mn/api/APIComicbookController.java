package org.demis27.kickoff.mn.api;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import org.demis27.kickoff.mn.common.Comicbook;
import org.demis27.kickoff.mn.common.ComicbookClient;
import org.demis27.kickoff.mn.common.Person;

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

    @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Comicbook> get(@PathVariable String id) {
        return client.get(id);
    }

    @Post(value = "/", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Comicbook> post(@Body Comicbook comicbook) {
        return client.post(comicbook);
    }

    @Put(value = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Comicbook> put(@PathVariable String id, @Body Comicbook comicbook) {
        return client.put(id, comicbook);
    }

    @Delete(value = "/{id}")
    public HttpResponse delete(@PathVariable String id) {
        return client.delete(id);
    }

    @Post(value = "/{comicbookId}/writer/", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    HttpResponse<Comicbook> addWriter(@PathVariable String comicbookId, @Body Person person) {
        return client.addWriter(comicbookId, person);
    }
}