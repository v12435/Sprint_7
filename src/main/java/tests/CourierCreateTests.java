package tests;

import base.BaseTest;
import client.CourierClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Courier;
import model.CourierCredentials;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static util.DataFactory.*;

public class CourierCreateTests extends BaseTest {
    private final CourierClient courierClient = new CourierClient();
    private Integer createdCourierId; // для cleanup

    @After
    public void tearDown() {
        if (createdCourierId != null) {
            courierClient.delete(createdCourierId);
        }
    }

    @Step("Создать + запомнить id")
    private void createAndRemember(Courier c) {
        Response r = courierClient.create(c);
        r.then().statusCode(anyOf(is(201), is(409))); // 201 — успех, 409 — если уже существует
        if (r.statusCode() == 201) {
            // логинимся, чтобы получить id
            int id = courierClient.login(new CourierCredentials(c.getLogin(), c.getPassword()))
                    .then().statusCode(200).extract().jsonPath().getInt("id");
            createdCourierId = id;
        }
    }

    @Test
    public void courierCanBeCreated() {
        Courier c = randomCourier();
        courierClient.create(c)
                .then().statusCode(201).body("ok", is(true));
        int id = courierClient.login(credsFrom(c))
                .then().statusCode(200).extract().path("id");
        createdCourierId = id; // для удаления
    }

    @Test
    public void cannotCreateDuplicateCourier() {
        Courier c = randomCourier();
        createAndRemember(c);
        courierClient.create(c)
                .then().statusCode(409)
                .body("message", not(isEmptyOrNullString()));
    }

    @Test
    public void missingRequiredFieldsReturnsError() {
        Courier c = new Courier(); // пустой
        courierClient.create(c)
                .then().statusCode(anyOf(is(400), is(404)))
                .body("message", not(isEmptyOrNullString()));
    }
}

