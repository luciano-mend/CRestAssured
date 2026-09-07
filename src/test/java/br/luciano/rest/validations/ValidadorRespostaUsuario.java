package br.luciano.rest.validations;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

import io.restassured.response.Response;

public class ValidadorRespostaUsuario {

	private final Response resposta;

	public ValidadorRespostaUsuario(Response resposta) {
		this.resposta = resposta;
	}

	public static ValidadorRespostaUsuario validar(Response resposta) {
		return new ValidadorRespostaUsuario(resposta);
	}

	public ValidadorRespostaUsuario validarId(Integer idEsperado) {
		resposta.then().body("id", is(idEsperado));
		return this;
	}

	public ValidadorRespostaUsuario validarNome(String nomeEsperado) {
		resposta.then().body("name", containsString(nomeEsperado));
		return this;
	}

	public ValidadorRespostaUsuario validarIdadeMinima(Integer idadeMinima) {
		resposta.then().body("age", greaterThan(idadeMinima));
		return this;
	}

	public ValidadorRespostaUsuario validarEndereco(String ruaEsperada, Integer numeroEsperado) {
		resposta.then()
				.body("endereco.rua", is(ruaEsperada))
				.body("endereco.numero", is(numeroEsperado));
		return this;
	}

	public ValidadorRespostaUsuario validarAtributosCompletosUsuario(Integer idEsperado,
			String nomeEsperado, Integer idadeEsperada, Number salarioEsperado, String ruaEsperada,
			Integer numeroEsperado) {
		resposta.then()
				.body("id", is(idEsperado))
				.body("name", is(nomeEsperado))
				.body("age", is(idadeEsperada))
				.body("salary", is(salarioEsperado))
				.body("endereco.rua", is(ruaEsperada))
				.body("endereco.numero", is(numeroEsperado));
		return this;
	}
}
