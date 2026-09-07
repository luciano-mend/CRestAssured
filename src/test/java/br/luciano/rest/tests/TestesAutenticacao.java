package br.luciano.rest.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import br.luciano.rest.core.ConfiguracaoBaseTest;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.XmlPath.CompatibilityMode;

public class TestesAutenticacao extends ConfiguracaoBaseTest {

	@Test
	public void deveAcessarApiPublicaStarWarsComSucesso() {
		given()
		.when()
			.get("https://swapi.dev/api/people/1")
		.then()
			.statusCode(200)
			.body("name", is("Luke Skywalker"));
	}

	@Test
	public void deveObterDadosClimaticosComSucesso() {
		given()
			.param("lat", "-23.3112878")
			.param("lon", "-51.1595023")
			.param("units", "metric")
			.param("lang", "pt_br")
			.param("appid", "6d4406d83f3a9f5049b9d9eea779f94b")
		.when()
			.get("https://api.openweathermap.org/data/2.5/weather")
		.then()
			.statusCode(200)
			.body("name", is("Londrina"))
			.body("sys.country", is("BR"));
	}

	@Test
	public void naoDevePermitirAcessoSemAutenticacao() {
		given()
		.when()
			.get("/basicauth")
		.then()
			.statusCode(401);
	}

	@Test
	public void deveAutenticarComSucessoViaBasicAuthNaUrl() {
		given()
		.when()
			.get("http://admin:senha@restapi.wcaquino.me/basicauth")
		.then()
			.statusCode(200)
			.body("status", is("logado"));
	}

	@Test
	public void deveAutenticarComSucessoViaBasicAuthPadrao() {
		given()
			.auth().basic("admin", "senha")
		.when()
			.get("/basicauth")
		.then()
			.statusCode(200)
			.body("status", is("logado"));
	}

	@Test
	public void deveAutenticarComSucessoViaBasicAuthPreemptive() {
		given()
			.auth().preemptive().basic("admin", "senha")
		.when()
			.get("/basicauth2")
		.then()
			.statusCode(200)
			.body("status", is("logado"));
	}

	@Test
	public void deveAutenticarERecuperarContasViaTokenJwt() {
		Map<String, String> login = new HashMap<>();
		login.put("email", "luciano@email.com");
		login.put("senha", "123456");

		String token = given()
			.body(login)
			.contentType(ContentType.JSON)
		.when()
			.post("https://barrigarest.wcaquino.me/signin")
		.then()
			.statusCode(200)
			.extract().path("token");

		given()
			.header("Authorization", "JWT " + token)
		.when()
			.get("https://barrigarest.wcaquino.me/contas")
		.then()
			.statusCode(200)
			.body("nome", notNullValue());
	}

	@Test
	public void deveAutenticarERecuperarContasViaCookieSessao() {
		String cookie = given()
			.formParam("email", "luciano@email.com")
			.formParam("senha", "123456")
			.contentType(ContentType.URLENC.withCharset("UTF-8"))
		.when()
			.post("https://seubarriga.wcaquino.me/logar")
		.then()
			.statusCode(200)
			.extract().header("set-cookie");

		cookie = cookie.split("=")[1].split(";")[0];

		String body = given()
			.contentType(ContentType.URLENC.withCharset("UTF-8"))
			.cookie("connect.sid", cookie)
		.when()
			.get("https://seubarriga.wcaquino.me/contas")
		.then()
			.statusCode(200)
			.body("html.body.table.tbody.tr[0].td[0]", notNullValue())
			.extract().body().asString();

		XmlPath xmlPath = new XmlPath(CompatibilityMode.HTML, body);
		MatcherAssert.assertThat(xmlPath.getString("html.body.table.tbody.tr[0].td[0]"), notNullValue());
	}
}
