package com.example.walsmart.Models;

public class ProductRecord {
    private Product product;
    private int amount;

    public ProductRecord(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    public ProductRecord() {
        // needed for database
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void increase() {
        this.amount++;
    }

    public void decrease() {
        this.amount--;
    }

    public double getTotalPrice() {
        return amount * product.getPrice();
    }
}
