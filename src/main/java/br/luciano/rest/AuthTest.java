package br.luciano.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.restassured.http.ContentType;


public class AuthTest {
	
	@Test
	public void deveAcessarSWAPI() {
		given()
			.log().ifValidationFails()
		.when()
			.get("https://swapi.dev/api/people/1")
		.then()
			.log().ifValidationFails()
			.statusCode(200)
			.body("name", is("Luke Skywalker"))
		;
	}
	
	@Test
	public void deveObterClima() {
		given()
			.log().ifValidationFails()
			.param("lat", "-23.3112878")
			.param("lon", "-51.1595023")
			.param("units", "metric")
			.param("lang", "pt_br")
			.param("appid", "6d4406d83f3a9f5049b9d9eea779f94b")
		.when()
			.get("https://api.openweathermap.org/data/2.5/weather")
		.then()
			.log().ifValidationFails()
			.statusCode(200)
			.body("name", is("Londrina"))
			.body("sys.country", is("BR"))
		;
	}
	
	@Test
	public void naoDeveAcessarSemSenha() {
		given()
			.log().ifValidationFails()
		.when()
			.get("http://restapi.wcaquino.me/basicauth")
		.then()
			.log().ifValidationFails()
			.statusCode(401)
		;
	}
	
	
	@Test
	public void deveFazerAutenticacaoBasica() {
		given()
			.log().ifValidationFails()
		.when()
			.get("http://admin:senha@restapi.wcaquino.me/basicauth")
		.then()
			.log().ifValidationFails()
			.statusCode(200)
			.body("status", is("logado"))
		;
	}
	
	@Test
	public void deveFazerAutenticacaoBasica2() {
		given()
			.log().ifValidationFails()
			.auth().basic("admin", "senha")
		.when()
			.get("http://restapi.wcaquino.me/basicauth")
		.then()
			.log().ifValidationFails()
			.statusCode(200)
			.body("status", is("logado"))
		;
	}
	
	@Test
	public void deveFazerAutenticacaoBasicaChallenge() {
		given()
			.log().ifValidationFails()
			.auth().preemptive().basic("admin", "senha")
		.when()
			.get("http://restapi.wcaquino.me/basicauth2")
		.then()
			.log().ifValidationFails()
			.statusCode(200)
			.body("status", is("logado"))
		;
	}
	
	@Test
	public void deveFazerAutenticacaoComTokenJWT() {
		Map<String, String> login = new HashMap<>();
		login.put("email", "luciano@email.com");
		login.put("senha", "123456");
		
		//login
		//receber token
		String token = given()
			.log().ifValidationFails()
			.body(login)
			.contentType(ContentType.JSON)
		.when()
			.post("https://barrigarest.wcaquino.me/signin")
		.then()
			.log().ifValidationFails()
			.statusCode(200)
			.extract().path("token")
		;
		
		
		//obter as contas
		given()
			.log().ifValidationFails()
			.header("Authorization", "JWT " + token)
		.when()
			.get("https://barrigarest.wcaquino.me/contas")
		.then()
			.log().ifValidationFails()
			.statusCode(200)
			.body("nome", notNullValue())
		;
		
	}
}
