package org.demis27.kickoff.mn.comicbook;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import org.demis27.kickoff.mn.common.Comicbook;
import org.demis27.kickoff.mn.common.ComicbookOperations;
import org.demis27.kickoff.mn.common.ComicbookRepository;

import java.util.List;

@Controller("/comicbook/v1/comicbooks")
public class ComicbookController implements ComicbookOperations {

    private ComicbookRepository comicbookRepository;

    public ComicbookController(ComicbookRepository comicbookRepository) {
        this.comicbookRepository = comicbookRepository;
    }

    @Override
    public HttpResponse<List<Comicbook>> list() {
        return HttpResponse.ok(comicbookRepository.list().blockingGet());
    }

    @Override
    public HttpResponse<Comicbook> get(@PathVariable String id) {
        Comicbook comicbook = comicbookRepository.get(id).blockingGet();
        if (comicbook != null) {
            return HttpResponse.ok(comicbook);
        } else {
            return HttpResponse.notFound();
        }
    }

    @Override
    public HttpResponse<Comicbook> post(@Body Comicbook person) {
        return HttpResponse.created(comicbookRepository.create(person).blockingGet());
    }

    @Override
    public HttpResponse<Comicbook> put(@PathVariable String id, @Body Comicbook comicbook) {
        return HttpResponse.ok(comicbookRepository.update(comicbook).blockingGet());
    }

    @Override
    public HttpResponse delete(@PathVariable String id) {
        comicbookRepository.delete(id);
        return HttpResponse.noContent();
    }
}