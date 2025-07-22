package br.com.inmetrics.iuriraredu.settings;

import br.com.inmetrics.iuriraredu.utils.WaitUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

/**
 * Classe base para testes automatizados utilizando Selenium WebDriver.
 * Gerencia a criação, configuração e finalização dos drivers de navegador,
 * além de fornecer suporte para espera explícita.
 *
 * <p>Utiliza ThreadLocal para garantir isolamento entre execuções paralelas.</p>
 */
public abstract class BaseTest {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> waitThreadLocal = new ThreadLocal<>();
    private static final Duration GLOBAL_TIMEOUT = Duration.ofSeconds(30);
    private static final Duration POLLING_INTERVAL = Duration.ofMillis(300);

    /**
     * Retorna a instância atual do {@link WebDriver} para a thread.
     *
     * @return WebDriver da thread atual, ou {@code null} se não inicializado
     */
    protected WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    /**
     * Retorna a instância atual do {@link WebDriverWait} para a thread.
     *
     * @return WebDriverWait da thread atual, ou {@code null} se não inicializado
     */
    protected WebDriverWait getWait() {
        return waitThreadLocal.get();
    }

    /**
     * Cria uma instância do {@link WebDriver} de acordo com o navegador especificado.
     *
     * @param browser Nome do navegador ("chrome", "edge" ou "firefox")
     * @return Nova instância de WebDriver
     * @throws IllegalArgumentException Se o navegador não for suportado
     */
    private WebDriver createDriver(String browser) {
        return switch (browser.toLowerCase()) {
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                yield new ChromeDriver();
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                yield new EdgeDriver();
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                yield new FirefoxDriver();
            }
            default -> throw new IllegalArgumentException("Navegador não suportado: " + browser);
        };
    }

    /**
     * Configura o driver, maximizando a janela e acessando a URL base.
     *
     * @param driver Instância do WebDriver a ser configurada
     * @param url URL base a ser acessada
     */
    private void configureDriver(WebDriver driver, String url) {
        driver.manage().window().maximize();
        driver.get(url);
    }

    /**
     * Cria uma instância de {@link WebDriverWait} com timeout global e polling customizado.
     * Ignora exceções comuns de elementos dinâmicos.
     *
     * @param driver Instância do WebDriver
     * @return Nova instância de WebDriverWait
     */
    private WebDriverWait createWebDriverWait(WebDriver driver) {
        return (WebDriverWait) new WebDriverWait(driver, GLOBAL_TIMEOUT, POLLING_INTERVAL)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class);
    }

    /**
     * Inicializa o navegador, configura o driver e o WebDriverWait para a thread atual.
     *
     * @param browser Nome do navegador a ser utilizado
     * @param url URL base para navegação inicial
     */
    protected void browserSetUp(String browser, String url) {
        WebDriver driver = createDriver(browser);
        configureDriver(driver, url);
        driverThreadLocal.set(driver);
        waitThreadLocal.set(createWebDriverWait(driver));
    }

    /**
     * Finaliza o navegador e remove as instâncias dos drivers das threads.
     * Libera recursos utilizados pelo WebDriver.
     */
    protected void browserTearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driverThreadLocal.remove();
            waitThreadLocal.remove();
        }
    }
}