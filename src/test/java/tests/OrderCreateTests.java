package tests;

import base.BaseTest;
import client.OrderClient;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.OrderRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import util.DataFactory;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
@Feature("Orders")
@Story("Create order")
@Owner("Vasiliy")
public class OrderCreateTests extends BaseTest {

    private final OrderClient orders = new OrderClient();

    @Parameterized.Parameters(name = "Цвета: {0}")
    public static Object[][] colors() {
        return new Object[][]{
                { DataFactory.orderBlack()   },
                { DataFactory.orderGrey()    },
                { DataFactory.orderBoth()    },
                { DataFactory.orderNoColor() }
        };
    }

    private final OrderRequest order;

    public OrderCreateTests(OrderRequest order) {
        this.order = order;
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Создание заказа возвращает 201 и track")
    public void createOrder_returnsTrack() {
        String label = DataFactory.label(order.getColor());
        Response resp = orders.createOrder(order, label);
        resp.then().statusCode(201).body("track", notNullValue());
    }
}
