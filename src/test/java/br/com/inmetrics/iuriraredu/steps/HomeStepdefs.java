package br.com.inmetrics.iuriraredu.steps;

import br.com.inmetrics.iuriraredu.web.actions.HomeActions;
import br.com.inmetrics.iuriraredu.web.actions.SearchResultActions;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Quando;

/**
 * Step definitions para ações na página inicial.
 */
public class HomeStepdefs {

    private final HomeActions homeActions;
    private final SearchResultActions searchResultActions;

    /**
     * Construtor com injeção de dependências.
     */
    public HomeStepdefs() {
        this.homeActions = new HomeActions();
        this.searchResultActions = new SearchResultActions();
    }

    @Dado("que estou na página inicial com o campo de busca ativo")
    public void acessarPaginaInicialComBuscaAtiva() {
        homeActions.clickOnSearchBtn();
    }

    @Quando("digito o nome do produto {string}")
    public void digitarNomeProduto(String produto) {
        homeActions.sendTextOnSearchInput(produto);
    }

    @Dado("que navego até a página do produto {string}")
    public void navegarParaPaginaProduto(String produto) {
        homeActions.clickOnSearchBtn();
        homeActions.sendTextOnSearchInput(produto);
        searchResultActions.clickProduct(produto);
    }

    @E("pressiono a tecla {string}")
    public void pressionarTecla(String tecla) {
        homeActions.closeSearch();
    }
}