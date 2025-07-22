package br.com.inmetrics.iuriraredu.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Classe utilitária para operações de espera explícita e implícita em testes automatizados com Selenium WebDriver.
 * Fornece métodos estáticos para aguardar elementos clicáveis, carregamento completo da página e configurar o tempo de espera implícito.
 *
 * <p>Utiliza configurações de timeout e polling, permitindo personalização conforme necessidade dos testes.</p>
 *
 * <ul>
 *   <li>{@link #waitForClickable(WebDriver, WebElement)}: Aguarda até que o elemento esteja clicável usando o timeout padrão.</li>
 *   <li>{@link #waitForClickable(WebDriver, WebElement, Duration)}: Aguarda até que o elemento esteja clicável com timeout customizado.</li>
 *   <li>{@link #waitForPageLoad(WebDriver, Duration)}: Aguarda o carregamento completo da página.</li>
 *   <li>{@link #implicitlyWait(WebDriver)}: Configura o tempo de espera implícito do WebDriver.</li>
 * </ul>
 *
 * <p>Os métodos ignoram exceções de referência obsoleta e lançam {@link org.openqa.selenium.TimeoutException} caso a condição não seja atendida no tempo limite.</p>
 */
public class WaitUtils {

    /** Intervalo de polling padrão para verificação de condições durante a espera. */
    private static final Duration POLLING = Duration.ofMillis(150);

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
        return waitForClickable(driver, element, ConfigManager.getWaitPropertyDuration());
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
                .pollingEvery(POLLING)
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
    public static void implicitlyWait(WebDriver driver){
        driver.manage().timeouts().implicitlyWait(ConfigManager.getWaitPropertyDuration());
    }
}