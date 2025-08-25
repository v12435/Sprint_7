package tests;

import base.BaseTest;
import client.OrdersClient;
import model.OrderRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static util.DataFactory.orderWithColors;

@RunWith(Parameterized.class)
public class OrderCreateTests extends BaseTest {
    private final OrdersClient ordersClient = new OrdersClient();

    @Parameterized.Parameters(name = "colors={0}")
    public static Object[][] colors() {
        return new Object[][]{
                {Collections.singletonList("BLACK")},
                {Collections.singletonList("GREY")},
                {Arrays.asList("BLACK","GREY")},
                {Collections.emptyList()}
        };
    }

    @Parameterized.Parameter
    public List<String> colorsParam;

    @Test
    public void createOrderWithColors_shouldReturnTrack() {
        OrderRequest req = orderWithColors(colorsParam);
        ordersClient.create(req)
                .then().statusCode(201)
                .body("track", notNullValue());
    }
}
