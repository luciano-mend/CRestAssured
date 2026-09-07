package br.luciano.rest.core;

import org.junit.jupiter.api.BeforeAll;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;

public class ConfiguracaoBaseTest {

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "http://restapi.wcaquino.me";
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

		RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
		requestBuilder.addFilter(new AllureRestAssured());
		RestAssured.requestSpecification = requestBuilder.build();

		ResponseSpecBuilder responseBuilder = new ResponseSpecBuilder();
		RestAssured.responseSpecification = responseBuilder.build();
	}
}
