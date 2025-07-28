package br.com.inmetrics.iuriraredu.steps;

import br.com.inmetrics.iuriraredu.web.actions.ShoppingCartActions;
import io.cucumber.java.pt.Entao;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingCartStepdefs {

    private final ShoppingCartActions shoppingCartActions;

    public ShoppingCartStepdefs() {
        shoppingCartActions = new ShoppingCartActions();
    }

    @Entao("confirmo que o produto {string} foi adicionado ao carrinho")
    public void confirmoQueOProdutoFoiAdicionadoAoCarrinho(String productName) {
        String productNameByApplication = shoppingCartActions.getProductName();
        assertEquals(productName.toUpperCase(), productNameByApplication);
    }
}
