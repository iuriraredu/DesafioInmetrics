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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
        bearerToken = super.postLogin(user);
    }

    @Dado("um arquivo de imagem {string} para upload")
    public void umArquivoDeImagemParaUpload(String fileName) {
        this.fileName = fileName;
    }

    @Quando("envio uma requisição GET")
    public void envioUmaRequisicaoGET() {
        Response response = getResponse();
        if (response != null && response.contentType() != null &&
                response.contentType().contains("application/json")) {
            jsonPath = response.jsonPath();
            List<Map<String, Object>> categories = jsonPath.getList("");
            if (categories != null && !categories.isEmpty()) {
                category = categories.get(0);
            }
        }
    }

    @Quando("envio uma requisição GET para uma URL incorreta")
    public void envioUmaRequisicaoGETParaUmaURLIncorreta() {
        Response response = getResponse();
        if (response != null) {
            bodyResponse = response.getBody().asString();
        }
    }

    @Quando("eu envio a imagem para o produto com ID {int}")
    public void euEnvioAImagemParaOProdutoComID(int productId) {
        setResponse(super.postUploadImage(bearerToken, fileName, "red", productId));
    }

    @Entao("devo receber status code {int}")
    public void devoReceberStatusCode(int expectedStatusCode) {
        Response response = getResponse();
        assertNotNull(response, "Response não pode ser nula");
        assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @Entao("apenas {int} categoria deve ser retornada")
    public void apenasCategoriaDeveSerRetornada(int expectedCount) {
        assertNotNull(jsonPath, "JsonPath não pode ser nulo");
        List<Object> list = jsonPath.getList("$");
        assertNotNull(list, "Lista não pode ser nula");
        assertEquals(expectedCount, list.size());
    }

    @Entao("apenas {int} produto deve ser retornado")
    public void apenasProdutoDeveSerRetornada(int expectedCount) {
        assertNotNull(category, "Categoria não pode ser nula");
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> products = (List<Map<String, Object>>) category.get("products");
        assertNotNull(products, "Lista de produtos não pode ser nula");
        assertEquals(expectedCount, products.size());
    }
    @Entao("todos os produtos devem ser retornados")
    public void todosOsProdutosDevemSerRetornados() {
        List<Map<String, Object>> products = (List<Map<String, Object>>) category.get("products");
        assertFalse(products.isEmpty());
    }

    @Entao("a estrutura da resposta deve ser válida")
    public void validarEstruturaDaResposta() {
        assertNotNull(category, "Categoria não pode ser nula");
        validaCategoria(category);
        validarProduto();
    }

    @Entao("resposta HTML de erro")
    public void respostaHTMLDeErro() {
        Response response = getResponse();
        assertNotNull(response, "Response não pode ser nula");
        assertTrue(response.contentType() != null && response.contentType().contains("text/html"), "Content-Type não é text/html");
        assertNotNull(bodyResponse, "Body response não pode ser nulo");
        assertTrue(bodyResponse.contains("<title>HTTP Status 404"), "Título HTML de erro não encontrado");
        assertTrue(bodyResponse.contains("The requested resource"), "Mensagem 'The requested resource' ausente");
        assertTrue(bodyResponse.contains("is not available"), "Mensagem 'is not available' ausente");
    }

    private void validaCategoria(Map<String, Object> category) {
        assertNotNull(category, "Categoria não pode ser nula");

        assertThat(category.keySet(), containsInAnyOrder("categoryId", "categoryName", "categoryImageId", "products"));

        assertTrue(category.get("categoryId") instanceof Number, "categoryId deve ser numérico");

        String categoryName = (String) category.get("categoryName");
        assertNotNull(categoryName, "categoryName não pode ser nulo");
        assertFalse(categoryName.isEmpty(), "categoryName vazio");

        String categoryImageId = (String) category.get("categoryImageId");
        assertNotNull(categoryImageId, "categoryImageId não pode ser nulo");
        assertFalse(categoryImageId.isEmpty(), "categoryImageId vazio");
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getProductsFromCategory() {
        return (category == null) ?
                List.of():
                (List<Map<String, Object>>) category.get("products");
    }

    private void validarProduto() {
        List<Map<String, Object>> products = getProductsFromCategory();
        assertNotNull(products, "Lista de produtos não pode ser nula");
        assertFalse(products.isEmpty(), "Lista de produtos vazia");

        products.forEach(product -> {
            assertNotNull(product, "Produto não pode ser nulo");

            assertThat(product.keySet(), containsInAnyOrder("productId", "categoryId", "productName", "price", "imageUrl"));

            assertEquals(category.get("categoryId"), product.get("categoryId"), "categoryId inconsistente");

            String productName = (String) product.get("productName");
            assertNotNull(productName, "productName não pode ser nulo");
            assertFalse(productName.isEmpty(), "productName vazio");

            Number price = (Number) product.get("price");
            assertNotNull(price, "price não pode ser nulo");
            assertTrue(price.doubleValue() > 0, "preço inválido");

            String imageUrl = (String) product.get("imageUrl");
            assertNotNull(imageUrl, "imageUrl não pode ser nulo");
            assertFalse(imageUrl.isEmpty(), "imageUrl vazio");
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
        if (response != null) {
            JsonPath responseJson = response.jsonPath();
            if (responseJson != null) {
                String userId = responseJson.getString("response.userId");
                user.setUserId(userId);
            }
        }
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
