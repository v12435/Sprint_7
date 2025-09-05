package client;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.OrderRequest;

import static io.restassured.RestAssured.given;

public class OrderClient {
    private static final String ORDERS = "/api/v1/orders";

    @Step("Создаём заказ")
    public Response createOrder(OrderRequest body) {
        Response resp = given()
                .contentType(ContentType.JSON)
                .body(body)
                .post(ORDERS);

        Allure.addAttachment("Тело ответа (create order)", resp.asPrettyString());
        return resp;
    }

    @Step("GET /api/v1/orders — получить список заказов")
    public Response getOrders() {
        Response resp = given()
                .contentType(ContentType.JSON)
                .get(ORDERS);

        Allure.addAttachment("Тело ответа (orders list)", resp.asPrettyString());
        return resp;
    }

    @Step("Получаем заказ по треку t={track}")
    public Response getOrderByTrack(int track) {
        Response resp = given()
                .queryParam("t", track)
                .get(ORDERS + "/track");

        Allure.addAttachment("Тело ответа (order by track)", resp.asPrettyString());
        return resp;
    }
}
