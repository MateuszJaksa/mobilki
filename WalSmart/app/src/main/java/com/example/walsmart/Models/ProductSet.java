package com.example.walsmart.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ProductSet implements Parcelable {
    private String userId;
    private String name;
    private String photo;
    private ArrayList<Product> products;
    private double totalPrice;

    public ProductSet() {
        // needed for downloading from database
    }

    public ProductSet(String userId, String name, String photo, ArrayList<Product> products, double totalPrice) {
        this.userId = userId;
        this.name = name;
        this.photo = photo;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    protected ProductSet(Parcel in) {
        userId = in.readString();
        name = in.readString();
        photo = in.readString();
        totalPrice = in.readDouble();
    }

    public static final Creator<ProductSet> CREATOR = new Creator<ProductSet>() {
        @Override
        public ProductSet createFromParcel(Parcel in) {
            return new ProductSet(in);
        }

        @Override
        public ProductSet[] newArray(int size) {
            return new ProductSet[size];
        }
    };

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(name);
        dest.writeString(photo);
        dest.writeDouble(totalPrice);
    }
}
