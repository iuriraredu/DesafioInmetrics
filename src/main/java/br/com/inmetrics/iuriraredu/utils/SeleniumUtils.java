package br.com.inmetrics.iuriraredu.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static br.com.inmetrics.iuriraredu.utils.ConfigManager.getGlobalTimeout;

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
public class SeleniumUtils{


    /**
     * Aguarda até que o elemento esteja clicável, usando um tempo limite personalizado.
     * Ignora exceções de referência obsoleta do elemento durante a espera.
     *
     * @param wait    Instância de {@link WebDriverWait} utilizada para aguardar a condição.
     * @param element Elemento {@link WebElement} que deve se tornar clicável.
     * @return O elemento clicável, pronto para interação.
     * @throws org.openqa.selenium.TimeoutException Se o elemento não ficar clicável dentro do tempo limite.
     */
    public static WebElement waitForClickable(WebDriverWait wait, WebElement element){
        return wait.ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Verifica se o elemento está realmente clicável, ou seja, se não está coberto por outro elemento.
     *
     * @param driver  Instância do {@link WebDriver} utilizada para executar o JavaScript.
     * @param element Elemento {@link WebElement} a ser verificado.
     * @return {@code true} se o elemento é clicável, {@code false} caso contrário.
     */
    private static boolean isElementClickable(WebDriver driver, WebElement element){
        return (Boolean) ((JavascriptExecutor) driver).executeScript(
                "var elem = arguments[0];" +
                        "var rect = elem.getBoundingClientRect();" +
                        "var x = rect.left + rect.width/2;" +
                        "var y = rect.top + rect.height/2;" +
                        "return document.elementFromPoint(x, y) === elem;",
                element
        );
    }

    /**
     * Aguarda até que o carregamento da página esteja completo, verificando o estado do documento via JavaScript.
     *
     * @param driver  Instância do {@link WebDriver} utilizada na espera.
     * @param timeout Tempo máximo de espera.
     * @throws org.openqa.selenium.TimeoutException Se a página não carregar completamente dentro do tempo limite.
     */
    public static void waitForPageLoad(WebDriverWait wait, WebDriver driver, Duration timeout){
        wait.ignoring(StaleElementReferenceException.class)
                .until(webDriver -> ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState").equals("complete")
                );
    }

    /**
     * Configura o tempo de espera implícito do {@link WebDriver} com o valor definido na configuração.
     *
     * @param driver Instância do {@link WebDriver} a ser configurada.
     */
    public static void implicitlyWait(WebDriver driver){
        driver.manage().timeouts().implicitlyWait(getGlobalTimeout());
    }

    /**
     * Realiza a rolagem da página até que o elemento informado esteja visível na viewport.
     *
     * @param driver  Instância do {@link WebDriver} utilizada para executar o JavaScript.
     * @param element Elemento {@link WebElement} para o qual será realizada a rolagem.
     */
    public static void scrollToElement(WebDriver driver, WebElement element){
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);", element
        );
    }

    /**
     * Realiza a rolagem da página para o topo.
     *
     * @param driver Instância do {@link WebDriver} utilizada para executar o JavaScript.
     */
    public static void scrollToTop(WebDriver driver){
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }

    /**
     * Realiza a rolagem da página até o final (parte inferior).
     *
     * @param driver Instância do {@link WebDriver} utilizada para executar o JavaScript.
     */
    public static void scrollToBottom(WebDriver driver){
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
    public static void jsClick(WebDriver driver, WebElement element){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public static boolean tryToClick(WebDriver driver, WebDriverWait wait, WebElement element){
        try {
            // 1ª Tentativa: Clique normal com wait
            wait.until(ExpectedConditions.elementToBeClickable(element))
                    .click();
            return true;

        } catch (Exception e1) {
            try {
                // 2ª Tentativa: JavaScript click
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                return true;

            } catch (Exception e2) {
                // 3ª Tentativa: Scroll + Click
                try {
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
                    Thread.sleep(500); // Pequena pausa após scroll
                    element.click();
                    return true;

                } catch (Exception e3) {
                    return false;
                }
            }
        }
    }

    /**
     * Tenta enviar texto com múltiplas estratégias
     */
    public static boolean tryToSendKeys(WebDriver driver, WebDriverWait wait, WebElement element, String text){
        try {
            // 1ª Tentativa: SendKeys normal com wait
            wait.until(ExpectedConditions.elementToBeClickable(element))
                    .sendKeys(text);
            return true;

        } catch (Exception e1) {
            try {
                // 2ª Tentativa: JavaScript focus + sendKeys
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].focus(); arguments[0].value = arguments[1];",
                        element, text);
                return true;

            } catch (Exception e2) {
                // 3ª Tentativa: Clear + SendKeys forçado
                try {
                    element.clear();
                    element.sendKeys(text);
                    return true;

                } catch (Exception e3) {
                    return false;
                }
            }
        }
    }

    /**
     * Tenta múltiplas estratégias para obter texto de um elemento
     */
    private static String tryToGetText(WebDriver driver, WebDriverWait wait, WebElement element){
        // 1ª Tentativa: getText() padrão com wait
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(element))
                    .getText();
        } catch (Exception e) {
            // 2ª Tentativa: getAttribute("textContent")
            try {
                return element.getAttribute("textContent");
            } catch (Exception e2) {
                // 3ª Tentativa: JavaScript
                try {
                    return (String) ((JavascriptExecutor) driver)
                            .executeScript("return arguments[0].textContent || arguments[0].innerText", element);
                } catch (Exception e3) {
                    // 4ª Tentativa: getAttribute("value") para inputs
                    try {
                        return element.getAttribute("value");
                    } catch (Exception e4) {
                        return null;
                    }
                }
            }
        }
    }
}