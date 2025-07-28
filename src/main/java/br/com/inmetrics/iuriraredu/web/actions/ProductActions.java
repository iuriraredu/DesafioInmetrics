package br.com.inmetrics.iuriraredu.web.actions;

import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.jsClick;
import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.implicitlyWait;

import br.com.inmetrics.iuriraredu.settings.BaseTest;
import br.com.inmetrics.iuriraredu.web.pages.ProductPage;

public class ProductActions extends BaseTest {

    private final ProductPage productPage;

    public ProductActions() {
        this.productPage = new ProductPage(getDriver());
    }

    /**
     * Clica no botão de cor vermelha na página do produto.
     */
    public void clickColorButton(String color) {
        implicitlyWait(getDriver());
//        productPage.getColorBtn(color, getDriver()).click();
        jsClick(
                getDriver(), productPage.getColorBtn(color, getDriver())
        );
    }

    /**
     * Clica no botão de adicionar ao carrinho na página do produto.
     */
    public void clickAddToCartButton() {
        implicitlyWait(getDriver());
        productPage.getAddToCartBtn().click();
    }

    public void clickCartButton() {
        implicitlyWait(getDriver());
        productPage.getCartBtn().click();
    }
}
