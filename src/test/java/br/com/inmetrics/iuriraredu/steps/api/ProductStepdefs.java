package br.com.inmetrics.iuriraredu.steps.api;

import br.com.inmetrics.iuriraredu.api.ApiService;
import br.com.inmetrics.iuriraredu.models.BearerToken;
import br.com.inmetrics.iuriraredu.models.User;
import com.github.javafaker.Faker;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ProductStepdefs extends ApiService {

    private final Faker faker = new Faker(new Locale("pt-BR"));
    private JsonPath jsonPath;
    private String bodyResponse;
    private BearerToken bearerToken;
    private Map<String, Object> category;
    private String fileName;


    @Dado("que busco pelo nome {string} sem o parâmetro {string} configurado")
    public void queBuscoPeloNomeSemOParametroConfigurado(String productName, String arg1) {
        setResponse(super.searchProducts(productName, null, null));
    }

    @Dado("que busco pelo nome {string} com parâmetro {string} configurado para {int}")
    public void queBuscoPeloNomeComParametroConfiguradoPara(String productName, String arg1, int quantity) {
        setResponse(super.searchProducts(productName, quantity, null));
    }

    @Dado("que busco pelo nome {string}")
    public void queBuscoPeloNome(String productName) {
        setResponse(super.searchProducts(productName, null, "notFound"));
    }

    @Dado("que eu tenho um token de administrador válido")
    public void queEuTenhoUmTokenDeAdministradorValido() {
        User user = createNewUser();
        registerUser(user);
        bearerToken = getBearerToken(user);
    }

    private BearerToken getBearerToken(User user) {
        return super.postLogin(user);
    }

    @Dado("um arquivo de imagem {string} para upload")
    public void umArquivoDeImagemParaUpload(String fileName) {
        this.fileName = fileName;
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

    @Quando("eu envio a imagem para o produto com ID {int}")
    public void euEnvioAImagemParaOProdutoComID(int productId) {
        setResponse(super.postUploadImage(bearerToken, fileName, "red", productId));
    }

    @Entao("devo receber status code {int}")
    public void devoReceberStatusCode(int expectedStatusCode) {
        assertEquals(expectedStatusCode, getResponse().getStatusCode());
    }

    @Entao("apenas {int} categoria deve ser retornada")
    public void apenasCategoriaDeveSerRetornada(int expectedCount) {
        assertEquals(expectedCount, jsonPath.getList("$").size());
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
        validarProduto();

    }

    @Entao("resposta HTML de erro")
    public void respostaHTMLDeErro() {
        assertTrue("Content-Type não é text/html", getResponse().contentType().contains("text/html"));
        assertTrue("Título HTML de erro não encontrado", bodyResponse.contains("<title>HTTP Status 404"));
        assertTrue("Mensagem 'The requested resource' ausente", bodyResponse.contains("The requested resource"));
        assertTrue("Mensagem 'is not available' ausente", bodyResponse.contains("is not available"));
    }

    private void validaCategoria(Map<String, Object> category) {
        assertNotNull("Categoria não pode ser nula", category);

        assertThat(category.keySet(), containsInAnyOrder("categoryId", "categoryName", "categoryImageId", "products"));

        assertTrue("categoryId deve ser numérico", category.get("categoryId") instanceof Number);
        assertFalse("categoryName vazio", ((String) category.get("categoryName")).isEmpty());
        assertFalse("categoryImageId vazio", ((String) category.get("categoryImageId")).isEmpty());
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getProductsFromCategory() {
        return (List<Map<String, Object>>) category.get("products");
    }


    private void validarProduto() {
        List<Map<String, Object>> products = getProductsFromCategory();
        assertFalse("Lista de produtos vazia", products.isEmpty());

        products.forEach(product -> {
            assertThat(product.keySet(), containsInAnyOrder("productId", "categoryId", "productName", "price", "imageUrl"));
            assertEquals("categoryId inconsistente", category.get("categoryId"), product.get("categoryId"));
            assertFalse("productName vazio", ((String) product.get("productName")).isEmpty());
            assertTrue("preço inválido", ((Number) product.get("price")).doubleValue() > 0);
            assertFalse("imageUrl vazio", ((String) product.get("imageUrl")).isEmpty());
        });
    }

    private User createNewUser() {
        return new User(
                faker.address().streetName(),
                faker.address().cityName(),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber().cellPhone(),
                faker.address().stateAbbr(),
                faker.address().zipCode()
        );
    }

    private void registerUser(User user) {
        Response response = super.postCreateUserAccount(user);
        user.setUserId(response.jsonPath().getString("response.userId"));
    }

    @Entao("o corpo da resposta deve conter os campos:")
    public void oCorpoDaRespostaDeveConterOsCampos(Map<String, String> camposEsperados) {
        jsonPath = getResponse().jsonPath();

        camposEsperados.forEach((campo, valorEsperado) -> {
            // Remove aspas se existirem
            String valorTratado = valorEsperado.replace("\"", "").trim();

            // Validação especial para cada tipo de campo
            switch (campo) {
                case "success":
                    assertThat("O campo 'success' deve ser true",
                            jsonPath.getBoolean(campo), is(Boolean.parseBoolean(valorTratado)));
                    break;

                case "id":
                    assertThat("O ID do produto deve ser 83",
                            jsonPath.getInt(campo), is(Integer.parseInt(valorTratado)));
                    break;

                case "imageId":
                    assertThat("O imageId deve começar com 'custom_image_'",
                            jsonPath.getString(campo), startsWith(valorTratado));
                    break;

                case "reason":
                case "imageColor":
                    assertThat("Valor incorreto para " + campo,
                            jsonPath.getString(campo), equalTo(valorTratado));
                    break;

                default:
                    fail("Campo não mapeado: " + campo);
            }
        });
    }
}
