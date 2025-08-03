package br.com.inmetrics.iuriraredu.settings;

import br.com.inmetrics.iuriraredu.utils.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static br.com.inmetrics.iuriraredu.utils.ConfigManager.getGlobalTimeout;
import static br.com.inmetrics.iuriraredu.utils.ConfigManager.getPollingInterval;
import static java.lang.Boolean.parseBoolean;

/**
 * Classe base para testes automatizados utilizando Selenium WebDriver.
 * Gerencia a criação, configuração e finalização dos drivers de navegador,
 * além de fornecer suporte para espera explícita.
 *
 * <p>Utiliza ThreadLocal para garantir isolamento entre execuções paralelas.</p>
 */
public abstract class BaseTest {
    /** ThreadLocal para armazenar a instância do WebDriver.
     * Garante que cada thread tenha sua própria instância de WebDriver.
     */
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    /** ThreadLocal para armazenar a instância do WebDriverWait.
     * Garante que cada thread tenha sua própria instância de espera explícita.
     */
    private static final ThreadLocal<WebDriverWait> waitThreadLocal = new ThreadLocal<>();
    /** ThreadLocal para armazenar a instância do RequestSpecification.
     * Garante que cada thread tenha sua própria configuração de requisição.
     */
    private static final ThreadLocal<RequestSpecification> requestSpecThreadLocal = new ThreadLocal<>();
    /** ThreadLocal para armazenar a última Response da API.
     * Permite que o step 'Then' acesse a resposta da requisição feita no step 'When'.
     */
    private static final ThreadLocal<Response> responseThreadLocal = new ThreadLocal<>();

    /**
     * Retorna a instância atual do {@link RequestSpecification} para a thread.
     *
     * @return RequestSpecification da thread atual.
     */
    protected RequestSpecification getRequestSpec() {
        return requestSpecThreadLocal.get();
    }

    /**
     * Retorna a instância da última {@link Response} para a thread.
     *
     * @return Response da thread atual.
     */
    protected Response getResponse() {
        return responseThreadLocal.get();
    }

    /**
     * Define a instância da última {@link Response} para a thread atual.
     * Isso é usado no step 'When' para que o step 'Then' possa acessá-la.
     *
     * @param response A resposta da requisição da API.
     */
    protected void setResponse(Response response) {
        responseThreadLocal.set(response);
    }

    /**
     * Configura a RequestSpecification base para todas as chamadas de API.
     * É executado antes de cada cenário de API.
     */
    protected void apiSetUp(String baseUrl) {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(baseUrl); // Pega a URL base do seu gerenciador de configs
        builder.setContentType(ContentType.JSON);

        // Adiciona logging para ver o que está sendo enviado e recebido. Ótimo para debug.
        builder.addFilter(new RequestLoggingFilter());
        builder.addFilter(new ResponseLoggingFilter());

        requestSpecThreadLocal.set(builder.build());
    }

    /**
     * Limpa as configurações das threads após a execução de um cenário.
     * Libera os recursos para evitar memory leaks e interferência entre testes.
     */
    protected void apiTearDown() {
        requestSpecThreadLocal.remove();
        responseThreadLocal.remove();
    }

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
        boolean isHeadless = parseBoolean(ConfigManager.getPropertiesValue("HEADLESS_MODE"));

        return switch (browser.toLowerCase()) {
            case "chrome" -> {
                ChromeOptions options = new ChromeOptions();
                options.addArguments(
                        isHeadless ? "--headless=new" : "--start-maximized",
                        "--no-sandbox",
                        "--disable-dev-shm-usage",
                        "--disable-gpu",
                        "--window-size=1920,1080",
                        "--remote-allow-origins=*"
                );
                // Configurações específicas do Chrome
                options.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
                WebDriverManager.chromedriver().setup();
                yield new ChromeDriver(options);
            }
            case "edge" -> {
                EdgeOptions options = new EdgeOptions();
                options.addArguments(
                        isHeadless ? "--headless" : "--start-maximized",
                        "--no-sandbox",
                        "--disable-dev-shm-usage",
                        "--disable-gpu",
                        "--window-size=1920,1080"
                );
                WebDriverManager.edgedriver().setup();
                yield new EdgeDriver(options);
            }
            case "firefox" -> {
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments(
                        isHeadless ? "--headless" : "--start-maximized",
                        "--width=1920",
                        "--height=1080"
                );
                // Configurações específicas do Firefox
                options.setLogLevel(FirefoxDriverLogLevel.ERROR);
                WebDriverManager.firefoxdriver().setup();
                yield new FirefoxDriver(options);
            }
            default -> throw new IllegalArgumentException("Navegador não suportado: " + browser);
        };
    }

    /**
     * Configura o driver, maximizando a janela e acessando a URL base.
     *
     * @param driver Instância do WebDriver a ser configurada
     * @param url    URL base a ser acessada
     */
    private void configureWebDriver(WebDriver driver, String url) {
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
        return (WebDriverWait) new WebDriverWait(driver, getGlobalTimeout(), getPollingInterval())
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class);
    }

    /**
     * Inicializa o navegador, configura o driver e o WebDriverWait para a thread atual.
     *
     * @param browser Nome do navegador a ser utilizado
     * @param url     URL base para navegação inicial
     */
    protected void browserSetUp(String browser, String url) {
        WebDriver driver = createDriver(browser);
        configureWebDriver(driver, url);
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