package com.example.walsmart;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

public class ProductActivity extends AppCompatActivity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Button basket = findViewById(R.id.basket_btn);
        basket.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BasketActivity.class);
            startActivity(intent);
        });

        SearchView searchView = findViewById(R.id.search_product);
        CharSequence query = searchView.getQuery();

    }

    public void increaseInteger(View view) {
        if (quantity < 10) quantity++;
        display(quantity);
    }

    public void decreaseInteger(View view) {
        if (quantity > 0) quantity--;
        display(quantity);
    }

    @SuppressLint("SetTextI18n")
    private void display(int number) {
        TextView displayInteger = (TextView) findViewById(R.id.integer_number);
        displayInteger.setText("" + number);
    }
}
