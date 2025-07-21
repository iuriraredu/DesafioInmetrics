package br.com.inmetrics.iuriraredu.web.actions;

import br.com.inmetrics.iuriraredu.settings.BaseTest;
import br.com.inmetrics.iuriraredu.utils.WaitUtils;
import br.com.inmetrics.iuriraredu.web.pages.SearchResultPage;
import org.openqa.selenium.WebElement;

public class SearchResultActions extends BaseTest {

    private final SearchResultPage searchResultPage;

    public SearchResultActions() {
        this.searchResultPage = new SearchResultPage(getDriver());
    }

    /**
     * Clica no no produto.
     */
    public void clickProduct(String productName) {
        WebElement product = WaitUtils.waitForClickable(getDriver(), searchResultPage.getProductByName(productName, getDriver()));
        product.click();
    }

    public String getProductName(String productName) {
        WaitUtils.implicitlyWait(getDriver());
        WebElement product = WaitUtils.waitForClickable(getDriver(), searchResultPage.getProductByName(productName, getDriver()));
        return product.getText();
    }
}
