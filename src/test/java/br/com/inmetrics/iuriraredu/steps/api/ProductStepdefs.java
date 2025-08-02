package br.com.inmetrics.iuriraredu.steps.api;

import br.com.inmetrics.iuriraredu.api.ApiService;
import br.com.inmetrics.iuriraredu.settings.BaseTest;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.path.json.JsonPath;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ProductStepdefs extends BaseTest {

    private final ApiService apiService = new ApiService();
    private JsonPath jsonPath;
    private String bodyResponse;
    private Map<String, Object> category;


    @Dado("que busco pelo nome {string} sem o parâmetro {string} configurado")
    public void queBuscoPeloNomeSemOParametroConfigurado(String productName, String arg1) {
        setResponse(apiService.searchProducts(productName, null));
    }

    @Dado("que busco pelo nome {string} com parâmetro {string} configurado para {int}")
    public void queBuscoPeloNomeComParametroConfiguradoPara(String productName, String arg1, int quantity) {
        setResponse(apiService.searchProducts(productName, quantity));
    }

    @Dado("que busco pelo nome {string}")
    public void queBuscoPeloNome(String productName) {
        setResponse(apiService.searchProducts(productName, "notFound"));
    }

    @Quando("envio uma requisição GET")
    public void envioUmaRequisicaoGET() {
        if (getResponse().contentType().equals("application/json")) {
            jsonPath = getResponse().jsonPath();
            List<Map<String, Object>> categories = jsonPath.get();
            category = categories.getFirst();
        }
    }

    @Quando("envio uma requisição GET para uma URL incorreta")
    public void envioUmaRequisicaoGETParaUmaURLIncorreta() {
        bodyResponse = getResponse().getBody().asString();
    }

    @Entao("devo receber status code {int}")
    public void devoReceberStatusCode(int expectedStatusCode) {
        assertEquals(expectedStatusCode, getResponse().getStatusCode());
    }

    @Entao("apenas {int} categoria deve ser retornada")
    public void apenasCategoriaDeveSerRetornada(int expectedCount) {
        assertEquals(expectedCount,  jsonPath.getList("$").size());
    }

    @Entao("apenas {int} produto deve ser retornado")
    public void apenasProdutoDeveSerRetornada(int expectedCount) {
        List<Map<String, Object>> products = (List<Map<String, Object>>) category.get("products");
        assertEquals(expectedCount, products.size());
    }

    @Entao("todos os produtos devem ser retornados")
    public void todosOsProdutosDevemSerRetornados() {
        List<Map<String, Object>> products = (List<Map<String, Object>>) category.get("products");
        assertFalse(products.isEmpty());
    }

    @Entao("a estrutura da resposta deve ser válida")
    public void validarEstruturaDaResposta() {
        // Valida estrutura da categoria
        validaCategoria(category);

        // Valida estrutura do produto
        for (Map<String, Object> product : (List<Map<String, Object>>) category.get("products")) {
            validarProduto(product, category);
        }

    }

    @Entao("resposta HTML de erro")
    public void respostaHTMLDeErro() {
        assertTrue("Content-Type não é text/html", getResponse().contentType().contains("text/html"));
        assertTrue("Título HTML de erro não encontrado", bodyResponse.contains("<title>HTTP Status 404"));
        assertTrue("Mensagem 'The requested resource' ausente", bodyResponse.contains("The requested resource"));
        assertTrue("Mensagem 'is not available' ausente", bodyResponse.contains("is not available"));
    }

    private void validaCategoria(Map<String, Object> category) {
        assertTrue(category.containsKey("categoryId"));
        assertTrue(category.containsKey("categoryName"));
        assertTrue(category.containsKey("categoryImageId"));
        assertTrue(category.containsKey("products"));

        assertTrue(category.get("categoryId") instanceof Number);
        assertTrue(category.get("categoryName") instanceof String);
        assertFalse(((String) category.get("categoryName")).isEmpty());
        assertTrue(category.get("categoryImageId") instanceof String);
        assertFalse(((String) category.get("categoryImageId")).isEmpty());
    }

    private void validarProduto(Map<String, Object> product, Map<String, Object> category) {
        assertTrue(product.containsKey("productId"));
        assertTrue(product.containsKey("categoryId"));
        assertTrue(product.containsKey("productName"));
        assertTrue(product.containsKey("price"));
        assertTrue(product.containsKey("imageUrl"));

        assertTrue(product.get("productId") instanceof Number);
        assertTrue(product.get("categoryId") instanceof Number);
        assertEquals(category.get("categoryId"), product.get("categoryId")); // Valida consistência

        assertTrue(product.get("productName") instanceof String);
        assertFalse(((String) product.get("productName")).isEmpty());

        assertTrue(product.get("price") instanceof Number);
        assertTrue(((Number) product.get("price")).doubleValue() > 0);

        assertTrue(product.get("imageUrl") instanceof String);
        assertFalse(((String) product.get("imageUrl")).isEmpty());
    }
}
