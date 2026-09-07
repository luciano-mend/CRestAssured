package br.luciano.rest.suite;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import br.luciano.rest.tests.TestesAutenticacao;
import br.luciano.rest.tests.TestesEnvioParametros;
import br.luciano.rest.tests.TestesHtml;
import br.luciano.rest.tests.TestesSchemaValidation;
import br.luciano.rest.tests.TestesUploadDownloadArquivo;
import br.luciano.rest.tests.TestesUsuarioJson;
import br.luciano.rest.tests.TestesUsuarioXml;
import br.luciano.rest.tests.TestesVerbosHttp;

@Suite
@SelectClasses({
	TestesAutenticacao.class,
	TestesVerbosHttp.class,
	TestesUsuarioJson.class,
	TestesUsuarioXml.class,
	TestesUploadDownloadArquivo.class,
	TestesSchemaValidation.class,
	TestesEnvioParametros.class,
	TestesHtml.class
})
public class SuiteDeTestes {
}
