package br.com.inmetrics.iuriraredu.web.actions;

import br.com.inmetrics.iuriraredu.settings.BaseTest;
import br.com.inmetrics.iuriraredu.web.pages.SearchResultPage;
import org.openqa.selenium.WebElement;

import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.tryToClick;
import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.waitForClickable;

/** Classe responsável por ações relacionadas à página de resultados de busca.
 *
 * <p>Utiliza métodos utilitários do Selenium para interagir com elementos da SearchResultPage,
 * como busca de produtos, navegação e fechamento de campos de pesquisa.</p>
 *
 * <p>Herda configurações de teste da classe {@link BaseTest}.</p>
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
        tryToClick(getDriver(), getWait(), searchResultPage.getProductByName(productName, getDriver()));
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
