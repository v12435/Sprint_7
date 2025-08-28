package model;

import java.util.List;

public class OrderRequest {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation; // дока позволяет строку/число — берём строку
    private String phone;
    private int rentTime;
    private String deliveryDate; // "YYYY-MM-DD"
    private String comment;
    private List<String> color;

    public OrderRequest() {}

    public OrderRequest(String firstName, String lastName, String address, String metroStation,
                        String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    // геттеры/сеттеры по необходимости
}
