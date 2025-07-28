package br.com.inmetrics.iuriraredu.steps;

import br.com.inmetrics.iuriraredu.web.actions.HomeActions;
import br.com.inmetrics.iuriraredu.web.actions.ProductActions;
import br.com.inmetrics.iuriraredu.web.actions.SearchResultActions;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Quando;

import java.util.List;

/**
 * Step definitions para ações na página inicial.
 */
public class HomeStepdefs {

    private final HomeActions homeActions;
    private final SearchResultActions searchResultActions;
    private final ProductActions productActions;

    /**
     * Construtor para injeção de dependências.
     */
    public HomeStepdefs() {
        this.homeActions = new HomeActions();
        this.searchResultActions = new SearchResultActions();
        this.productActions = new ProductActions();
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
        buscarESelecionarProduto(produto);
    }

    @E("pressiono a tecla {string}")
    public void pressionarTecla(String tecla) {
        homeActions.closeSearch();
    }

    @Dado("que adicionei os seguintes produtos ao carrinho:")
    public void queAdicioneiOsSeguintesProdutosAoCarrinho(DataTable dataTableProducts) {
        List<String> products = dataTableProducts.asList(String.class);
        for (String product : products) {
            buscarESelecionarProduto(product);
            productActions.clickAddToCartButton();
        }
        productActions.clickCartButton();
    }

    /**
     * Busca e seleciona um produto pelo nome.
     *
     * @param produto Nome do produto a ser buscado e selecionado.
     */
    private void buscarESelecionarProduto(String produto) {
        homeActions.clickOnSearchBtn();
        homeActions.sendTextOnSearchInput(produto);
        searchResultActions.clickProduct(produto);
    }
}