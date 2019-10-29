package org.demis27.kickoff.mn.person;

import com.mongodb.client.model.Filters;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.demis27.kickoff.mn.common.ComicbookConfiguration;
import org.demis27.kickoff.mn.common.Person;

import javax.inject.Singleton;
import java.util.List;
import java.util.UUID;

@Singleton
public class PersonRepository {

    public static final String PERSON_REPOSITORY = "person";

    private final MongoClient mongoClient;

    private final ComicbookConfiguration configuration;

    public PersonRepository(MongoClient mongoClient, ComicbookConfiguration configuration) {
        this.mongoClient = mongoClient;
        this.configuration = configuration;
    }

    public Single<List<Person>> list() {
        return Flowable.fromPublisher(getCollection().find()).toList();
    }

    public Maybe<Person> get(String id) {
        return Flowable.fromPublisher(getCollection().find(Filters.eq("_id", id)).limit(1)).firstElement();
    }

    public Single<Person> create(Person Person) {
        if (Person.getId() == null) {
            Person.setId(UUID.randomUUID().toString());
        }
        return Single.fromPublisher(getCollection().insertOne(Person)).map(success -> Person);
    }

    public Single<Person> update(Person Person) {
        return Single
                .fromPublisher(getCollection().replaceOne(Filters.eq("_id", Person.getId()), Person))
                .map(success -> Person);
    }

    public void delete(String id) {
        Single.fromPublisher(getCollection().deleteMany(Filters.eq("_id", id))).blockingGet();
    }

    private MongoCollection<Person> getCollection() {
        return mongoClient.getDatabase(configuration.getDatabaseName()).getCollection(PERSON_REPOSITORY, Person.class);
    }
}