package tests;

import base.BaseTest;
import client.CourierClient;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import model.Courier;
import model.CourierCredentials;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.*;
import util.DataFactory;

@Feature("Courier")
@Story("Create courier")
@Owner("Vasiliy")
public class CourierCreateTests extends BaseTest {

    private final CourierClient courier = new CourierClient();
    private Integer createdId;

    @After
    public void tearDown() {
        if (createdId != null) {
            courier.deleteCourier(createdId).then().statusCode(SC_OK).body("ok", is(true));
            createdId = null;
        }
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Курьера можно создать: 201 и ok:true")
    public void createCourier201AndOkTrue() {
        Courier body = DataFactory.randomCourier();

        courier.createCourier(body)
                .then().statusCode(SC_CREATED).body("ok", is(true));

        createdId = courier.loginCourier(new CourierCredentials(body.getLogin(), body.getPassword()))
                .then().statusCode(SC_OK)
                .extract().path("id");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Дубликат логина: 409 и корректное сообщение")
    public void createDuplicate409WithMessage() {
        Courier body = DataFactory.randomCourier();

        courier.createCourier(body).then().statusCode(SC_CREATED);
        createdId = courier.loginCourier(new CourierCredentials(body.getLogin(), body.getPassword()))
                .then().extract().path("id");

        courier.createCourier(body)
                .then().statusCode(SC_CONFLICT)
                .body("message", containsString("Этот логин уже используется"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Без логина: 400 и сообщение")
    public void createWithoutLogin400() {
        Courier body = new Courier(null, "1234", "saske");
        courier.createCourier(body)
                .then().statusCode(SC_BAD_REQUEST)
                .body("message", containsString("Недостаточно данных"));
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Без пароля: 400 и сообщение")
    public void createWithoutPassword400() {
        Courier body = new Courier(DataFactory.uniqueLogin(), null, "saske");
        courier.createCourier(body)
                .then().statusCode(SC_BAD_REQUEST)
                .body("message", containsString("Недостаточно данных"));
    }
}
