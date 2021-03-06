package org.demis27.kickoff.quarkus.api;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ApiComicbookControllerTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/api/v1/comicbook")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}