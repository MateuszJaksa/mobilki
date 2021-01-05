package com.example.walsmart.ProductSet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.walsmart.BasketActivity;
import com.example.walsmart.Models.Product;
import com.example.walsmart.Models.ProductSet;
import com.example.walsmart.Product.ProductAdapter;
import com.example.walsmart.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class EditSet extends AppCompatActivity {
    public static RecyclerView products;
    private static ArrayList<Product> download_products = new ArrayList<>();
    private final ProductInSetAdapter itemsAdapter = new ProductInSetAdapter(R.layout.product_set_design, download_products);

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_set);

        Bundle extras = getIntent().getExtras();
        ProductSet set = (ProductSet) extras.get("product_set");
        ImageView img = findViewById(R.id.set_image);
        Picasso.with(getApplicationContext()).load(set.getPhoto()).into(img);
        TextView setName = findViewById(R.id.set_name);
        setName.setText(set.getName());
        TextView setPrice = findViewById(R.id.set_price);
        setPrice.setText("PLN " + set.getTotalPrice());

        Button addAndReturnToBasket = findViewById(R.id.add_btn_sets);
        addAndReturnToBasket.setOnClickListener(v -> {
            // dodanie product setu do koszyka
            Intent intent = new Intent(getApplicationContext(), BasketActivity.class);
            startActivity(intent);
        });

        products = findViewById(R.id.set_products);
        products.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        products.setItemAnimator(new DefaultItemAnimator());
        products.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        getProductsFromDatabase(set);
    }

    private void getProductsFromDatabase(ProductSet set) {
        download_products.clear();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        for (String key : set.getProducts()) {
            mDatabase.child(String.format("products/%s", key)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Product p = dataSnapshot.getValue(Product.class);
                    download_products.add(p);
                    products.setAdapter(itemsAdapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        }
    }
}