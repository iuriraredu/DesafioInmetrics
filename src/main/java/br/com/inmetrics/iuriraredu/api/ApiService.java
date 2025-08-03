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

public class ApiService extends BaseTest {

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

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

    public Response postUploadImage(BearerToken bearerToken, String fileName, String color, int productId) {
        String endpoint = String.format("%s/%s/%s/%s",
                getPropertiesValue("UPLOAD_IMAGE_ENDPOINT"), bearerToken.userId(), fileName, color);

        Response response = given()
                .spec(getRequestSpec())
                .queryParam("product_id", productId)
                .header(AUTH_HEADER, BEARER_PREFIX + bearerToken.token())
                .contentType("multipart/form-data")
                .multiPart("file", FileManager.getImageFile(fileName))
                .when()
                .post(endpoint);
        return response;
    }
}

