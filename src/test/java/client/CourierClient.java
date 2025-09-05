package client;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.Courier;
import model.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierClient {
    private static final String COURIER = "/api/v1/courier";
    private static final String LOGIN = "/api/v1/courier/login";

    @Step("Создаём курьера: {courier}")
    public Response createCourier(Courier courier) {
        Response resp = given()
                .contentType(ContentType.JSON)
                .body(courier)
                .post(COURIER);

        Allure.addAttachment("Тело ответа (create courier)", resp.asPrettyString());
        return resp;
    }

    @Step("Логинимся курьером: {creds}")
    public Response loginCourier(CourierCredentials creds) {
        Response resp = given()
                .contentType(ContentType.JSON)
                .body(creds)
                .post(LOGIN);

        Allure.addAttachment("Тело ответа (login)", resp.asPrettyString());
        return resp;
    }

    @Step("Удаляем курьера id={courierId}")
    public Response deleteCourier(int courierId) {
        Response resp = given()
                .delete(COURIER + "/" + courierId);
        Allure.addAttachment("Тело ответа (delete)", resp.asPrettyString());
        return resp;
    }
}
