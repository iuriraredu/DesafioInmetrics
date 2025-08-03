package br.com.inmetrics.iuriraredu.steps.web;

import br.com.inmetrics.iuriraredu.web.actions.OrderPaymentActions;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Entao;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderPaymentStepdefs {

    OrderPaymentActions orderPaymentActions;

    public OrderPaymentStepdefs () {
        this.orderPaymentActions = new OrderPaymentActions();
    }

    @Entao("devo visualizar os mesmos produtos listados:")
    public void devoVisualizarOsMesmosProdutosListados(DataTable dataTableProducts) {
        List<String> products = dataTableProducts.asList(String.class);
        for (String product : products) {
            String productName = product.toUpperCase();
            assertEquals(
                    productName,
                    orderPaymentActions.getProductName(productName)
            );
        }
    }
}
