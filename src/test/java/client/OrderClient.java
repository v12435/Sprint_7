package client;

import com.google.gson.Gson;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.OrderRequest;

import static io.restassured.RestAssured.given;

public class OrderClient {
    private static final Gson GSON = new Gson();

    @Step("Создаём заказ (color={colorsLabel})")
    public Response createOrder(OrderRequest body, String colorsLabel) {
        String json = GSON.toJson(body);
        Allure.addAttachment("Тело запроса (create order)", json);

        Response resp = given()
                .contentType(ContentType.JSON)
                .body(json)
                .post("/api/v1/orders");

        Allure.addAttachment("Тело ответа (create order)", resp.asPrettyString());
        return resp;
    }

    @Step("GET /api/v1/orders — получить список заказов")
    public Response getOrders() {
        Response resp = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/orders");

        Allure.addAttachment("Тело ответа (orders list)", "application/json", resp.asPrettyString(), "json");
        return resp;
    }


    @Step("Получаем заказ по треку t={track}")
    public Response getOrderByTrack(int track) {
        Response resp = given()
                .queryParam("t", track)
                .get("/api/v1/orders/track");
        Allure.addAttachment("Тело ответа (order by track)", resp.asPrettyString());
        return resp;
    }
}
