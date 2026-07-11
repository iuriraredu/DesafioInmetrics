package br.com.inmetrics.iuriraredu.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

import static io.cucumber.junit.CucumberOptions.SnippetType.UNDERSCORE;

/**
 * Runner de testes automatizados utilizando Cucumber com JUnit.
 *
 * <p>Responsável por:</p>
 * <ul>
 *   <li>Configurar os diretórios de features para testes de API e Web</li>
 *   <li>Definir os pacotes de step definitions e hooks</li>
 *   <li>Gerar relatórios nos formatos HTML, JSON e JUnit</li>
 *   <li>Executar apenas cenários marcados com a tag <b>@AdvantageShopping</b></li>
 *   <li>Utilizar snippets no padrão CamelCase</li>
 *   <li>Exibir saída dos testes de forma legível (monochrome)</li>
 * </ul>
 *

 * <h3>Tags disponiveis:</h3>
 * <ul>
 *     <li><b>@AdvantageShopping</b> - Executa os testes relacionados ao site Advantage Shopping</li>
 *     <li><b>@All</b> - Executa todos os testes disponíveis</li>
 *     <li><b>@API</b> - Executa os testes de API</li>
 *     <li><b>@WEB</b> - Executa os testes de interface web</li>
 *     <li><b>@CarrinhoDeCompras</b> - Executa os testes relacionados ao Carrinho De Compras
 *     <li><b>@AtualizarImagem</b> - Executa os testes relacionados a atualizar imagem de produtos por API</li>
 *     <li><b>@BuscarProdutos</b> - Executa os testes relacionados a busca de produtos por API </li>
 * </ul>
 *
 * <p>Esta classe deve permanecer vazia, servindo apenas como ponto de entrada para o JUnit executar os testes do Cucumber.</p>
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                "src/test/resources/features/api",
                "src/test/resources/features/web"
        },
        glue = {
                "br.com.inmetrics.iuriraredu.steps.api",
                "br.com.inmetrics.iuriraredu.steps.web",
                "br.com.inmetrics.iuriraredu.hooks"
        },
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-reports.html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/junit-report.xml"
        },
        monochrome = true,
        snippets = UNDERSCORE,
        tags = "@WEB"
)
public class RunTest {
}