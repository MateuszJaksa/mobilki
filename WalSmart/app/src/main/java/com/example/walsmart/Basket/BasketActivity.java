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
import com.example.walsmart.Models.ProductRecord;
import com.example.walsmart.OrderActivity;
import com.example.walsmart.Product.ProductActivity;
import com.example.walsmart.ProductSet.ProductInSetAdapter;
import com.example.walsmart.ProductSet.ProductSetActivity;
import com.example.walsmart.R;

import java.util.ArrayList;

public class BasketActivity extends AppCompatActivity {

    public static RecyclerView products;
    private static final ArrayList<ProductRecord> basket_products = new ArrayList<>();
    private final ProductInSetAdapter itemsAdapter = new ProductInSetAdapter(R.layout.product_set_design, basket_products);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        Button sets_btn = (Button)findViewById(R.id.sets_btn);
        Button products_btn = (Button)findViewById(R.id.products_btn);
        Button checkout_btn = (Button)findViewById(R.id.checkout_btn);

        products = (RecyclerView) findViewById(R.id.my_basket);
        products.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        products.setItemAnimator(new DefaultItemAnimator());
        products.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        loadBasket();

        checkout_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
            startActivity(intent);
        });

        sets_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProductSetActivity.class);
            startActivity(intent);
        });

        products_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
            startActivity(intent);
        });
    }

    private void loadBasket() {
        basket_products.clear();
        basket_products.addAll(Basket.getProducts());
        products.setAdapter(itemsAdapter);
    }
}