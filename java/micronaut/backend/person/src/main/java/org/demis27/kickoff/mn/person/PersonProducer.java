package org.demis27.kickoff.mn.person;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import org.demis27.kickoff.mn.common.Person;

@KafkaClient
public interface PersonProducer {

    @Topic("person")
    void sendPerson(@KafkaKey String id, Person person);
}