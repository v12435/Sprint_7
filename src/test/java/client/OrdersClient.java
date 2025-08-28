package client;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrdersClient {
    private static final String BASE = "/api/v1/orders";

    @Step("Создать заказ")
    public Response create(Object body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .post(BASE);
    }

    @Step("Получить список заказов")
    public Response list() {
        return given().get(BASE);
    }

    @Step("Получить заказ по треку {track}")
    public Response getByTrack(int track) {
        return given().queryParam("t", track).get(BASE + "/track");
    }

    @Step("Принять заказ {orderId} курьером {courierId}")
    public Response accept(int orderId, int courierId) {
        return given().queryParam("courierId", courierId).put(BASE + "/accept/" + orderId);
    }

    @Step("Завершить заказ {orderId}")
    public Response finish(int orderId) {
        return given().put(BASE + "/finish/" + orderId);
    }

    @Step("Отменить заказ по треку {track}")
    public Response cancel(int track) {
        return given().contentType(ContentType.JSON)
                .body("{\"track\":" + track + "}")
                .put(BASE + "/cancel");
    }
}
