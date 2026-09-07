package br.luciano.rest.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import br.luciano.rest.core.ConfiguracaoBaseTest;

public class TestesUploadDownloadArquivo extends ConfiguracaoBaseTest {

	@Test
	public void deveObrigarEnviarArquivo() {
		given()
		.when()
			.post("/upload")
		.then()
			.statusCode(404)
			.body("error", is("Arquivo não enviado"));
	}

	@Test
	public void deveFazerUploadArquivoComSucesso() {
		given()
			.multiPart("arquivo", new File("src/test/resources/arquivo.pdf"))
		.when()
			.post("/upload")
		.then()
			.statusCode(200)
			.body("name", is("arquivo.pdf"));
	}

	@Test
	public void naoDeveFazerUploadArquivoGrande() {
		given()
			.multiPart("arquivo", new File("src/test/resources/arquivoGrande.zip"))
		.when()
			.post("/upload")
		.then()
			.time(lessThan(5000L))
			.statusCode(413);
	}

	@Test
	public void deveBaixarArquivoComSucesso() throws IOException {
		byte[] image = given()
		.when()
			.get("/download")
		.then()
			.statusCode(200)
			.extract().asByteArray();

		File imagem = new File("src/test/resources/file.jpg");
		try (OutputStream out = new FileOutputStream(imagem)) {
			out.write(image);
		}

		MatcherAssert.assertThat(imagem.length(), lessThan(95000L));
	}
}
