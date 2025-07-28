package br.com.inmetrics.iuriraredu.utils;

import static br.com.inmetrics.iuriraredu.utils.ConfigManager.getGlobalTimeout;
import static br.com.inmetrics.iuriraredu.utils.ConfigManager.getPollingInterval;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Classe utilitária para operações com Selenium WebDriver em testes automatizados.
 *
 * <p>Fornece métodos estáticos para:</p>
 * <ul>
 *   <li>Esperas explícitas para elementos clicáveis, com timeout padrão ou customizado</li>
 *   <li>Esperar o carregamento completo da página</li>
 *   <li>Configurar o tempo de espera implícito do WebDriver</li>
 *   <li>Realizar rolagem da página até um elemento, para o topo ou para o final</li>
 * </ul>
 *
 * <p>Utiliza configurações de timeout e polling, permitindo personalização conforme a necessidade dos testes.</p>
 *
 * <p>Os métodos de espera ignoram exceções de referência obsoleta e lançam {@link org.openqa.selenium.TimeoutException} caso a condição não seja atendida no tempo limite.</p>
 */
public class SeleniumUtils {

    /**
     * Aguarda até que o elemento esteja clicável, utilizando o timeout padrão definido em configuração.
     * Ignora exceções de referência obsoleta do elemento.
     *
     * @param driver  Instância do {@link WebDriver} utilizada na espera.
     * @param element Elemento {@link WebElement} a ser aguardado.
     * @return O elemento clicável, pronto para interação.
     * @throws org.openqa.selenium.TimeoutException Se o elemento não se tornar clicável dentro do tempo limite.
     */
    public static WebElement waitForClickable(WebDriver driver, WebElement element) {
        return waitForClickable(driver, element, getGlobalTimeout());
    }

    /**
     * Aguarda até que o elemento esteja clicável, utilizando um timeout customizado.
     * Ignora exceções de referência obsoleta do elemento.
     *
     * @param driver  Instância do {@link WebDriver} utilizada na espera.
     * @param element Elemento {@link WebElement} a ser aguardado.
     * @param timeout Tempo máximo de espera.
     * @return O elemento clicável, pronto para interação.
     * @throws org.openqa.selenium.TimeoutException Se o elemento não se tornar clicável dentro do tempo limite.
     */
    public static WebElement waitForClickable(WebDriver driver, WebElement element, Duration timeout) {
        return new WebDriverWait(driver, timeout)
                .pollingEvery(getPollingInterval())
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Aguarda até que o carregamento da página esteja completo, verificando o estado do documento via JavaScript.
     *
     * @param driver  Instância do {@link WebDriver} utilizada na espera.
     * @param timeout Tempo máximo de espera.
     * @throws org.openqa.selenium.TimeoutException Se a página não carregar completamente dentro do tempo limite.
     */
    public static void waitForPageLoad(WebDriver driver, Duration timeout) {
        new WebDriverWait(driver, timeout).until(
                webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Configura o tempo de espera implícito do {@link WebDriver} com o valor definido na configuração.
     *
     * @param driver Instância do {@link WebDriver} a ser configurada.
     */
    public static void implicitlyWait(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(getGlobalTimeout());
    }

    /**
     * Realiza a rolagem da página até que o elemento informado esteja visível na viewport.
     *
     * @param driver  Instância do {@link WebDriver} utilizada para executar o JavaScript.
     * @param element Elemento {@link WebElement} para o qual será realizada a rolagem.
     */
    public static void scrollToElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);", element
        );
    }

    /**
     * Realiza a rolagem da página para o topo.
     *
     * @param driver Instância do {@link WebDriver} utilizada para executar o JavaScript.
     */
    public static void scrollToTop(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

    /**
     * Realiza a rolagem da página até o final (parte inferior).
     *
     * @param driver Instância do {@link WebDriver} utilizada para executar o JavaScript.
     */
    public static void scrollToBottom(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    /**
     * Realiza um clique no elemento informado utilizando JavaScript.
     *
     * <p>Útil para casos em que o clique padrão do Selenium não funciona devido a sobreposição de elementos,
     * animações ou outros problemas de interação.</p>
     *
     * @param driver  Instância do {@link WebDriver} utilizada para executar o JavaScript.
     * @param element Elemento {@link WebElement} que será clicado via script.
     */
    public static void jsClick(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}