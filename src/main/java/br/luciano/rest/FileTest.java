package br.luciano.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

public class FileTest {
	
	@Test
	public void deveObrigarEnviarArquivo() {
		given()
			.log().ifValidationFails()
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().ifValidationFails()
			.statusCode(404) //deveria ser 400
			.body("error", is("Arquivo não enviado"))
		;
	}
	
	@Test
	public void deveFazerUploadArquivo() {
		given()
			.log().ifValidationFails()
			.multiPart("arquivo", new File("src/main/resources/arquivo.pdf"))
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().ifValidationFails()
			.statusCode(200)
			.body("name", is("arquivo.pdf"))
		;
	}
	
	@Test
	public void naoDeveFazerUploadArquivoGrande() {
		given()
			.log().ifValidationFails()
			.multiPart("arquivo", new File("src/main/resources/arquivoGrande.zip"))
		.when()
			.post("http://restapi.wcaquino.me/upload")
		.then()
			.log().ifValidationFails()
			.time(lessThan(2300L))
			.statusCode(413)
		;
	}
	
	@Test
	public void deveBaxarArquivo() throws IOException {
		byte[] image = given()
			.log().ifValidationFails()
		.when()
			.get("http://restapi.wcaquino.me/download")
		.then()
			.log().ifValidationFails()
			.statusCode(200)
			.extract().asByteArray()
		;
		
		File imagem = new File("src/main/resources/file.jpg");
		OutputStream out = new FileOutputStream(imagem);
		out.write(image);
		out.close();
		
		MatcherAssert.assertThat(imagem.length(), lessThan(95000L));
	}

}
