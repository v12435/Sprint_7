package client;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient {
    private static final String BASE = "/api/v1/courier";

    @Step("Создать курьера")
    public Response create(Object body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body) // Gson подтянется автоматически
                .post(BASE);
    }

    @Step("Логин курьера")
    public Response login(Object body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .post(BASE + "/login");
    }

    @Step("Удалить курьера id={courierId}")
    public Response delete(int courierId) {
        return given().delete(BASE + "/" + courierId);
    }
}
