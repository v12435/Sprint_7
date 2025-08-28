package tests;

import base.BaseTest; // если нет BaseTest — см. ниже вариант без него
import com.google.gson.Gson;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.Courier;
import model.CourierCredentials;
import org.junit.After;
import org.junit.Test;

import java.util.UUID;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Feature("Courier")
@Story("Create courier")
@Owner("Vasiliy")
public class CourierCreateTests extends BaseTest { // <-- используем общий базовый сетап

    private static final Gson GSON = new Gson();
    private Integer createdId;

    @After
    public void tearDown() {
        if (createdId != null) {
            Allure.step("Удаляем курьера id=" + createdId, () ->
                    deleteCourier(createdId).then().statusCode(200)
            );
        }
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Курьера можно создать: ответ 201 и ok:true")
    public void createCourier_201_andOkTrue() {
        String login = uniqueLogin();
        String password = "1234";
        String firstName = "saske";

        createCourier(login, password, firstName)
                .then().statusCode(201).body("ok", is(true));

        createdId = loginCourier(login, password)
                .then().statusCode(200).extract().path("id");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Нельзя создать двух одинаковых курьеров: ответ 409 и корректное сообщение")
    public void createDuplicate_409_withMessage() {
        String login = uniqueLogin();
        String password = "1234";
        String firstName = "saske";

        createCourier(login, password, firstName).then().statusCode(201);

        createCourier(login, password, firstName)
                .then().statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        createdId = loginCourier(login, password).then().statusCode(200).extract().path("id");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Без логина вернётся 400 и сообщение об ошибке")
    public void createWithoutLogin_400_withMessage() {
        createCourier(null, "1234", "saske")
                .then().statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Без пароля вернётся 400 и сообщение об ошибке")
    public void createWithoutPassword_400_withMessage() {
        createCourier("ninja_" + UUID.randomUUID().toString().substring(0, 8), null, "saske")
                .then().statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    // ---------------- Шаги с понятными названиями ----------------

    private Response createCourier(String login, String password, String firstName) {
        return Allure.step("POST /api/v1/courier — создать курьера (login=" + login + ", firstName=" + firstName + ")", () ->
                given()
                        .contentType(ContentType.JSON)
                        .body(GSON.toJson(new Courier(login, password, firstName)))
                        .post("/api/v1/courier")
        );
    }

    private Response loginCourier(String login, String password) {
        return Allure.step("POST /api/v1/courier/login — логин (login=" + login + ")", () ->
                given()
                        .contentType(ContentType.JSON)
                        .body(GSON.toJson(new CourierCredentials(login, password)))
                        .post("/api/v1/courier/login")
        );
    }

    private Response deleteCourier(int courierId) {
        return Allure.step("DELETE /api/v1/courier/" + courierId + " — удалить курьера", () ->
                given().delete("/api/v1/courier/" + courierId)
        );
    }

    private String uniqueLogin() {
        return "ninja_" + UUID.randomUUID().toString().substring(0, 8);
    }
}
