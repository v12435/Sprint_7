package client;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class OrdersClient {
    private static final String BASE = "/api/v1/orders";

    @Step("Создать заказ")
    public io.restassured.response.Response create(Object body) {
        return given().contentType(ContentType.JSON).body(body).post(BASE);
    }

    @Step("Получить список заказов")
    public io.restassured.response.Response list() {
        return given().get(BASE);
    }

    @Step("Принять заказ id={orderId} курьером id={courierId}")
    public io.restassured.response.Response accept(int orderId, int courierId) {
        return given().queryParam("courierId", courierId).put(BASE + "/accept/" + orderId);
    }

    @Step("Получить заказ по треку t={track}")
    public io.restassured.response.Response getByTrack(int track) {
        return given().queryParam("t", track).get(BASE + "/track");
    }
}
