package tests;

import base.BaseTest;
import client.CourierClient;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.*;

@Feature("Courier")
@Story("Login courier")
@Owner("Vasiliy")
public class CourierLoginTests extends BaseTest {

    private final CourierClient courier = new CourierClient();

    private String createdLogin;
    private String createdPassword;
    private Integer createdId;

    @After
    public void cleanUp() {
        if (createdLogin != null && createdPassword != null && createdId == null) {
            Response loginOk = courier.loginCourier(createdLogin, createdPassword);
            if (loginOk.statusCode() == 200) {
                createdId = loginOk.then().extract().path("id");
            }
        }
        if (createdId != null) {
            courier.deleteCourier(createdId).then().statusCode(200).body("ok", is(true));
        }
        createdLogin = null;
        createdPassword = null;
        createdId = null;
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Курьер может авторизоваться: 200 и id в ответе")
    public void courierCanLoginAndGetsId() {
        String login = uniqueLogin();
        String password = "1234";

        courier.createCourier(login, password, "saske").then().statusCode(201);

        createdLogin = login;
        createdPassword = password;
        createdId = courier.loginCourier(login, password)
                .then().statusCode(200)
                .body("id", notNullValue())
                .extract().path("id");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Нет логина: 400 и сообщение")
    public void missingLoginReturns400() {
        courier.loginCourier(null, "1234")
                .then().statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Нет пароля: 400 и сообщение")
    public void missingPasswordReturns400() {
        courier.loginCourier("user_" + UUID.randomUUID().toString().substring(0, 8), null)
                .then().statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Неверный пароль: 404 «Учетная запись не найдена»")
    public void wrongPasswordGivesError() {
        String login = uniqueLogin();
        String password = "1234";

        courier.createCourier(login, password, "saske").then().statusCode(201);
        createdLogin = login;
        createdPassword = password;
        createdId = courier.loginCourier(login, password).then().extract().path("id");

        courier.loginCourier(login, "WRONG")
                .then().statusCode(404)
                .body("message", containsString("Учетная запись не найдена"));
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Несуществующий пользователь: 404 «Учетная запись не найдена»")
    public void nonExistentUserReturns404() {
        courier.loginCourier("ghost_" + UUID.randomUUID().toString().substring(0, 8), "1234")
                .then().statusCode(404)
                .body("message", containsString("Учетная запись не найдена"));
    }

    private String uniqueLogin() {
        return "qa_" + UUID.randomUUID().toString().substring(0, 8);
    }
}
