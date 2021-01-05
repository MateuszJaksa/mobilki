package com.example.walsmart.Models;

import android.util.Log;

import com.example.walsmart.MyCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ProductSet {
    private String name;
    private String photo;
    private List<String> products;
    private double totalPrice;

    public ProductSet() {
    }
    public ProductSet(String name, String photo, List<String> products, double totalPrice) {
        this.name = name;
        this.photo = photo;
        this.products = products;
        this.totalPrice = totalPrice;
    }
    public ProductSet(String name, String photo, List<String> products) {
        this.name = name;
        this.photo = photo;
        this.products = products;
        this.totalPrice = 0.0;
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

    // liczenie totalPrice dziala ale najpierw jest tworzony obiekt, a potem sciagane obiekty z bazy danych
    public void calculateTotalPrice() {
        readData(value -> {
            totalPrice += value;
            Log.d("Debug", "Msg: " + totalPrice);
        });
    }

    public void readData(MyCallback myCallback) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        for (String key : products) {
            mDatabase.child(String.format("products/%s/price", key)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    myCallback.onCallback(dataSnapshot.getValue(Double.class));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }
}
