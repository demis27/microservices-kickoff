package org.demis27.kickoff.mn.person;

import java.util.List;

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
import org.demis27.kickoff.mn.common.PersonRepository;

@Controller("/person/v1/persons")
public class PersonController {

    private PersonRepository personRepository;

    public PersonController(PersonRepository PersonRepository) {
        this.personRepository = PersonRepository;
    }

    @Get(value = "/", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<List<Person>> list() {
        return HttpResponse.ok(personRepository.list().blockingGet());
    }

    @Get(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Person> get(@PathVariable String id) {
        Person person = personRepository.get(id).blockingGet();
        if (person != null) {
            return HttpResponse.ok(person);
        } else {
            return HttpResponse.notFound();
        }
    }

    @Post(value = "/", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Person> post(@Body Person person) {
        return HttpResponse.created(personRepository.create(person).blockingGet());
    }

    @Put(value = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Person> put(@PathVariable String id, @Body Person person) {
        return HttpResponse.ok(personRepository.update(person).blockingGet());
    }

    @Delete(value = "/{id}")
    public HttpResponse delete(@PathVariable String id) {
        personRepository.delete(id);
        return HttpResponse.noContent();
    }
}