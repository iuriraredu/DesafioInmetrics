package br.com.inmetrics.iuriraredu.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Classe utilitária para operações de espera explícita em testes automatizados com Selenium WebDriver.
 * Permite aguardar elementos clicáveis e o carregamento completo da página, utilizando configurações de timeout e polling.
 *
 * <p>Os métodos são estáticos e podem ser utilizados em qualquer contexto de teste.</p>
 */
public class WaitUtils {

    /** Timeout padrão para operações de espera, definido nas propriedades do projeto. */
    private static final Duration TIMEOUT = Duration.ofSeconds(
            Long.parseLong(ConfigManager.getPropertiesValue("wait"))
    );

    /** Intervalo de polling padrão para verificação de condições durante a espera. */
    private static final Duration POLLING = Duration.ofMillis(150);

    /**
     * Aguarda até que o elemento esteja clicável, utilizando o timeout padrão.
     * Ignora exceções de referência obsoleta do elemento.
     *
     * @param driver  Instância do {@link WebDriver} utilizada na espera.
     * @param element Elemento {@link WebElement} a ser aguardado.
     * @return O elemento clicável, pronto para interação.
     * @throws org.openqa.selenium.TimeoutException Se o elemento não se tornar clicável dentro do tempo limite.
     */
    public static WebElement waitForClickable(WebDriver driver, WebElement element) {
        return waitForClickable(driver, element, TIMEOUT);
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

    public static void implicitlyWait(WebDriver driver){
        driver.manage().timeouts().implicitlyWait(ConfigManager.getWaitPropertyDuration());
    }
}