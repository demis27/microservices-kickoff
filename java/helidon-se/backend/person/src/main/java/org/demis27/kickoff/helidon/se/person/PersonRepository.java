package org.demis27.kickoff.helidon.se.person;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import io.helidon.config.Config;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.demis27.kickoff.helidon.se.common.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class PersonRepository {

    private MongoClient mongoClient;

    private Config config = Config.create();

    public PersonRepository() {
        CodecRegistry pojoCodecRegistry = fromRegistries(com.mongodb.MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClientSettings settings = MongoClientSettings.builder().codecRegistry(pojoCodecRegistry).build();

        this.mongoClient = MongoClients.create(settings);
    }

    public List<Person> list() {
        FindIterable<Person> persons = getCollection().find();
        List<Person> result = new ArrayList();
        for (Person person : persons) {
            result.add(person);
        }
        return result;
    }

    public Person get(String id) {
        return getCollection().find(Filters.eq("_id", id)).limit(1).first();
    }

    public Person create(Person person) {
        if (person.getId() == null) {
            person.setId(UUID.randomUUID().toString());
        }
        getCollection().insertOne(person);
        return get(person.getId());
    }

    public Person update(Person person) {
        getCollection().replaceOne(Filters.eq("_id", person.getId()), person);
        return get(person.getId());
    }

    public void delete(String id) {
        getCollection().deleteOne(Filters.eq("_id", id));
    }

    private MongoCollection<Person> getCollection() {
        return mongoClient.getDatabase(config.get("database").asString().orElse("kickoff-2019")).getCollection("person", Person.class);
    }

}
