package br.com.inmetrics.iuriraredu.web.actions;

import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.implicitlyWait;
import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.jsClick;
import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.scrollToBottom;

import br.com.inmetrics.iuriraredu.settings.BaseTest;
import br.com.inmetrics.iuriraredu.web.pages.ShoppingCartPage;

public class ShoppingCartActions extends BaseTest {

    private final ShoppingCartPage shoppingCartPage;

    public ShoppingCartActions() {
        this.shoppingCartPage = new ShoppingCartPage(getDriver());
    }

    public String getProductName() {
        implicitlyWait(getDriver());
        return shoppingCartPage.getProductName().getText();
    }

    public void clickCheckoutButton() {
        implicitlyWait(getDriver());
        scrollToBottom(getDriver());
        jsClick(getDriver(), shoppingCartPage.getCheckoutButton());
        shoppingCartPage.getCheckoutButton().click();
    }
}
