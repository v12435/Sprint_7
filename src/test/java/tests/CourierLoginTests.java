package tests;

import base.BaseTest;
import client.CourierClient;
import model.Courier;
import org.junit.After;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.*;

public class CourierLoginTests extends BaseTest {
    private final CourierClient courierClient = new CourierClient();
    private Integer createdId;

    @After
    public void tearDown() {
        if (createdId != null) courierClient.delete(createdId);
    }

    private Courier newCourier() {
        String login = "qa_" + UUID.randomUUID().toString().substring(0, 8);
        return new Courier(login, "Passw0rd!", "Test");
    }

    @Test
    public void courierCanLoginAndGetsId() {
        Courier c = newCourier();
        courierClient.create(c).then().statusCode(201);

        createdId = courierClient.login(new Courier(c.getLogin(), c.getPassword(), null))
                .then()
                .statusCode(200)
                .extract().path("id");
    }

    @Test
    public void wrongPasswordGivesError() {
        Courier c = newCourier();
        courierClient.create(c).then().statusCode(201);

        courierClient.login(new Courier(c.getLogin(), "WRONG", null))
                .then()
                .statusCode(400) // строго ждём 400
                .body("message", not(isEmptyOrNullString()));
    }

    @Test
    public void missingFieldsGiveError() {
        courierClient.login(new Courier(null, null, null))
                .then()
                .statusCode(400) // строго ждём 400
                .body("message", not(isEmptyOrNullString()));
    }
}
