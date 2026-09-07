package br.luciano.rest.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasXPath;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import br.luciano.rest.core.ConfiguracaoBaseTest;
import io.restassured.http.ContentType;

public class TestesHtml extends ConfiguracaoBaseTest {

	@Test
	public void deveFazerBuscasComHtml() {
		given()
		.when()
			.get("/v2/users")
		.then()
			.statusCode(200)
			.contentType(ContentType.HTML)
			.body("html.body.div.table.tbody.tr.size()", is(3))
			.body("html.body.div.table.tbody.tr[1].td[2]", is("25"))
			.appendRootPath("html.body.div.table.tbody")
			.body("tr.find{it.toString().startsWith('2')}.td[1]", is("Maria Joaquina"));
	}

	@Test
	public void deveFazerBuscasComXpathEmHtml() {
		given()
		.when()
			.get("/v2/users?format=clean")
		.then()
			.statusCode(200)
			.contentType(ContentType.HTML)
			.body(hasXPath("count(//table/tr)", is("4")))
			.body(hasXPath("//td[text() = '2']/../td[2]", is("Maria Joaquina")));
	}
}
