package util;

import model.Courier;
import model.OrderRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class DataFactory {
    public static Courier randomCourier() {
        String login = "qa_" + UUID.randomUUID().toString().substring(0, 8);
        return new Courier(login, "Passw0rd!", "Vasya");
    }

    public static OrderRequest orderWithColors(List<String> colors) {
        return new OrderRequest(
                "Naruto", "Uchiha", "Konoha, 142 apt.", "4",
                "+7 800 355 35 35", 5,
                LocalDate.now().plusDays(1).toString(),
                "Saske, come back", colors
        );
    }
}
