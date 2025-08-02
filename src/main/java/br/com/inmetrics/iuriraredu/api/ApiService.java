package br.com.inmetrics.iuriraredu.api;

import br.com.inmetrics.iuriraredu.settings.BaseTest;
import io.restassured.response.Response;

import java.io.File;

import static br.com.inmetrics.iuriraredu.utils.ConfigManager.getPropertiesValue;
import static io.restassured.RestAssured.given;

public class ApiService extends BaseTest {


    // Metodo genérico para GET
    public Response get(String endpoint) {
        return given()
                .spec(getRequestSpec())
                .when()
                .get(endpoint);
    }

    // Metodo para busca de produtos
    public Response searchProducts(String productName, int quantity) {
        return given()
                .spec(getRequestSpec())
                .queryParam("name", productName)
                .queryParam("quantityPerEachCategory", quantity)
                .when()
                .get(getPropertiesValue("SEARCH_PRODUCTS_ENDPOINT"));
    }

    public Response searchProducts(String productName, String notFound) {
        String url;
        if (notFound != null) {
            url = getPropertiesValue("SEARCH_PRODUCTS_NOT_FOUND_ENDPOINT") + "/" + notFound;
        } else {
            url = getPropertiesValue("SEARCH_PRODUCTS_ENDPOINT");
        }
        return given()
                .spec(getRequestSpec())
                .queryParam("name", productName)
                .when()
                .get(url);
    }

    // Metodo para upload de imagem (exemplo)
    public Response uploadProductImage(int productId, String filePath) {
        return given()
                .spec(getRequestSpec())
                .multiPart("file", new File(filePath))
                .when()
                .post("/catalog/api/v1/product/image/{productId}", productId);
    }
}

