package com.example.walsmart.Models;

import java.util.List;

public class Order {

    private List<ProductRecord> products;
    private double totalPrice;
    private String phoneNumber;
    private String date;
    private String city;
    private String address;
    private String userId;
    private String pickupDate;

    public Order() {
        // potrzebny do sciagania z bazy
    }

    public Order(List<ProductRecord> products, double totalPrice, String phoneNumber, String date, String city, String address, String userId, String pickup_date) {
        this.products = products;
        this.totalPrice = totalPrice;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.city = city;
        this.address = address;
        this.userId = userId;
        this.pickupDate = pickup_date;
    }

    public void increasePrice() {
        this.totalPrice *= 1.02;
    }

    public void reducePrice() {
        totalPrice *= 0.8;
    }

    public List<ProductRecord> getProducts() {
        return products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDate() {
        return date;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }
}
