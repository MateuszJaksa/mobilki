package com.example.walsmart.Basket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.walsmart.Models.Basket;
import com.example.walsmart.Models.Product;
import com.example.walsmart.OrderActivity;
import com.example.walsmart.Product.ProductActivity;
import com.example.walsmart.ProductSet.ProductSetActivity;
import com.example.walsmart.R;

import java.util.ArrayList;

public class BasketActivity extends AppCompatActivity {

    public static RecyclerView products;
    private static ArrayList<Product> basket_products = new ArrayList<>();
    private final BasketAdapter itemsAdapter = new BasketAdapter(R.layout.product_design, basket_products);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        Button sets = (Button)findViewById(R.id.sets_btn);
        Button products = (Button)findViewById(R.id.products_btn);
        Button checkout = (Button)findViewById(R.id.checkout_btn);

        RecyclerView basketContent = (RecyclerView) findViewById(R.id.my_basket);
        basketContent.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        basketContent.setItemAnimator(new DefaultItemAnimator());
        basketContent.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        loadBasket();

        checkout.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
            startActivity(intent);
        });

        sets.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProductSetActivity.class);
            startActivity(intent);
        });

        products.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
            startActivity(intent);
        });
    }

    private void loadBasket() {
        for (Product product : Basket.getProducts()) {
            basket_products.add(product);
            products.setAdapter(itemsAdapter);
        }
    }
}