package com.example.walsmart.Models;

import java.util.LinkedList;
import java.util.List;

public class Basket {
    private static final List<ProductRecord> products;

    static {
        products = new LinkedList<>();
    }

    private Basket() {
    }

    public static List<ProductRecord> getProducts() {
        return products;
    }

    public static void addProductRecord(ProductRecord product) {
        products.add(product);
    }

    public static double getTotalPrice() {
        double totalPrice = 0.0;
        for (ProductRecord productRecord : products) {
            totalPrice += productRecord.getTotalPrice();
        }
        return totalPrice;
    }
}
