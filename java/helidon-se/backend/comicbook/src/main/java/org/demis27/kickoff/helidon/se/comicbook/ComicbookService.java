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

package org.demis27.kickoff.helidon.se.comicbook;

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
import org.demis27.kickoff.helidon.se.common.Comicbook;

public class ComicbookService implements Service {

    private final AtomicReference<String> greeting = new AtomicReference<>();

    ComicbookService(Config config) {
        greeting.set(config.get("app.comicbook").asString().orElse("Ciao"));
    }

    private ComicbookRepository comicbookRepository = new ComicbookRepository();

    @Override
    public void update(Routing.Rules rules) {
        rules.get("/", this::list)
                .get("/{id}", this::get)
                .post("/", Handler.create(Comicbook.class, this::post))
                .put("/{id}", Handler.create(Comicbook.class, this::put))
                .delete("/{id}", this::delete);
    }

    private void delete(ServerRequest request, ServerResponse response) {
        String id = request.path().param("id");
        comicbookRepository.delete(id);
        response.status(200).send();
    }

    private void list(ServerRequest request, ServerResponse response) {
        response.status(200).headers().add(Http.Header.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
        response.send(JsonbBuilder.create().toJson(comicbookRepository.list()));
    }

    private void get(ServerRequest request, ServerResponse response) {
        String id = request.path().param("id");
        Comicbook comicbook = comicbookRepository.get(id);
        if (comicbook != null) {
            response.status(200).headers().add(Http.Header.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
            response.send(JsonbBuilder.create().toJson(comicbook));
        }
        else {
            response.status(404);
            response.send();
        }
    }

    private void post(ServerRequest request, ServerResponse response, Comicbook comicbook) {
        Comicbook created = comicbookRepository.create(comicbook);
        response.status(200).headers().add(Http.Header.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
        response.send(created);
    }

    private void put(ServerRequest request, ServerResponse response, Comicbook comicbook) {
        Comicbook updated = comicbookRepository.update(comicbook);
        response.status(200).headers().add(Http.Header.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
        response.send(updated);
    }
}
