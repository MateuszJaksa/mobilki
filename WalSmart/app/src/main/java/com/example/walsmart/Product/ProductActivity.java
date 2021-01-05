package com.example.walsmart.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    int quantity = 0;
    public static RecyclerView products;
    private static ArrayList<Product> download_products = new ArrayList<>();
    private SearchView search_engine;
    private ProductAdapter itemArrayAdapter = new ProductAdapter(R.layout.product_design, download_products);

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
        getProductsFromDatabase();

        //search
        search_engine = findViewById(R.id.search_product);
        /*search_engine.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                itemArrayAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                itemArrayAdapter.getFilter().filter(query);
                return false;
            }
        });*/

    }

    private void getProductsFromDatabase() {
        download_products.clear();
       /* for (Product t : *//*no z jakiejs listy produktow sciagnietych z bazy*//*)
            download_products.add(t);*/

        products.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        products.setItemAnimator(new DefaultItemAnimator());
        products.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        products.setAdapter(itemArrayAdapter);
        ;
    }

    public void increaseInteger(View view) {
        if (quantity < 10) quantity++;
    }

    public void decreaseInteger(View view) {
        if (quantity > 0) quantity--;
    }
}
