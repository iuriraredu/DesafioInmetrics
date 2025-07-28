package br.com.inmetrics.iuriraredu.hooks;

import br.com.inmetrics.iuriraredu.settings.BaseTest;
import br.com.inmetrics.iuriraredu.utils.FileManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.io.File;
import java.io.IOException;

import static br.com.inmetrics.iuriraredu.utils.ConfigManager.getPropertiesValue;
import static org.apache.commons.io.FileUtils.readFileToByteArray;

/**
 * Classe de hooks do Cucumber para inicialização e finalização de cenários de teste web.
 * Estende {@link BaseTest} para gerenciar o ciclo de vida do WebDriver.
 */
public class Hooks extends BaseTest {
    /**
     * Inicializa o navegador antes de cenários marcados com {@code @web}.
     */
    @Before(value = "@web")
    public void initWebApplication() {
        System.out.println("🔧 Iniciando o cenário de teste...");
        super.browserSetUp(
                getPropertiesValue("browser"),
                getPropertiesValue("BASEURLWEB")
        );
    }

    /**
     * Finaliza o navegador após cenários marcados com {@code @web}.
     * Captura e anexa um screenshot ao relatório do cenário.
     *
     * @param scenario Instância do cenário em execução
     */
    @After(value = "@web")
    public void finishWebApplication(Scenario scenario) {
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
}
