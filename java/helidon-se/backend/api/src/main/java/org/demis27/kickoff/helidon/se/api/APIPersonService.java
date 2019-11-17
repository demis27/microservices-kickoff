package org.demis27.kickoff.helidon.se.api;

import io.helidon.config.Config;
import io.helidon.webserver.Handler;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;
import org.demis27.kickoff.helidon.se.common.Person;

import java.util.concurrent.atomic.AtomicReference;

public class APIPersonService implements Service {

    private final AtomicReference<String> greeting = new AtomicReference<>();

    APIPersonService(Config config) {
        greeting.set(config.get("app.comicbook").asString().orElse("Ciao"));
    }

    @Override
    public void update(Routing.Rules rules) {
        rules.get("/", this::list)
                .get("/{id}", this::get)
                .post("/", Handler.create(Person.class, this::post))
                .put("/{id}", Handler.create(Person.class, this::put))
                .delete("/{id}", this::delete);
    }

    private void delete(ServerRequest serverRequest, ServerResponse serverResponse) {

    }

    private void put(ServerRequest serverRequest, ServerResponse serverResponse, Person person) {

    }

    private void post(ServerRequest serverRequest, ServerResponse serverResponse, Person person) {

    }

    private void get(ServerRequest serverRequest, ServerResponse serverResponse) {

    }

    private void list(ServerRequest serverRequest, ServerResponse serverResponse) {
    }

}
