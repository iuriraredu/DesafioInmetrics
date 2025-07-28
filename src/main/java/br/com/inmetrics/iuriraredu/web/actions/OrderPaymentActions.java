package br.com.inmetrics.iuriraredu.web.actions;

import br.com.inmetrics.iuriraredu.settings.BaseTest;
import br.com.inmetrics.iuriraredu.web.pages.OrderPaymentPage;

public class OrderPaymentActions extends BaseTest {
    private final OrderPaymentPage orderPaymentPage;

    public OrderPaymentActions() {
        this.orderPaymentPage = new OrderPaymentPage(getDriver());
    }

    public String getProductName(String productName) {
        return orderPaymentPage.getProductByName(productName, getDriver()).getText();
    }
}
