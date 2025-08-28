package tests;

import base.BaseTest;
import client.OrderClient;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

@Feature("Orders")
@Story("List orders")
@Owner("Vasiliy")
public class OrdersListTests extends BaseTest {

    private final OrderClient orders = new OrderClient();

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("GET /api/v1/orders возвращает массив orders")
    public void getOrders_returnsList() {
        orders.getOrders()
                .then().statusCode(200)
                .body("orders", notNullValue())
                .body("orders.size()", greaterThan(0));
    }
}
