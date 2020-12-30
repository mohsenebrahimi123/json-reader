package com.example.jsonreader;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import io.restassured.http.ContentType;

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
		//Map.of("firstName", "Mohsen");
		Map<String, Object> values = new HashMap<>();
		values.put("firstName", "Ramin");
		values.put("lastName", "Pakzad");
		values.put("age", "31");

		//@formatter:off
		given()
				.contentType(ContentType.JSON)
//            .body(JsonUtil.loadFromFile("json/complex-object.json", "Ramin", "Pakzad", 31))
				.body(JsonUtil.loadFromTemplateFile("/src/test/resources/vm/complex-object.vm", values))
				.when()
				.post(baseUrl)
				.then()
				.statusCode(OK.value())
				.body("fullName", is("Ramin Pakzad"))
				.body("age", is(31));
		//@formatter:on
	}

}
