package br.luciano.rest.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import org.junit.Test;

import br.luciano.rest.core.ConfiguracaoBaseTest;
import io.restassured.http.ContentType;

public class TestesEnvioParametros extends ConfiguracaoBaseTest {

	@Test
	public void deveEnviarValorViaQueryStringNaUrl() {
		given()
		.when()
			.get("/v2/users?format=xml")
		.then()
			.statusCode(200)
			.contentType(ContentType.XML);
	}

	@Test
	public void deveEnviarValorViaQueryParam() {
		given()
			.queryParam("format", "json")
		.when()
			.get("/v2/users")
		.then()
			.statusCode(200)
			.contentType(ContentType.JSON)
			.contentType(containsString("utf-8"));
	}

	@Test
	public void deveEnviarValorViaHeaderAccept() {
		given()
			.accept(ContentType.XML)
		.when()
			.get("/v2/users")
		.then()
			.statusCode(200)
			.contentType(ContentType.XML);
	}
}
