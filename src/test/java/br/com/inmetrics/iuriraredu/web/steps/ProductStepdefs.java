package br.com.inmetrics.iuriraredu.web.steps;

import br.com.inmetrics.iuriraredu.web.actions.ProductActions;
import br.com.inmetrics.iuriraredu.web.actions.ShoppingCartActions;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Quando;

public class ProductStepdefs {
    
    private final ProductActions        productActions;
    private final ShoppingCartActions   shoppingCartActions;
    
    public ProductStepdefs() {
        productActions      = new ProductActions();
        shoppingCartActions = new ShoppingCartActions();
    }

    @E("^seleciono a opção de cor \"([^\"]*)\"$")
    public void selecionoAOpcaoDeCor(String color) {
        productActions.clickColorButton(color);
    }

    @Quando("^clino no botão \"([^\"]*)\"$")
    public void clinoNoBotao(String arg0) {
        productActions.clickAddToCartButton();
    }

    @E("clico no icone do carrinho de compras")
    public void clicoNoIconeDoCarrinhoDeCompras() {
        productActions.clickCartButton();
    }

    @Quando("acesso a tela de pagamento")
    public void acessoATelaDePagamento() {
        productActions.clickCartButton();
        shoppingCartActions.clickCheckoutButton();
    }
}


