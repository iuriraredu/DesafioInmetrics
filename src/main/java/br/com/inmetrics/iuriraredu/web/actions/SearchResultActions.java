package br.com.inmetrics.iuriraredu.web.actions;

import br.com.inmetrics.iuriraredu.settings.BaseTest;
import br.com.inmetrics.iuriraredu.web.pages.SearchResultPage;

import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.implicitlyWait;

public class SearchResultActions extends BaseTest {

    private final SearchResultPage searchResultPage;

    public SearchResultActions() {
        this.searchResultPage = new SearchResultPage(getDriver());
    }

    /**
     * Clica no no produto.
     */
    public void clickProduct(String productName) {
        implicitlyWait(getDriver());
        searchResultPage.getProductByName(productName, getDriver()).click();
    }

    public String getProductName(String productName) {
        implicitlyWait(getDriver());
        return searchResultPage.getProductByName(productName, getDriver()).getText();
    }
}
