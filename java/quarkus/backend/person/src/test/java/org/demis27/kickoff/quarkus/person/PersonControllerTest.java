package org.demis27.kickoff.quarkus.person;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PersonControllerTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/person/v1/persons")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}