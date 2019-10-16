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
import org.demis27.kickoff.mn.common.Person;
import org.demis27.kickoff.mn.common.PersonClient;

import java.util.List;

@Controller("/api/v1/persons/")
public class APIPersonController {

    private final PersonClient client;

    public APIPersonController(PersonClient client) {
        this.client = client;
    }

    @Get(value = "/", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<Person>> list() {
        return client.list();
    }

    @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Person> get(@PathVariable String id) {
        return client.get(id);
    }

    @Post(value = "/", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Person> post(@Body Person person) {
        return client.post(person);
    }

    @Put(value = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Person> put(@PathVariable String id, @Body Person person) {
        return client.put(id, person);
    }

    @Delete(value = "/{id}")
    public HttpResponse delete(@PathVariable String id) {
        return client.delete(id);
    }

}
