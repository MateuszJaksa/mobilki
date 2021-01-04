package com.example.walsmart.Models;

import java.util.List;

public class ProductSet {
    private String name;
    private List<Product> products;
    private double totalPrice;

    public ProductSet() {
    }

    public ProductSet(String name, List<Product> products, double totalPrice) {
        this.name = name;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
