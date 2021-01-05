package com.example.walsmart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.walsmart.Product.ProductActivity;

public class BasketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        Button sets = (Button)findViewById(R.id.sets_btn);
        Button products = (Button)findViewById(R.id.products_btn);
        Button checkout = (Button)findViewById(R.id.checkout_btn);

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
}