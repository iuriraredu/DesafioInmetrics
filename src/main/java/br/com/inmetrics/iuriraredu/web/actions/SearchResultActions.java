package br.com.inmetrics.iuriraredu.web.actions;

import br.com.inmetrics.iuriraredu.settings.BaseTest;
import br.com.inmetrics.iuriraredu.web.pages.SearchResultPage;
import org.openqa.selenium.WebElement;

import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.waitForClickable;
/** * Classe responsável por ações relacionadas à página de resultados de busca.
 * Contém métodos para interagir com os produtos encontrados na busca.
 */
public class SearchResultActions extends BaseTest {

    private final SearchResultPage searchResultPage;

    public SearchResultActions() {
        this.searchResultPage = new SearchResultPage(getDriver());
    }

    /**
     * Clica no produto especificado pelo nome.
     *
     * @param productName o nome do produto a ser clicado
     */
    public void clickProduct(String productName) {
        WebElement productByName = waitForClickable(
                getWait(), searchResultPage.getProductByName(productName, getDriver()));
        productByName.click();
    }

    /**
     * Obtém o nome do produto.
     *
     * @param productName o nome do produto a ser buscado
     * @return o nome do produto encontrado
     */
    public String getProductName(String productName) {
        WebElement productByName = waitForClickable(
                getWait(), searchResultPage.getProductByName(productName, getDriver()));
        return productByName.getText();
    }
}
