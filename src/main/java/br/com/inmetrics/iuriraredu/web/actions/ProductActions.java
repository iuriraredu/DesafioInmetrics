package br.com.inmetrics.iuriraredu.web.actions;

import br.com.inmetrics.iuriraredu.settings.BaseTest;
import br.com.inmetrics.iuriraredu.web.pages.ProductPage;

import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.tryToClick;

/**
 * Classe responsável por ações relacionadas à página do produto.
 *
 * <p>Utiliza métodos utilitários do Selenium para interagir com elementos da ProductPage,
 * como busca de produtos, navegação e fechamento de campos de pesquisa.</p>
 *
 * <p>Herda configurações de teste da classe {@link BaseTest}.</p>
 */
public class ProductActions extends BaseTest {

    private final ProductPage productPage;

    /**
     * Construtor da classe ProductActions.
     * Inicializa a página do produto com o driver.
     */
    public ProductActions() {
        this.productPage = new ProductPage(getDriver());
    }

    /**
     * Obtém o nome do produto exibido na página.
     *
     * @return o nome do produto como uma String.
     */
    public void clickColorButton(String color) {
        if (!tryToClick(getDriver(), getWait(), this.productPage.getColorBunnyBtn(color, getDriver())))
            tryToClick(getDriver(), getWait(), this.productPage.getColorRabbitBtn(color, getDriver()));
    }

    /**
     * Clica no botão "Adicionar ao Carrinho" na página do produto.
     * Utiliza um método utilitário para tentar clicar no botão.
     */
    public void clickAddToCartButton() {
        tryToClick(getDriver(), getWait(), this.productPage.getAddToCartBtn());
    }

    /**
     * Clica no botão "Carrinho" na página do produto.
     * Utiliza um método utilitário para esperar que o botão esteja clicável antes de clicar.
     */
    public void clickCartButton() {
        tryToClick(getDriver(), getWait(), this.productPage.getCartBtn());

    }
}
