package tests;

import base.BaseTest;
import client.OrdersClient;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class OrdersListTests extends BaseTest {
    private final OrdersClient ordersClient = new OrdersClient();

    @Test
    public void ordersListIsReturned() {
        ordersClient.list()
                .then().statusCode(200)
                .body("orders", notNullValue());
    }
}
