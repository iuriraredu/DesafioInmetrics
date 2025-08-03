package br.com.inmetrics.iuriraredu.hooks;

import br.com.inmetrics.iuriraredu.settings.BaseTest;
import br.com.inmetrics.iuriraredu.utils.FileManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.io.File;
import java.io.IOException;

import static br.com.inmetrics.iuriraredu.utils.ConfigManager.getPropertiesValue;
import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.implicitlyWait;
import static org.apache.commons.io.FileUtils.readFileToByteArray;

/**
 * Hooks do Cucumber para inicialização e finalização de cenários de teste web e API.
 *
 * <p>Responsável por:</p>
 * <ul>
 *   <li>Inicializar o navegador e acessar a URL base antes de cenários marcados com <b>@web</b></li>
 *   <li>Inicializar contexto de testes de API antes de cenários marcados com <b>@api</b></li>
 *   <li>Capturar e anexar screenshot ao relatório após cenários <b>@web</b></li>
 *   <li>Finalizar o navegador ou contexto de API após execução dos cenários</li>
 * </ul>
 *
 * <p>Utiliza propriedades de configuração para browser e URL base. Estende {@link BaseTest} para gerenciar o ciclo de vida do WebDriver e API.</p>
 *
 * <p>Os métodos são executados automaticamente pelo Cucumber, de acordo com as tags definidas nos cenários.</p>
 */
public class Hooks extends BaseTest {
    /**
     * Inicializa o navegador antes de cenários marcados com {@code @web}.
     */
    @Before(value = "@WEB")
    public void initWebApplication() {
        System.out.println("🔧 Iniciando o cenário de teste...");
        super.browserSetUp(
                getPropertiesValue("BROWSER"),
                getPropertiesValue("BASEURL")
        );
        implicitlyWait(getDriver());
    }

    /**
     * Finaliza o navegador após cenários marcados com {@code @web}.
     * Captura e anexa um screenshot ao relatório do cenário.
     *
     * @param scenario Instância do cenário em execução
     */
    @After(value = "@WEB")
    public void finishWebApplication(Scenario scenario) {
        implicitlyWait(getDriver());
        try {
            String screenshotPath = FileManager.takeScreenShot(getDriver(), scenario.getName());
            if (screenshotPath != null) {
                scenario.attach(readFileToByteArray(
                                new File(screenshotPath)),
                        "image/png",
                        "Screenshot"
                );
            } else {
                System.err.println("Screenshot não foi capturado.");
            }
        } catch (IOException e) {
            System.err.println("Erro ao capturar screenshot: " + e.getMessage());
        }

        super.browserTearDown();
        System.out.println("🧹 Finalizando o cenário de teste...");
    }

    /**
     * Inicializa o contexto de testes de API antes de cenários marcados com {@code @api}, configurando a URL base.
     */
    @Before(value = "@API")
    public void initApiApplication() {
        System.out.println("🔧 Iniciando o cenário de teste API...");
        super.apiSetUp(
                getPropertiesValue("BASEURL")
        );
    }

    /**
     * Finaliza o contexto de testes de API de cenários marcados com {@code @api} e realiza limpezas necessárias.
     */
    @After(value = "@API")
    public void finishApiApplication() {
        super.apiTearDown();
        System.out.println("🧹 Finalizando o cenário de teste API...");
    }
}
