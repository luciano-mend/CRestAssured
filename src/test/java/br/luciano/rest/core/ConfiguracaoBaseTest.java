package br.luciano.rest.core;

import org.junit.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;

public class ConfiguracaoBaseTest {

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://restapi.wcaquino.me";
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

		RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
		RestAssured.requestSpecification = requestBuilder.build();

		ResponseSpecBuilder responseBuilder = new ResponseSpecBuilder();
		RestAssured.responseSpecification = responseBuilder.build();
	}
}
