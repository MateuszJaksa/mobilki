package com.example.walsmart.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.walsmart.BasketActivity;
import com.example.walsmart.Models.Product;
import com.example.walsmart.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ProductActivity extends AppCompatActivity {
    int quantity = 0;
    public static RecyclerView products;
    private static ArrayList<Product> download_products = new ArrayList<>();
    private SearchView search_engine;
    private final ProductAdapter itemsAdapter = new ProductAdapter(R.layout.product_design, download_products);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Button basket = findViewById(R.id.basket_btn);
        basket.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BasketActivity.class);
            startActivity(intent);
        });
        products = findViewById(R.id.my_products);
        products.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        products.setItemAnimator(new DefaultItemAnimator());
        products.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        getProductsFromDatabase();

        //search
        search_engine = findViewById(R.id.search_product);
        search_engine.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                itemsAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                itemsAdapter.getFilter().filter(query);
                return false;
            }
        });

    }

    private void getProductsFromDatabase() {
        download_products.clear();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("products").orderByKey();
        ValueEventListener queryValueListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                for (DataSnapshot next : snapshotIterator) {
                    Product p = new Product(
                            Objects.requireNonNull(next.child("name").getValue()).toString(),
                            Objects.requireNonNull(next.child("photo").getValue()).toString(),
                            Objects.requireNonNull(next.child("size").getValue()).toString(),
                            Double.parseDouble(Objects.requireNonNull(next.child("price").getValue()).toString()));
                    download_products.add(p);
                    products.setAdapter(itemsAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        query.addListenerForSingleValueEvent(queryValueListener);
    }

    public void increaseInteger(View view) {
        if (quantity < 10) quantity++;
    }

    public void decreaseInteger(View view) {
        if (quantity > 0) quantity--;
    }
}
