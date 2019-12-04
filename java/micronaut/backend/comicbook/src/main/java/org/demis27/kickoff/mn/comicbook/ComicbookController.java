package org.demis27.kickoff.mn.comicbook;

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
import org.demis27.kickoff.mn.common.ComicbookRepository;

import java.util.List;

@Controller("/comicbook/v1/comicbooks")
public class ComicbookController {

    private ComicbookRepository comicbookRepository;

    public ComicbookController(ComicbookRepository comicbookRepository) {
        this.comicbookRepository = comicbookRepository;
    }

    @Get(value = "/", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<Comicbook>> list() {
        return HttpResponse.ok(comicbookRepository.list().blockingGet());
    }

    @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Comicbook> get(@PathVariable String id) {
        Comicbook comicbook = comicbookRepository.get(id).blockingGet();
        if (comicbook != null) {
            return HttpResponse.ok(comicbook);
        } else {
            return HttpResponse.notFound();
        }
    }

    @Post(value = "/", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Comicbook> post(@Body Comicbook comicbook) {
        return HttpResponse.created(comicbookRepository.create(comicbook).blockingGet());
    }

    @Put(value = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Comicbook> put(@PathVariable String id, @Body Comicbook comicbook) {
        return HttpResponse.ok(comicbookRepository.update(comicbook).blockingGet());
    }

    @Delete(value = "/{id}")
    public HttpResponse delete(@PathVariable String id) {
        comicbookRepository.delete(id);
        return HttpResponse.noContent();
    }
}