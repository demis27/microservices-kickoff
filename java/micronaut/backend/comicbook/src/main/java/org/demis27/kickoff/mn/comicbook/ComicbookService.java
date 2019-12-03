package org.demis27.kickoff.mn.comicbook;

import org.demis27.kickoff.mn.common.Comicbook;
import org.demis27.kickoff.mn.common.Person;

import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class ComicbookService {

    private final ComicbookRepository comicbookRepository;

    public ComicbookService(ComicbookRepository comicbookRepository) {
        this.comicbookRepository = comicbookRepository;
    }

    public void updatePerson(Person updatedPerson) {
        // Get list of comicbook that contains this person
        List<Comicbook> needToUpdate = comicbookRepository.list()
                .blockingGet()
                .stream()
                .filter(comicBook -> comicBook.getAllPersons().contains(updatedPerson))
                .collect(Collectors.toList());
        // Update comicbooks with the updated person
        needToUpdate.stream().flatMap(comicBook -> comicBook.getAllPersons().stream()).forEach(person -> {
            person.setLastname(updatedPerson.getLastname());
            person.setFirstname(updatedPerson.getFirstname());
        });
        // save
        needToUpdate.stream().forEach(comicbook -> comicbookRepository.update(comicbook).blockingGet());
    }
}