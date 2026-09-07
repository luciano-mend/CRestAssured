package br.luciano.rest.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.luciano.rest.tests.TestesAutenticacao;
import br.luciano.rest.tests.TestesEnvioParametros;
import br.luciano.rest.tests.TestesHtml;
import br.luciano.rest.tests.TestesSchemaValidation;
import br.luciano.rest.tests.TestesUploadDownloadArquivo;
import br.luciano.rest.tests.TestesUsuarioJson;
import br.luciano.rest.tests.TestesUsuarioXml;
import br.luciano.rest.tests.TestesVerbosHttp;

@RunWith(Suite.class)
@SuiteClasses({
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
