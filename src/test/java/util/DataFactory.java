package util;

import com.github.javafaker.Faker;
import model.Courier;
import model.OrderRequest;

import java.util.Locale;

public class DataFactory {
    private static final Faker faker = new Faker(new Locale("en"));

    public static String uniqueLogin() {
        return "qa_" + faker.name().username() + "_" + faker.number().digits(4);
    }

    public static Courier randomCourier() {
        return new Courier(uniqueLogin(), "1234", "saske");
    }

    public static OrderRequest orderBlack()   { return orderWithColors(new String[]{"BLACK"}); }
    public static OrderRequest orderGrey()    { return orderWithColors(new String[]{"GREY"}); }
    public static OrderRequest orderBoth()    { return orderWithColors(new String[]{"BLACK","GREY"}); }
    public static OrderRequest orderNoColor() { return orderWithColors(null); }

    public static OrderRequest orderWithColors(String[] colors) {
        return new OrderRequest(
                "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                4,
                "+7 800 355 35 35",
                5,
                "2020-06-06",
                "Saske, come back to Konoha",
                colors
        );
    }
}
