package br.com.inmetrics.iuriraredu.steps.web;

import br.com.inmetrics.iuriraredu.web.actions.SearchResultActions;
import io.cucumber.java.pt.Entao;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchResultStepdefs {

    private final SearchResultActions searchResultActions;

    public SearchResultStepdefs() {
        this.searchResultActions = new SearchResultActions();
    }

    @Entao("^sou apresentado aos \"([^\"]*)\"$")
    public void souApresentadoAoProduto(String product) {
        assertEquals(product, this.searchResultActions.getProductName(product));
    }
}
