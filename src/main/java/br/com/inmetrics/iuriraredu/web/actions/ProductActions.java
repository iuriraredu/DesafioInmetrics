package br.com.inmetrics.iuriraredu.web.actions;

import br.com.inmetrics.iuriraredu.settings.BaseTest;
import br.com.inmetrics.iuriraredu.utils.WaitUtils;
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
        WaitUtils.implicitlyWait(getDriver());
        productPage.getColorBtn(color, getDriver()).click();
    }

    /**
     * Clica no botão de adicionar ao carrinho na página do produto.
     */
    public void clickAddToCartButton() {
        WaitUtils.implicitlyWait(getDriver());
        productPage.getAddToCartBtn().click();
    }

    /**
     * Obtém o nome do produto exibido na página do produto.
     *
     * @return o nome do produto
     */
    public String getProductName() {
        WaitUtils.implicitlyWait(getDriver());
        return productPage.getProductName().getText();
    }
}
