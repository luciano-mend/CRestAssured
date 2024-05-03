package br.luciano.rest;

import static io.restassured.RestAssured.given;

import org.junit.Test;
import org.xml.sax.SAXParseException;

import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;

public class SchemaTest {
	
	@Test
	public void deveValidarSchemaXML() {
		given()
			.log().ifValidationFails()
		.when()
			.get("https://restapi.wcaquino.me/usersXML")
		.then()
			.log().ifValidationFails()
			.statusCode(200)
			.body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"))
		;
	}
	
	@Test(expected = SAXParseException.class)
	public void naoDeveValidarSchemaXMLInvalido() {
		given()
			.log().ifValidationFails()
		.when()
			.get("https://restapi.wcaquino.me/invalidUsersXML")
		.then()
			.log().ifValidationFails()
			.statusCode(200)
			.body(RestAssuredMatchers.matchesXsdInClasspath("users.xsd"))
		;
	}
	
	@Test
	public void deveValidarSchemaJson() {
		given()
			.log().ifValidationFails()
		.when()
			.get("https://restapi.wcaquino.me/users")
		.then()
			.log().ifValidationFails()
			.statusCode(200)
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("users.json"))
		;
	}

}
