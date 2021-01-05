package com.example.walsmart.Models;

import java.util.List;

public class ProductSet {
    private String name;
    private String photo;
    private List<String> products;
    private double totalPrice;

    public ProductSet() {
    }

    public ProductSet(String name, String photo, List<String> products) {
        this.name = name;
        this.photo = photo;
        this.products = products;
        calculateTotalPrice();
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

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void calculateTotalPrice() {
        double sum = 0.0;
        for (String p : products) {
            //tu trzeba zrobic jakies query zeby po id produktow ich cene zliczyc
            //sum += p.getPrice();
        }
        this.totalPrice = sum;
    }
}
