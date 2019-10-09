package org.talend.kickoff.mn.comicbook;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.talend.kickoff.mn.common.Comicbook;
import org.talend.kickoff.mn.common.ComicbookRepository;

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

}