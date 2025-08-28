package client;

import com.google.gson.Gson;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.Courier;
import model.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierClient {
    private static final Gson GSON = new Gson();

    @Step("Создаём курьера: login={login}, firstName={firstName}")
    public Response createCourier(String login, String password, String firstName) {
        Courier body = new Courier(login, password, firstName);
        String json = GSON.toJson(body);
        Allure.addAttachment("Тело запроса (create courier)", json);

        Response resp = given()
                .contentType(ContentType.JSON)
                .body(json)
                .post("/api/v1/courier");

        Allure.addAttachment("Тело ответа (create courier)", resp.asPrettyString());
        return resp;
    }

    @Step("Логинимся курьером: login={login}")
    public Response loginCourier(String login, String password) {
        CourierCredentials creds = new CourierCredentials(login, password);
        String json = GSON.toJson(creds);
        Allure.addAttachment("Тело запроса (login)", json);

        Response resp = given()
                .contentType(ContentType.JSON)
                .body(json)
                .post("/api/v1/courier/login");

        Allure.addAttachment("Тело ответа (login)", resp.asPrettyString());
        return resp;
    }

    @Step("Удаляем курьера id={courierId}")
    public Response deleteCourier(int courierId) {
        Response resp = given()
                .delete("/api/v1/courier/" + courierId);
        Allure.addAttachment("Тело ответа (delete)", resp.asPrettyString());
        return resp;
    }
}
