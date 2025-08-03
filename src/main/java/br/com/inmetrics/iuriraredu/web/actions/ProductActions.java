package br.com.inmetrics.iuriraredu.web.actions;

import br.com.inmetrics.iuriraredu.settings.BaseTest;
import br.com.inmetrics.iuriraredu.web.pages.ProductPage;
import org.openqa.selenium.WebElement;

import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.jsClick;
import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.waitForClickable;

/** * Classe responsável por ações relacionadas à página do produto.
 * Contém métodos para interagir com os elementos da página do produto,
 * como clicar em botões de cor, adicionar ao carrinho e acessar o carrinho.
 */
public class ProductActions extends BaseTest {

    private final ProductPage productPage;

    public ProductActions() {
        this.productPage = new ProductPage(getDriver());
    }

    /**
     * Clica no botão de cor do produto na página do produto.
     *
     * @param color Cor do botão a ser clicado.
     */
    public void clickColorButton(String color) {
        WebElement buttonColor = waitForClickable(getWait(), productPage.getColorBtn(color, getDriver()));
//        productPage.getColorBtn(color, getDriver()).click();
        jsClick(getDriver(),buttonColor);
    }

    /**
     * Obtém o nome do produto na página do produto.
     *
     * @return Nome do produto.
     */
    public void clickAddToCartButton() {
        WebElement addToCartBtn = waitForClickable(getWait(), productPage.getAddToCartBtn());
        addToCartBtn.click();
    }

    /**
     * Clica no botão do carrinho na página do produto.
     */
    public void clickCartButton() {
        WebElement cartBtn = waitForClickable(getWait(), productPage.getCartBtn());
        cartBtn.click();
    }
}
