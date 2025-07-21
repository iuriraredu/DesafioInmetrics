package br.com.inmetrics.iuriraredu.steps;

import br.com.inmetrics.iuriraredu.web.actions.ProductActions;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.jupiter.api.Assertions;

public class ProductStepdefs {
    
    private final ProductActions productActions;
    
    public ProductStepdefs() {
        productActions = new ProductActions();
    }

    @E("^seleciono a opção de cor \"([^\"]*)\"$")
    public void selecionoAOpcaoDeCor(String color) {
        productActions.clickColorButton(color);
    }

    @Quando("^clino no botão \"([^\"]*)\"$")
    public void clinoNoBotao(String arg0) {
        productActions.clickAddToCartButton();
    }

    @Então("^aparece um popup de confirmação com o produto \"([^\"]*)\" adicionado ao carrinho$")
    public void apareceUmPopupDeConfirmacaoComOProdutoAdicionadoAoCarrinho(String productName) {
        Assertions.assertEquals(productName, productActions.getProductName());
    }
}

