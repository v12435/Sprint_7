package tests;

import base.BaseTest;
import client.CourierClient;
import model.Courier;
import model.CourierCredentials;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static util.DataFactory.*;

public class CourierLoginTests extends BaseTest {
    private final CourierClient courierClient = new CourierClient();
    private Integer createdId;

    @After
    public void tearDown() {
        if (createdId != null) courierClient.delete(createdId);
    }

    @Test
    public void courierCanLoginAndGetsId() {
        Courier c = randomCourier();
        courierClient.create(c).then().statusCode(201);
        int id = courierClient.login(credsFrom(c))
                .then().statusCode(200).extract().path("id");
        createdId = id;
    }

    @Test
    public void wrongPasswordGivesError() {
        Courier c = randomCourier();
        courierClient.create(c).then().statusCode(201);
        courierClient.login(new CourierCredentials(c.getLogin(), "WRONG"))
                .then().statusCode(anyOf(is(400), is(404)))
                .body("message", not(isEmptyOrNullString()));
    }

    @Test
    public void missingFieldsGiveError() {
        courierClient.login(new CourierCredentials(null, null))
                .then().statusCode(400)
                .body("message", not(isEmptyOrNullString()));
    }

    @Test
    public void loginNonExistingUserGivesError() {
        courierClient.login(new CourierCredentials("no-user", "no-pass"))
                .then().statusCode(anyOf(is(404), is(400)))
                .body("message", not(isEmptyOrNullString()));
    }
}

