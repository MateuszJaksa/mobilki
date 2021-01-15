package com.example.walsmart.Models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Basket {
    private static final List<ProductRecord> products;

    static {
        products = new ArrayList<>();
    }

    private Basket() {
    }

    public static List<ProductRecord> getProducts() {
        return products;
    }

    public static void addProductRecord(ProductRecord product) {
        products.add(product);
    }

    public static void removeProductRecord(ProductRecord product) {
        products.remove(product);
    }

    public static void clear() {
        products.clear();
    }

    public static double getTotalPrice() {
        double totalPrice = 0.0;
        for (ProductRecord productRecord : products) {
            totalPrice += productRecord.getTotalPrice();
        }
        return totalPrice;
    }
}
