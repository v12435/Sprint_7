package tests;

import base.BaseTest;
import client.OrdersClient;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class OrdersListTests extends BaseTest {
    private final OrdersClient ordersClient = new OrdersClient();

    @Test
    public void ordersListReturnsArray() {
        ordersClient.list()
                .then().statusCode(200)
                .body("orders", notNullValue()); // или проверь корневой массив, если структура иная
    }
}

