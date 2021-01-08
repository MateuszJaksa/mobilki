package com.example.walsmart.Models;

import java.util.List;

public class Order {
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

    private List<ProductRecord> products;
    private double totalPrice;
    private String phoneNumber;
    private String date;
    private String city;
    private String address;

    public Order() {
    }

    public Order(List<ProductRecord> products, double totalPrice, String phoneNumber, String date, String city, String address) {
        this.products = products;
        this.totalPrice = totalPrice;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.city = city;
        this.address = address;
    }
}
