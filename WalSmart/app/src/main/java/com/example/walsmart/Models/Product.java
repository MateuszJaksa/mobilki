package com.example.walsmart.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private String name;
    private String photo;
    private String size; // np: 6 sztuk / 250 gramow / 2 litry
    private double price;

    public Product() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Product(String name, String photo, String size, double price) {
        this.name = name;
        this.photo = photo;
        this.size = size;
        this.price = price;
    }

    protected Product(Parcel in) {
        name = in.readString();
        photo = in.readString();
        size = in.readString();
        price = in.readDouble();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(photo);
        dest.writeString(size);
        dest.writeDouble(price);
    }
}
