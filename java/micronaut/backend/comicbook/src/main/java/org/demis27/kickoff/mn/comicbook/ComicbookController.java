package org.demis27.kickoff.mn.comicbook;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import org.demis27.kickoff.mn.common.Comicbook;
import org.demis27.kickoff.mn.common.ComicbookOperations;
import org.demis27.kickoff.mn.common.Person;
import org.demis27.kickoff.mn.common.PersonClient;
import org.demis27.kickoff.mn.common.Role;

import java.util.List;

@Controller("/comicbook/v1/comicbooks")
public class ComicbookController implements ComicbookOperations {

    private ComicbookRepository comicbookRepository;

    private final PersonClient personClient;

    public ComicbookController(ComicbookRepository comicbookRepository, PersonClient personClient) {
        this.comicbookRepository = comicbookRepository;
        this.personClient = personClient;
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

    @Override public HttpResponse<Comicbook> post(@Body Comicbook comicbook) {
        return HttpResponse.created(comicbookRepository.create(comicbook).blockingGet());
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

    @Override
    public HttpResponse<Comicbook> addWriter(@PathVariable String comicbookId, @Body Person person) {
        if (person.getId() == null) {
            person = personClient.post(person).body();
        }
        else {
            person = personClient.get(person.getId()).body();
        }
        Comicbook comicbook = comicbookRepository.get(comicbookId).blockingGet();
        comicbook.addPerson(Role.WRITER, person);
        comicbook = comicbookRepository.update(comicbook).blockingGet();
        return HttpResponse.ok(comicbook);
    }
}