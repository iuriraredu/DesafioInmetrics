package br.com.inmetrics.iuriraredu.runner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.SNIPPET_TYPE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.FILTER_TAGS_PROPERTY_NAME;

/**
 * Runner de testes automatizados utilizando Cucumber com JUnit.
 *
 * <p>Responsável por:</p>
 * <ul>
 *   <li>Configurar os diretórios de features para testes de API e Web</li>
 *   <li>Definir os pacotes de step definitions e hooks</li>
 *   <li>Gerar relatórios nos formatos HTML, JSON e JUnit</li>
 *   <li>Executar apenas cenários marcados com a tag <b>{@code @AdvantageShopping}</b></li>
 *   <li>Utilizar snippets no padrão CamelCase</li>
 *   <li>Exibir saída dos testes de forma legível (monochrome)</li>
 * </ul>
 *

 * <h3>Tags disponiveis:</h3>
 * <ul>
 *     <li><b>{@code @AdvantageShopping}</b> - Executa os testes relacionados ao site Advantage Shopping</li>
 *     <li><b>{@code @All}</b> - Executa todos os testes disponíveis</li>
 *     <li><b>{@code @API}</b> - Executa os testes de API</li>
 *     <li><b>{@code @WEB}</b> - Executa os testes de interface web</li>
 *     <li><b>{@code @MOBILE}</b> - Executa os testes de interface mobile</li>
 *     <li><b>{@code @CarrinhoDeCompras}</b> - Executa os testes relacionados ao Carrinho De Compras
 *     <li><b>{@code @AtualizarImagem}</b> - Executa os testes relacionados a atualizar imagem de produtos por API</li>
 *     <li><b>{@code @BuscarProdutos}</b> - Executa os testes relacionados a busca de produtos por API </li>
 * </ul>
 *
 * <p>Esta classe deve permanecer vazia, servindo apenas como ponto de entrada para o JUnit executar os testes do Cucumber.</p>
 */

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features") // Aponta para a pasta onde ficam seus arquivos .feature
// Mapeia os pacotes onde estão os seus steps e hooks
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "br.com.inmetrics.iuriraredu.steps.api, br.com.inmetrics.iuriraredu.steps.web, br.com.inmetrics.iuriraredu.hooks")
// Configura a geração dos relatórios (HTML, JSON, XML) e a exibição no console
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-reports/cucumber-reports.html, json:target/cucumber-reports/cucumber.json, junit:target/cucumber-reports/junit-report.xml")
// Define o padrão CamelCase para os snippets gerados automaticamente
@ConfigurationParameter(key = SNIPPET_TYPE_PROPERTY_NAME, value = "camelcase")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@WEB")
public class RunTest {
}