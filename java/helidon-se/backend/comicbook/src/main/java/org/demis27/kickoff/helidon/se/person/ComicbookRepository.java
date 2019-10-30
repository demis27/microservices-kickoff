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
import org.demis27.kickoff.helidon.se.common.Comicbook;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class ComicbookRepository {

    private MongoClient mongoClient;

    private Config config = Config.create();

    public ComicbookRepository() {
        CodecRegistry pojoCodecRegistry = fromRegistries(com.mongodb.MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClientSettings settings = MongoClientSettings.builder().codecRegistry(pojoCodecRegistry).build();

        this.mongoClient = MongoClients.create(settings);
    }

    public List<Comicbook> list() {
        FindIterable<Comicbook> comicbooks = getCollection().find();
        List<Comicbook> result = new ArrayList();
        for (Comicbook comicbook : comicbooks) {
            result.add(comicbook);
        }
        return result;
    }

    public Comicbook get(String id) {
        return getCollection().find(Filters.eq("_id", id)).limit(1).first();
    }

    public Comicbook create(Comicbook comicbook) {
        if (comicbook.getId() == null) {
            comicbook.setId(UUID.randomUUID().toString());
        }
        getCollection().insertOne(comicbook);
        return get(comicbook.getId());
    }

    public Comicbook update(Comicbook comicbook) {
        getCollection().replaceOne(Filters.eq("_id", comicbook.getId()), comicbook);
        return get(comicbook.getId());
    }

    public void delete(String id) {
        getCollection().deleteOne(Filters.eq("_id", id));
    }

    private MongoCollection<Comicbook> getCollection() {
        return mongoClient.getDatabase(config.get("database").asString().orElse("kickoff-2019")).getCollection("comicbook", Comicbook.class);
    }

}
