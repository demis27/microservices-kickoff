package org.demis27.kickoff.mn.comicbook;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import org.demis27.kickoff.mn.common.Person;

@KafkaListener(offsetReset = OffsetReset.EARLIEST)
public class PersonListener {

    private final ComicbookService comicbookService;

    public PersonListener(ComicbookService comicbookService) {
        this.comicbookService = comicbookService;
    }

    @Topic("person")
    public void receivePerson(@KafkaKey String id, Person updatedPerson) {
        comicbookService.updatePerson(updatedPerson);
    }
}
