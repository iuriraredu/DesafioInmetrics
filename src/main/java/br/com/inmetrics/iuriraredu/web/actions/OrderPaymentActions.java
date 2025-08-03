package br.com.inmetrics.iuriraredu.web.actions;

import br.com.inmetrics.iuriraredu.settings.BaseTest;
import br.com.inmetrics.iuriraredu.web.pages.OrderPaymentPage;
import org.openqa.selenium.WebElement;

import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.waitForClickable;

public class OrderPaymentActions extends BaseTest {
    private final OrderPaymentPage orderPaymentPage;

    public OrderPaymentActions() {
        this.orderPaymentPage = new OrderPaymentPage(getDriver());
    }

    public String getProductName(String productName) {
        WebElement productElement = waitForClickable(
                getWait(), orderPaymentPage.getProductByName(productName, getDriver()));
        return productElement.getText();
    }
}
