package br.luciano.rest.tests;

import static io.restassured.RestAssured.given;

import org.junit.Test;
import org.xml.sax.SAXParseException;

import br.luciano.rest.core.ConfiguracaoBaseTest;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;

public class TestesSchemaValidation extends ConfiguracaoBaseTest {

	@Test
	public void deveValidarSchemaXmlComSucesso() {
		given()
		.when()
			.get("/usersXML")
		.then()
			.statusCode(200)
			.body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"));
	}

	@Test(expected = SAXParseException.class)
	public void naoDeveValidarSchemaXmlInvalido() {
		given()
		.when()
			.get("/invalidUsersXML")
		.then()
			.statusCode(200)
			.body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"));
	}

	@Test
	public void deveValidarSchemaJsonComSucesso() {
		given()
		.when()
			.get("/users")
		.then()
			.statusCode(200)
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("users.json"));
	}
}
