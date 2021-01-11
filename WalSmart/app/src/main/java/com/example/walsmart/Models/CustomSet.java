package com.example.walsmart.Models;

import java.util.LinkedList;
import java.util.List;

public class CustomSet {
    private String userId;
    private String name;
    private String photo;
    private List<Product> products;
    private double totalPrice;

    public CustomSet(String userId, String name, String photo, List<Product> products, double totalPrice) {
        this.userId = userId;
        this.name = name;
        this.photo = photo;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
