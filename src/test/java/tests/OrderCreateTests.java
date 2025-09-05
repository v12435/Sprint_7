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
import static org.apache.http.HttpStatus.*;

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
    public void createOrder201AndTrack() {
        Response resp = orders.createOrder(order);
        resp.then().statusCode(SC_CREATED).body("track", notNullValue());
    }
}
