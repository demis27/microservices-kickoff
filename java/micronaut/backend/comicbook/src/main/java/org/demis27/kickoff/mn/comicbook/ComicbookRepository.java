package org.demis27.kickoff.mn.comicbook;

import java.util.List;
import java.util.UUID;

import javax.inject.Singleton;

import com.mongodb.client.model.Filters;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.demis27.kickoff.mn.common.Comicbook;
import org.demis27.kickoff.mn.common.ComicbookConfiguration;

@Singleton
public class ComicbookRepository {

    public static final String COMICBOOK_COLLECTION = "comicbook";

    private final MongoClient mongoClient;

    private final ComicbookConfiguration configuration;

    public ComicbookRepository(MongoClient mongoClient, ComicbookConfiguration configuration) {
        this.mongoClient = mongoClient;
        this.configuration = configuration;
    }

    public Single<List<Comicbook>> list() {
        return Flowable.fromPublisher(getCollection().find()).toList();
    }

    public Maybe<Comicbook> get(String id) {
        return Flowable.fromPublisher(getCollection().find(Filters.eq("_id", id)).limit(1)).firstElement();
    }

    public Single<Comicbook> create(Comicbook comicbook) {
        if (comicbook.getId() == null) {
            comicbook.setId(UUID.randomUUID().toString());
        }
        return Single.fromPublisher(getCollection().insertOne(comicbook)).map(success -> comicbook);
    }

    public Single<Comicbook> update(Comicbook comicbook) {
        return Single
                .fromPublisher(getCollection().replaceOne(Filters.eq("_id", comicbook.getId()), comicbook))
                .map(success -> comicbook);
    }

    public void delete(String id) {
        Single.fromPublisher(getCollection().deleteOne(Filters.eq("_id", id))).blockingGet();
    }

    private MongoCollection<Comicbook> getCollection() {
        return mongoClient
                .getDatabase(configuration.getDatabaseName())
                .getCollection(COMICBOOK_COLLECTION, Comicbook.class);
    }
}