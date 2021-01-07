package com.example.walsmart.Models;

import java.util.LinkedList;
import java.util.List;

public class Basket {
    private static final List<ProductRecord> products;
    private static final double totalPrice;

    static {
        products = new LinkedList<>();
        totalPrice = 200.0;
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
        return totalPrice;
    }
}
