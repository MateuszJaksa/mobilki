package com.example.walsmart.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.walsmart.MyCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ProductSet implements Parcelable {
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

    protected ProductSet(Parcel in) {
        name = in.readString();
        photo = in.readString();
        products = in.createStringArrayList();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(photo);
        dest.writeStringList(products);
        dest.writeDouble(totalPrice);
    }
}
