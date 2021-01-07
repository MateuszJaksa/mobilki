package com.example.walsmart.Models;

import java.util.LinkedList;
import java.util.List;

public class Basket {
    private static List<Product> products;
    private static double totalPrice;

    static {
        products = new LinkedList<Product>();
        totalPrice = 200.0;
    }

    private Basket() {
    }

    public static List<Product> getProducts() {
        return products;
    }

    public static void addProduct(Product product) {
        //to be added when product is not null
    }

    public static void addProductSet(ProductSet set) {
        //to be added
    }

    public static double getTotalPrice() {
        return totalPrice;
    }
}
