package br.com.inmetrics.iuriraredu.web.actions;

import br.com.inmetrics.iuriraredu.settings.BaseTest;
import br.com.inmetrics.iuriraredu.web.pages.HomePage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import static br.com.inmetrics.iuriraredu.utils.ConfigManager.getGlobalTimeout;
import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.tryToClick;
import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.tryToSendKeys;
import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.waitForClickable;
import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.waitForPageLoad;


/**
 * Classe responsável por realizar ações na página inicial do site.
 *
 * <p>Utiliza métodos utilitários do Selenium para interagir com elementos da HomePage,
 * como busca de produtos, navegação e fechamento de campos de pesquisa.</p>
 *
 * <p>Herda configurações de teste da classe {@link BaseTest}.</p>
 */
public class HomeActions extends BaseTest {
    /** Instância da página inicial para acesso aos elementos. */
    private final HomePage homePage;

    /**
     * Construtor que inicializa a HomePage com o driver atual.
     */
    public HomeActions() {
        this.homePage = new HomePage(getDriver());
    }

    /**
     * Clica no botão de busca. Caso não seja possível, tenta clicar no campo de busca mobile.
     */
    public void clickOnSearchBtn() {
        waitForPageLoad(getWait(), getDriver(), getGlobalTimeout());
        tryToClick(getDriver(), getWait(), this.homePage.getSearchBtn());
        tryToClick(getDriver(), getWait(), this.homePage.getMobileSearchInput());
    }

    /**
     * Envia texto para o campo de busca e pressiona ENTER.
     * Caso não seja possível, tenta enviar para o campo de busca mobile.
     *
     * @param text Texto a ser pesquisado.
     */
    public void sendTextOnSearchInput(String text) {
        waitForPageLoad(getWait(), getDriver(), getGlobalTimeout());
        tryToSendKeys(getDriver(), getWait(), this.homePage.getSearchInput(), text + Keys.ENTER);
        tryToSendKeys(getDriver(), getWait(), this.homePage.getMobileSearchInput(), text + Keys.ENTER);
    }

    /**
     * Recupera o texto do link de laptops na página inicial.
     *
     * @return Texto do link de laptops.
     */
    public String getTextLaptop() {
        WebElement laptopLink = waitForClickable(getWait(), this.homePage.getLaptopLink());
        return laptopLink.getText();
    }

    /**
     * Fecha o campo de busca clicando no botão de fechar.
     */
    public void closeSearch() {
        tryToClick(getDriver(), getWait(), this.homePage.getCloseSearchBtn());
    }

    /**
     * Clica em um produto pelo nome informado.
     *
     * @param productName Nome do produto a ser clicado.
     */
    public void clickOnProductByName(String productName) {
        WebElement product = waitForClickable(getWait(),  this.homePage.getProductByName(productName, getDriver()));
        product.click();
    }

    /**
     * Clica no link para retornar à página inicial.
     */
    public void clickOnHomeLink() {
        tryToClick(getDriver(), getWait(), this.homePage.getHomeLink());
    }
}