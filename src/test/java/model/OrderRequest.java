package model;

public class OrderRequest {
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color; // может быть null — Gson не отправит поле

    public OrderRequest() { }

    public OrderRequest(String firstName, String lastName, String address,
                        int metroStation, String phone, int rentTime,
                        String deliveryDate, String comment, String[] color) {
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

    // геттеры/сеттеры при желании (не обязательны для Gson)
    public String getFirstName() { return firstName; }
    public void setFirstName(String v) { this.firstName = v; }
    public String getLastName() { return lastName; }
    public void setLastName(String v) { this.lastName = v; }
    public String getAddress() { return address; }
    public void setAddress(String v) { this.address = v; }
    public int getMetroStation() { return metroStation; }
    public void setMetroStation(int v) { this.metroStation = v; }
    public String getPhone() { return phone; }
    public void setPhone(String v) { this.phone = v; }
    public int getRentTime() { return rentTime; }
    public void setRentTime(int v) { this.rentTime = v; }
    public String getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(String v) { this.deliveryDate = v; }
    public String getComment() { return comment; }
    public void setComment(String v) { this.comment = v; }
    public String[] getColor() { return color; }
    public void setColor(String[] v) { this.color = v; }
}
