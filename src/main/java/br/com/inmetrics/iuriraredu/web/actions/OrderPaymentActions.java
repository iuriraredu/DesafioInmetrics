package br.com.inmetrics.iuriraredu.web.actions;

import br.com.inmetrics.iuriraredu.settings.BaseTest;
import br.com.inmetrics.iuriraredu.web.pages.OrderPaymentPage;
import org.openqa.selenium.WebElement;

import static br.com.inmetrics.iuriraredu.utils.SeleniumUtils.waitForClickable;

/**
 * Classe responsável por realizar ações na página de pagamentos do site.
 *
 * <p>Utiliza métodos utilitários do Selenium para interagir com elementos da OrderPaymentPage,
 * como busca de produtos, navegação e fechamento de campos de pesquisa.</p>
 *
 * <p>Herda configurações de teste da classe {@link BaseTest}.</p>
 */
public class OrderPaymentActions extends BaseTest {
    private final OrderPaymentPage orderPaymentPage;

    /**
     * Construtor que inicializa a OrderPaymentPage com o driver atual.
     */
    public OrderPaymentActions() {
        this.orderPaymentPage = new OrderPaymentPage(getDriver());
    }

    /**
     * Recupera o nome do produto adicionado ao carrinho.
     *
     * @param productName Nome do produto a ser verificado.
     * @return Nome do produto encontrado na página de pagamento.
     */
    public String getProductName(String productName) {
        WebElement productElement = waitForClickable(
                getWait(), orderPaymentPage.getProductByName(productName, getDriver()));
        return productElement.getText();
    }
}
