package tests;

import base.BaseTest;
import client.CourierClient;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import model.Courier;
import model.CourierCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.*;
import util.DataFactory;

@Feature("Courier")
@Story("Login courier")
@Owner("Vasiliy")
public class CourierLoginTests extends BaseTest {

    private final CourierClient courier = new CourierClient();
    private Courier createdCourier;
    private Integer createdId;

    @Before
    public void setUp() {
        createdCourier = DataFactory.randomCourier();
        courier.createCourier(createdCourier).then().statusCode(SC_CREATED);
        createdId = courier.loginCourier(new CourierCredentials(createdCourier.getLogin(), createdCourier.getPassword()))
                .then().statusCode(SC_OK).extract().path("id");
    }

    @After
    public void cleanUp() {
        if (createdId != null) {
            courier.deleteCourier(createdId).then().statusCode(SC_OK).body("ok", is(true));
            createdId = null;
        }
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Курьер может авторизоваться: 200 и id в ответе")
    public void courierCanLoginAndGetsId() {
        courier.loginCourier(new CourierCredentials(createdCourier.getLogin(), createdCourier.getPassword()))
                .then().statusCode(SC_OK)
                .body("id", notNullValue());
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Нет логина: 400 и сообщение")
    public void missingLogin400() {
        courier.loginCourier(new CourierCredentials(null, createdCourier.getPassword()))
                .then().statusCode(SC_BAD_REQUEST)
                .body("message", containsString("Недостаточно данных"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Нет пароля: 400 и сообщение")
    public void missingPassword400() {
        courier.loginCourier(new CourierCredentials(createdCourier.getLogin(), null))
                .then().statusCode(SC_BAD_REQUEST)
                .body("message", containsString("Недостаточно данных"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Неверный пароль: 404 «Учетная запись не найдена»")
    public void wrongPassword404() {
        courier.loginCourier(new CourierCredentials(createdCourier.getLogin(), "WRONG"))
                .then().statusCode(SC_NOT_FOUND)
                .body("message", containsString("Учетная запись не найдена"));
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Несуществующий пользователь: 404 «Учетная запись не найдена»")
    public void nonExistentUser404() {
        courier.loginCourier(new CourierCredentials(DataFactory.uniqueLogin(), "1234"))
                .then().statusCode(SC_NOT_FOUND)
                .body("message", containsString("Учетная запись не найдена"));
    }
}
