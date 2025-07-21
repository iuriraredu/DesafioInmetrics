package br.com.inmetrics.iuriraredu.web.actions;

import br.com.inmetrics.iuriraredu.settings.BaseTest;
import br.com.inmetrics.iuriraredu.utils.WaitUtils;
import br.com.inmetrics.iuriraredu.web.pages.ProductPage;
import org.openqa.selenium.WebElement;

public class ProductActions extends BaseTest {

    private final ProductPage productPage;

    public ProductActions() {
        this.productPage = new ProductPage(getDriver());
    }

    /**
     * Clica no botão de cor vermelha na página do produto.
     */
    public void clickColorButton(String color) {
        waitAnySeconds(60);
        WebElement redColorBtn = WaitUtils.waitForClickable(
                getDriver(), productPage.getColorBtn(color, getDriver()));
        redColorBtn.click();
    }

    /**
     * Clica no botão de adicionar ao carrinho na página do produto.
     */
    public void clickAddToCartButton() {
        waitAnySeconds(60);
        WebElement addToCartBtn = WaitUtils.waitForClickable(
                getDriver(), productPage.getAddToCartBtn());
        addToCartBtn.click();
    }

    /**
     * Obtém o nome do produto exibido na página do produto.
     *
     * @return o nome do produto
     */
    public String getProductName() {
        WebElement productNameElement = WaitUtils.waitForClickable(
                getDriver(), productPage.getProductName());
        return productNameElement.getText();
    }
}
