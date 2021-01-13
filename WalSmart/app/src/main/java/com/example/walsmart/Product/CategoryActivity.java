package com.example.walsmart.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.walsmart.BasketActivity;
import com.example.walsmart.ProductSet.ProductSetActivity;
import com.example.walsmart.R;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        Button basket = findViewById(R.id.basket_btn_sets);
        basket.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BasketActivity.class);
            startActivity(intent);
        });
        Button grains = findViewById(R.id.grains);
        grains.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
            intent.putExtra("category", "grains");
            startActivity(intent);
        });
        Button proteins = findViewById(R.id.proteins);
        proteins.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
            intent.putExtra("category", "proteins");
            startActivity(intent);
        });
        Button dairy = findViewById(R.id.dairy);
        dairy.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
            intent.putExtra("category", "dairy");
            startActivity(intent);
        });
        Button fruits = findViewById(R.id.fruits);
        fruits.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
            intent.putExtra("category", "fruits");
            startActivity(intent);
        });
        Button vegetables = findViewById(R.id.vegetables);
        vegetables.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
            intent.putExtra("category", "vegetables");
            startActivity(intent);
        });
        Button beverages = findViewById(R.id.beverages);
        beverages.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
            intent.putExtra("category", "beverages");
            startActivity(intent);
        });
        Button sweets = findViewById(R.id.sweets);
        sweets.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
            intent.putExtra("category", "sweets");
            startActivity(intent);
        });
        Button spices = findViewById(R.id.spices);
        spices.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
            intent.putExtra("category", "spices");
            startActivity(intent);
        });
        Button animal_food = findViewById(R.id.animal_food);
        animal_food.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
            intent.putExtra("category", "animal food");
            startActivity(intent);
        });
    }
}