package tests;

import base.BaseTest;
import client.CourierClient;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.*;

@Feature("Courier")
@Story("Create courier")
@Owner("Vasiliy")
public class CourierCreateTests extends BaseTest {

    private final CourierClient courier = new CourierClient();
    private Integer createdId;

    @After
    public void tearDown() {
        if (createdId != null) {
            courier.deleteCourier(createdId).then().statusCode(200).body("ok", is(true));
            createdId = null;
        }
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Курьера можно создать: 201 и ok:true")
    public void createCourier_201_andOkTrue() {
        String login = uniqueLogin();
        String password = "1234";
        String firstName = "saske";

        courier.createCourier(login, password, firstName)
                .then().statusCode(201).body("ok", is(true));

        createdId = courier.loginCourier(login, password)
                .then().statusCode(200)
                .extract().path("id");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Дубликат логина: 409 и корректное сообщение")
    public void createDuplicate_409_withMessage() {
        String login = uniqueLogin();
        String password = "1234";

        courier.createCourier(login, password, "saske").then().statusCode(201);
        createdId = courier.loginCourier(login, password).then().extract().path("id");

        courier.createCourier(login, password, "saske")
                .then().statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Без логина: 400 и сообщение")
    public void createWithoutLogin_returns400() {
        courier.createCourier(null, "1234", "saske")
                .then().statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Без пароля: 400 и сообщение")
    public void createWithoutPassword_returns400() {
        courier.createCourier(uniqueLogin(), null, "saske")
                .then().statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    private String uniqueLogin() {
        return "qa_" + UUID.randomUUID().toString().substring(0, 8);
    }
}
