package org.demis27.kickoff.helidon.se.api;

import io.helidon.config.Config;
import io.helidon.webserver.Handler;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;
import org.demis27.kickoff.helidon.se.common.Comicbook;
import org.demis27.kickoff.helidon.se.common.Person;

import java.util.concurrent.atomic.AtomicReference;

public class APIComicbookService implements Service {

    private final AtomicReference<String> greeting = new AtomicReference<>();

    APIComicbookService(Config config) {
        greeting.set(config.get("app.comicbook").asString().orElse("Ciao"));
    }

    @Override
    public void update(Routing.Rules rules) {
        rules.get("/", this::list)
                .get("/{id}", this::get)
                .post("/", Handler.create(Comicbook.class, this::post))
                .put("/{id}", Handler.create(Comicbook.class, this::put))
                .delete("/{id}", this::delete);
    }

    private void list(ServerRequest serverRequest, ServerResponse serverResponse) {

    }

    private void get(ServerRequest serverRequest, ServerResponse serverResponse) {

    }

    private void post(ServerRequest serverRequest, ServerResponse serverResponse, Comicbook comicbook) {

    }

    private void put(ServerRequest serverRequest, ServerResponse serverResponse, Comicbook comicbook) {

    }

    private void delete(ServerRequest serverRequest, ServerResponse serverResponse) {

    }

}
