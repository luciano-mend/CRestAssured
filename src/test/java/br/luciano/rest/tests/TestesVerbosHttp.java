package br.luciano.rest.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.luciano.rest.core.ConfiguracaoBaseTest;
import br.luciano.rest.model.Usuario;
import io.restassured.http.ContentType;

public class TestesVerbosHttp extends ConfiguracaoBaseTest {

	@Test
	public void deveCadastrarUsuarioComSucessoViaJsonString() {
		given()
			.contentType(ContentType.JSON)
			.body("{ \"name\": \"Jose\",\"age\": 50 }")
		.when()
			.post("/users")
		.then()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Jose"))
			.body("age", is(50));
	}

	@Test
	public void deveCadastrarUsuarioComSucessoViaMap() {
		Map<String, Object> params = new HashMap<>();
		params.put("name", "Usuario via map");
		params.put("age", 25);

		given()
			.contentType(ContentType.JSON)
			.body(params)
		.when()
			.post("/users")
		.then()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Usuario via map"))
			.body("age", is(25));
	}

	@Test
	public void deveCadastrarUsuarioComSucessoViaObjeto() {
		Usuario usuario = new Usuario("Usuario via Objeto", 35);

		given()
			.contentType(ContentType.JSON)
			.body(usuario)
		.when()
			.post("/users")
		.then()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("name", is("Usuario via Objeto"))
			.body("age", is(35));
	}

	@Test
	public void deveCadastrarEDeserializarObjetoUsuario() {
		Usuario usuario = new Usuario("Usuario deserializando", 35);

		Usuario usuarioInserido = given()
			.contentType(ContentType.JSON)
			.body(usuario)
		.when()
			.post("/users")
		.then()
			.statusCode(201)
			.extract().body().as(Usuario.class);

		MatcherAssert.assertThat(usuarioInserido.getId(), notNullValue());
		Assertions.assertEquals("Usuario deserializando", usuarioInserido.getName());
		MatcherAssert.assertThat(usuarioInserido.getAge(), is(35));
	}

	@Test
	public void naoDeveCadastrarUsuarioSemNomeObrigatorio() {
		given()
			.contentType(ContentType.JSON)
			.body("{ \"age\": 50 }")
		.when()
			.post("/users")
		.then()
			.statusCode(400)
			.body("id", is(nullValue()))
			.body("error", is("Name é um atributo obrigatório"));
	}

	@Test
	public void deveCadastrarUsuarioComSucessoViaXmlString() {
		given()
			.contentType(ContentType.XML)
			.body("<user><name>Jose</name><age>50</age></user>")
		.when()
			.post("/usersXML")
		.then()
			.statusCode(201)
			.body("user.@id", is(notNullValue()))
			.body("user.name", is("Jose"))
			.body("user.age", is("50"));
	}

	@Test
	public void deveCadastrarUsuarioComSucessoViaObjetoXml() {
		Usuario usuario = new Usuario("Usuario XML", 40);

		given()
			.contentType(ContentType.XML)
			.body(usuario)
		.when()
			.post("/usersXML")
		.then()
			.statusCode(201)
			.body("user.@id", is(notNullValue()))
			.body("user.name", is("Usuario XML"))
			.body("user.age", is("40"));
	}

	@Test
	public void deveCadastrarEDeserializarObjetoUsuarioXml() {
		Usuario usuario = new Usuario("Usuario XML", 40);

		Usuario usuarioInserido = given()
			.contentType(ContentType.XML)
			.body(usuario)
		.when()
			.post("/usersXML")
		.then()
			.statusCode(201)
			.extract().body().as(Usuario.class);

		MatcherAssert.assertThat(usuarioInserido.getId(), notNullValue());
		Assertions.assertEquals("Usuario XML", usuarioInserido.getName());
		MatcherAssert.assertThat(usuarioInserido.getAge(), is(40));
	}

	@Test
	public void deveAtualizarUsuarioComSucessoViaPut() {
		given()
			.contentType(ContentType.JSON)
			.body("{ \"name\": \"Usuario alterado\",\"age\": 80 }")
		.when()
			.put("/users/1")
		.then()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Usuario alterado"))
			.body("age", is(80))
			.body("salary", is(1234.5678f));
	}

	@Test
	public void deveAtualizarUsuarioUsandoParametrosNaUrl() {
		given()
			.contentType(ContentType.JSON)
			.body("{ \"name\": \"Usuario alterado\",\"age\": 80 }")
		.when()
			.put("/{entidade}/{userId}", "users", "1")
		.then()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Usuario alterado"))
			.body("age", is(80))
			.body("salary", is(1234.5678f));
	}

	@Test
	public void deveAtualizarUsuarioUsandoPathParams() {
		given()
			.contentType(ContentType.JSON)
			.body("{ \"name\": \"Usuario alterado\",\"age\": 80 }")
			.pathParam("entidade", "users")
			.pathParam("userId", 1)
		.when()
			.put("/{entidade}/{userId}")
		.then()
			.statusCode(200)
			.body("id", is(1))
			.body("name", is("Usuario alterado"))
			.body("age", is(80))
			.body("salary", is(1234.5678f));
	}

	@Test
	public void deveRemoverUsuarioComSucessoViaDelete() {
		given()
			.pathParam("entidade", "users")
			.pathParam("userId", 1)
		.when()
			.delete("/{entidade}/{userId}")
		.then()
			.statusCode(204);
	}

	@Test
	public void naoDeveRemoverUsuarioInexistente() {
		given()
			.pathParam("entidade", "users")
			.pathParam("userId", 1000)
		.when()
			.delete("/{entidade}/{userId}")
		.then()
			.statusCode(400)
			.body("error", is("Registro inexistente"));
	}
}
