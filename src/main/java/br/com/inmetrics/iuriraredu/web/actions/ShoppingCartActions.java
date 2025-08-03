package br.com.inmetrics.iuriraredu.web.actions;

import br.com.inmetrics.iuriraredu.settings.BaseTest;
import br.com.inmetrics.iuriraredu.web.pages.ShoppingCartPage;
import org.openqa.selenium.WebElement;

import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.jsClick;
import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.scrollToBottom;
import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.waitForClickable;

/**
 * Classe responsável por ações relacionadas ao carrinho de compras.
 * Esta classe estende BaseTest e utiliza métodos utilitários para interações com a página.
 */
public class ShoppingCartActions extends BaseTest {

    private final ShoppingCartPage shoppingCartPage;

    public ShoppingCartActions() {
        this.shoppingCartPage = new ShoppingCartPage(getDriver());
    }

    /**
     * Recupera o nome do produto atualmente no carrinho de compras.
     * Este método aguarda o elemento do nome do produto estar clicável e retorna seu texto.
     *
     * @return O nome do produto no carrinho de compras.
     */
    public String getProductName() {
        WebElement productName = waitForClickable(getWait(), shoppingCartPage.getProductName());
        return productName.getText();
    }

    /**
     * Clica no botão de checkout na página do carrinho de compras.
     * Este método aguarda o botão estar clicável, rola até o final da página
     * e então realiza um clique via JavaScript no botão.
     */
    public void clickCheckoutButton() {
        WebElement checkoutButton = waitForClickable(getWait(), shoppingCartPage.getCheckoutButton());
        scrollToBottom(getDriver());
        jsClick(getDriver(), checkoutButton);
        checkoutButton.click();
    }
}
