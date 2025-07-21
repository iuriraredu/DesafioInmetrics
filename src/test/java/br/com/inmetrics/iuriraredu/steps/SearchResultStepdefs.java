package br.com.inmetrics.iuriraredu.steps;

import br.com.inmetrics.iuriraredu.web.actions.SearchResultActions;
import io.cucumber.java.pt.Entao;
import org.junit.jupiter.api.Assertions;

public class SearchResultStepdefs {

    private final SearchResultActions searchResultActions;

    public SearchResultStepdefs() {
        this.searchResultActions = new SearchResultActions();
    }

    @Entao("^sou apresentado aos \"([^\"]*)\"$")
    public void souApresentadoAoProduto(String product) {
        Assertions.assertEquals(product, this.searchResultActions.getProductName(product));
    }
}
