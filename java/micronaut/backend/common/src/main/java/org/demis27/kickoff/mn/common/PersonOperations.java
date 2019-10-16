package org.demis27.kickoff.mn.common;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;

import java.util.List;

public interface PersonOperations {

    @Get(value = "/", produces = MediaType.APPLICATION_JSON) public HttpResponse<List<Person>> list();

    @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Person> get(@PathVariable String id);

    @Post(value = "/", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Person> post(@Body Person person);

    @Put(value = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Person> put(@PathVariable String id, @Body Person Person);

    @Delete(value = "/{id}") public HttpResponse delete(@PathVariable String id);
}
