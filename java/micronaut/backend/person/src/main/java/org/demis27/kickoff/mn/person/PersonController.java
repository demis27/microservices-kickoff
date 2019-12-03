package org.demis27.kickoff.mn.person;

import java.util.List;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.PathVariable;
import org.demis27.kickoff.mn.common.Person;
import org.demis27.kickoff.mn.common.PersonOperations;

@Controller("/person/v1/persons")
public class PersonController implements PersonOperations {

    private PersonRepository personRepository;

    private final PersonProducer personProducer;

    public PersonController(PersonRepository PersonRepository, PersonProducer personProducer) {
        this.personRepository = PersonRepository;
        this.personProducer = personProducer;
    }

    @Override
    public HttpResponse<List<Person>> list() {
        return HttpResponse.ok(personRepository.list().blockingGet());
    }

    @Override
    public HttpResponse<Person> get(@PathVariable String id) {
        Person person = personRepository.get(id).blockingGet();
        if (person != null) {
            return HttpResponse.ok(person);
        } else {
            return HttpResponse.notFound();
        }
    }

    @Override
    public HttpResponse<Person> post(@Body Person person) {
        Person created = personRepository.create(person).blockingGet();
        personProducer.sendPerson(created.getId(), created);
        return HttpResponse.created(created);
    }

    @Override
    public HttpResponse<Person> put(@PathVariable String id, @Body Person person) {
        Person updatedPerson = personRepository.update(person).blockingGet();
        personProducer.sendPerson(updatedPerson.getId(), updatedPerson);
        return HttpResponse.ok(updatedPerson);
    }

    @Override
    public HttpResponse delete(@PathVariable String id) {
        personRepository.delete(id);
        return HttpResponse.noContent();
    }
}