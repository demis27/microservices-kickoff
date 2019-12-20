package org.demis27.kickoff.quarkus.comicbook;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ComicbookControllerTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/comicbook/v1/comicbooks")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}