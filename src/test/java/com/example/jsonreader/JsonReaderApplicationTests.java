package com.example.jsonreader;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(classes = Application.class)
class JsonReaderApplicationTests {

    @Value("http://localhost:${local.server.port}/persons")
    private String baseUrl;

    @Test
    void searchPersonsByCreatingObject() {
        final SearchPersonRequest searchPersonRequest = SearchPersonRequest.builder()
                .firstName("Ramin")
                .lastName("Pakzad")
                .age(31)
                .build();

        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(searchPersonRequest)
        .when()
            .post(baseUrl)
        .then()
            .statusCode(OK.value())
            .body("fullName", is("Ramin Pakzad"))
            .body("age", is(31));
        //@formatter:on
    }

    @Test
    void searchPersonsByJsonWithoutAnyParameter() {
        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(JsonUtil.loadFromFile("json/simple-object.json"))
        .when()
            .post(baseUrl)
        .then()
            .statusCode(OK.value())
            .body("fullName", is("Ramin Pakzad"))
            .body("age", is(31));
        //@formatter:on
    }

    @Test
    void searchPersonsByJsonWitParameters() {
        //@formatter:off
        given()
            .contentType(ContentType.JSON)
            .body(JsonUtil.loadFromFile("json/complex-object.json", "Ramin", "Pakzad", 31))
        .when()
            .post(baseUrl)
        .then()
            .statusCode(OK.value())
            .body("fullName", is("Ramin Pakzad"))
            .body("age", is(31));
        //@formatter:on
    }

}
