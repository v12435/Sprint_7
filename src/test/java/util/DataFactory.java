package util;

import model.OrderRequest;

public class DataFactory {

    public static OrderRequest orderBlack()   { return orderWithColors(new String[]{"BLACK"}); }
    public static OrderRequest orderGrey()    { return orderWithColors(new String[]{"GREY"}); }
    public static OrderRequest orderBoth()    { return orderWithColors(new String[]{"BLACK","GREY"}); }
    public static OrderRequest orderNoColor() { return orderWithColors(null); } // null -> поле не отправится

    public static OrderRequest orderWithColors(String[] colors) {
        return new OrderRequest(
                "Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                4, // int, не "4"
                "+7 800 355 35 35",
                5,
                "2020-06-06",
                "Saske, come back to Konoha",
                colors
        );
    }

    public static String label(String[] c) {
        if (c == null) return "NONE";
        if (c.length == 0) return "[]";
        return String.join(",", c);
    }
}
