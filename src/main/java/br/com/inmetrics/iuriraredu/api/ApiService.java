package br.com.inmetrics.iuriraredu.api;

import br.com.inmetrics.iuriraredu.models.BearerToken;
import br.com.inmetrics.iuriraredu.models.User;
import br.com.inmetrics.iuriraredu.settings.BaseTest;
import br.com.inmetrics.iuriraredu.utils.FileManager;
import io.restassured.response.Response;

import java.util.Map;
import java.util.Objects;

import static br.com.inmetrics.iuriraredu.utils.ConfigManager.getPropertiesValue;
import static io.restassured.RestAssured.given;

/**
 * Classe ApiService que contém métodos para interagir com a API do sistema.
 * Inclui métodos para buscar produtos, criar usuários, realizar login e fazer upload de imagens.
 */
public class ApiService extends BaseTest {
    /**
     * Método para buscar produtos com base no nome e quantidade por categoria.
     * Se o parâmetro notFound for fornecido, utiliza um endpoint específico.
     *
     * @param productName Nome do produto a ser buscado.
     * @param quantity Quantidade por categoria (pode ser null).
     * @param notFound Endpoint alternativo se não for encontrado (pode ser null).
     * @return Response da requisição.
     */
    public Response searchProducts(String productName, Integer quantity, String notFound) {
        String endpoint = (notFound != null)
                ? getPropertiesValue("SEARCH_PRODUCTS_NOT_FOUND_ENDPOINT") + "/" + notFound
                : getPropertiesValue("SEARCH_PRODUCTS_ENDPOINT");

        return given()
                .spec(getRequestSpec())
                .queryParam("name", productName)
                .queryParam("quantityPerEachCategory", quantity) // Só adiciona se não for null
                .when()
                .get(endpoint);
    }

    /**
     * Método para criar uma conta de usuário.
     *
     * @param user Objeto User contendo os dados do usuário a ser criado.
     * @return Response da requisição de criação de conta.
     */
    public Response postCreateUserAccount(User user) {
        Objects.requireNonNull(user, "User não pode ser nulo");

        return given()
                .spec(getRequestSpec())
                .headers(Map.of(
                        "accept", "*/*",
                        "Content-Type", "application/json"
                ))
                .body(user.toJson())
                .when()
                .post(getPropertiesValue("CREATE_USER_ACCOUNT_ENDPOINT"));
    }

    /**
     * Método para realizar o login de um usuário.
     *
     * @param user Objeto User contendo os dados de login.
     * @return BearerToken contendo as informações do token, userId e sessionId.
     */
    public BearerToken postLogin(User user) {
        Response resp = given()
                .spec(getRequestSpec())
                .body(Map.of(
                        "email", user.getEmail(),
                        "loginPassword", user.getPassword(),
                        "loginUser", user.getLoginName()
                ))
                .headers(Map.of(
                        "accept", "*/*",
                        "Content-Type", "application/json"
                ))
                .when()
                .post(getPropertiesValue("LOGIN_ENDPOINT"));

        return new BearerToken(
                resp.jsonPath().getString("statusMessage.userId"),
                resp.jsonPath().getString("statusMessage.token"),
                resp.jsonPath().getString("statusMessage.sessionId")
        );
    }

    /**
     * Método para fazer o upload de uma imagem associada a um produto.
     *
     * @param bearerToken Token de autenticação do usuário.
     * @param fileName Nome do arquivo da imagem a ser enviado.
     * @param color Cor associada ao produto.
     * @param productId ID do produto ao qual a imagem será associada.
     * @return Response da requisição de upload da imagem.
     */
    public Response postUploadImage(BearerToken bearerToken, String fileName, String color, int productId) {
        String endpoint = String.format("%s/%s/%s/%s",
                getPropertiesValue("UPLOAD_IMAGE_ENDPOINT"), bearerToken.userId(), fileName, color);

        return given()
                .spec(getRequestSpec())
                .queryParam("product_id", productId)
                .header("Authorization", "Bearer " + bearerToken.token())
                .contentType("multipart/form-data")
                .multiPart("file", FileManager.getImageFile(fileName))
                .when()
                .post(endpoint);
    }
}

