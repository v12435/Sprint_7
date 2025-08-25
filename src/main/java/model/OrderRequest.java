package model;

import java.util.List;

public class OrderRequest {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;   // "2025-08-25"
    private String comment;
    private List<String> color;    // ["BLACK"], ["GREY"], ["BLACK","GREY"], []

    public OrderRequest() {}
    public OrderRequest(String firstName, String lastName, String address, String metroStation,
                        String phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName; this.lastName = lastName; this.address = address;
        this.metroStation = metroStation; this.phone = phone; this.rentTime = rentTime;
        this.deliveryDate = deliveryDate; this.comment = comment; this.color = color;
    }
    // getters/setters…
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getAddress() { return address; }
    public String getMetroStation() { return metroStation; }
    public String getPhone() { return phone; }
    public int getRentTime() { return rentTime; }
    public String getDeliveryDate() { return deliveryDate; }
    public String getComment() { return comment; }
    public List<String> getColor() { return color; }
    public void setFirstName(String v) { this.firstName = v; }
    public void setLastName(String v) { this.lastName = v; }
    public void setAddress(String v) { this.address = v; }
    public void setMetroStation(String v) { this.metroStation = v; }
    public void setPhone(String v) { this.phone = v; }
    public void setRentTime(int v) { this.rentTime = v; }
    public void setDeliveryDate(String v) { this.deliveryDate = v; }
    public void setComment(String v) { this.comment = v; }
    public void setColor(List<String> v) { this.color = v; }
}
