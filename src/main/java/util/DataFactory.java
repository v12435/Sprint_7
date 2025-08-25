package util;

import model.Courier;
import model.CourierCredentials;
import model.OrderRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class DataFactory {
    public static Courier randomCourier() {
        String login = "qa_" + UUID.randomUUID().toString().substring(0,8);
        return new Courier(login, "Passw0rd!", "Vasya");
    }

    public static CourierCredentials credsFrom(Courier c) {
        return new CourierCredentials(c.getLogin(), c.getPassword());
    }

    public static OrderRequest orderWithColors(List<String> colors) {
        return new OrderRequest(
                "Иван", "Иванов", "Москва, Арбат 1", "1",
                "+79990000000", 3, LocalDate.now().plusDays(1).toString(),
                "Позвонить за 30 минут", colors
        );
    }
}

