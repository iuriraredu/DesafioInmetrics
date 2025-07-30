package br.com.inmetrics.iuriraredu.settings;

import static br.com.inmetrics.iuriraredu.utils.ConfigManager.getGlobalTimeout;
import static br.com.inmetrics.iuriraredu.utils.ConfigManager.getPollingInterval;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;

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
    private static final ThreadLocal<RequestSpecification> requestSpecThreadLocal = new ThreadLocal<>();
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