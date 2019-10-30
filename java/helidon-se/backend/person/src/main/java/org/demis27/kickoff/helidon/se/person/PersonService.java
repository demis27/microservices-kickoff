/*
 * Copyright (c) 2018, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.demis27.kickoff.helidon.se.person;

import java.util.concurrent.atomic.AtomicReference;

import javax.json.bind.JsonbBuilder;

import io.helidon.common.http.Http;
import io.helidon.common.http.MediaType;
import io.helidon.config.Config;
import io.helidon.webserver.Handler;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;
import org.demis27.kickoff.helidon.se.common.Person;

public class PersonService implements Service {

    private final AtomicReference<String> greeting = new AtomicReference<>();

    PersonService(Config config) {
        greeting.set(config.get("app.person").asString().orElse("Ciao"));
    }

    private PersonRepository personRepository = new PersonRepository();

    @Override
    public void update(Routing.Rules rules) {
        rules.get("/", this::list)
                .get("/{id}", this::get)
                .post("/", Handler.create(Person.class, this::post))
                .put("/{id}", Handler.create(Person.class, this::put))
                .delete("/{id}", this::delete);
    }

    private void delete(ServerRequest request, ServerResponse response) {
        String id = request.path().param("id");
        personRepository.delete(id);
        response.status(200).send();
    }

    private void list(ServerRequest request, ServerResponse response) {
        response.status(200).headers().add(Http.Header.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
        response.send(JsonbBuilder.create().toJson(personRepository.list()));
    }

    private void get(ServerRequest request, ServerResponse response) {
        String id = request.path().param("id");
        Person person = personRepository.get(id);
        if (person != null) {
            response.status(200).headers().add(Http.Header.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            response.send(JsonbBuilder.create().toJson(person));
        }
        else {
            response.status(404);
            response.send();
        }
    }

    private void post(ServerRequest request, ServerResponse response, Person person) {
        Person created = personRepository.create(person);
        response.status(200).headers().add(Http.Header.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
        response.send(created);
    }

    private void put(ServerRequest request, ServerResponse response, Person person) {
        Person updated = personRepository.update(person);
        response.status(200).headers().add(Http.Header.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
        response.send(updated);
    }
}
