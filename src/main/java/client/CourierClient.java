package client;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class CourierClient {
    private static final String BASE = "/api/v1/courier";

    @Step("Создать курьера")
    public io.restassured.response.Response create(Object body) {
        return given().contentType(ContentType.JSON).body(body).post(BASE);
    }

    @Step("Логин курьера")
    public io.restassured.response.Response login(Object body) {
        return given().contentType(ContentType.JSON).body(body).post(BASE + "/login");
    }

    @Step("Удалить курьера по id={courierId}")
    public io.restassured.response.Response delete(int courierId) {
        return given().delete(BASE + "/" + courierId);
    }
}
