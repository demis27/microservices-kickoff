package org.demis27.kickoff.mn.common;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.tracing.annotation.ContinueSpan;

import java.util.List;

public interface ComicbookOperations {

    @ContinueSpan @Get(value = "/", produces = MediaType.APPLICATION_JSON) HttpResponse<List<Comicbook>> list();

    @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON) HttpResponse<Comicbook> get(@PathVariable String id);

    @Post(value = "/", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    HttpResponse<Comicbook> post(@Body Comicbook person);

    @Put(value = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    HttpResponse<Comicbook> put(@PathVariable String id, @Body Comicbook comicbook);

    @Delete(value = "/{id}") HttpResponse delete(@PathVariable String id);

    @Post(value = "/{comicbookId}/writer/", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    HttpResponse<Comicbook> addWriter(@PathVariable String comicbookId, @Body Person person);
}
