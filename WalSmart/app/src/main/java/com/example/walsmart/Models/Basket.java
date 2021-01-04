package com.example.walsmart.Models;

import java.util.List;
// this model is not a part of the database
// it is just to make the ordering process easier

public class Basket {
    private List<Product> products;
    private double totalPrice;

    public Basket(List<Product> products, double totalPrice) {
        this.products = products;
        this.totalPrice = totalPrice;
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
