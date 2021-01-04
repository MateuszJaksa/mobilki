package com.example.walsmart.Models;

import java.util.Date;
import java.util.List;

public class Order {
    private List<Product> products;
    private double totalPrice;
    private String phoneNumber;
    private Date date;

    public Order() {
    }

    public Order(List<Product> products, double totalPrice, String phoneNumber, Date date) {
        this.products = products;
        this.totalPrice = totalPrice;
        this.phoneNumber = phoneNumber;
        this.date = date;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
