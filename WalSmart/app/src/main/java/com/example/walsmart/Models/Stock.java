package com.example.walsmart.Models;


import java.util.ArrayList;
import java.util.List;

public class Stock {

    private static final List<Product> products;

    static {
        products = new ArrayList<>();
    }

    private Stock() {
    }

    public static List<Product> getProducts() {
        return products;
    }

    public static void clear() {
        products.clear();
    }

    public static void add(Product p) {
        products.add(p);
    }

}