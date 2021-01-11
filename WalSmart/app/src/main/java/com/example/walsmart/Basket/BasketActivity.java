package com.example.walsmart.Basket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.example.walsmart.Models.Basket;
import com.example.walsmart.Models.ProductRecord;
import com.example.walsmart.OrderActivity;
import com.example.walsmart.Product.ProductActivity;
import com.example.walsmart.ProductSet.ProductInSetAdapter;
import com.example.walsmart.ProductSet.ProductSetActivity;
import com.example.walsmart.R;
import com.example.walsmart.User.LogInActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class BasketActivity extends AppCompatActivity {

    public static RecyclerView products;
    private static final ArrayList<ProductRecord> basket_products = new ArrayList<>();
    private final ProductInSetAdapter itemsAdapter = new ProductInSetAdapter(R.layout.product_set_design, basket_products);
    private ImageView instruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Button sets_btn = findViewById(R.id.sets_btn);
        Button products_btn = findViewById(R.id.products_btn);
        Button my_sets_btn = findViewById(R.id.my_sets);
        Button checkout_btn = findViewById(R.id.checkout_btn);
        instruction = findViewById(R.id.instruction);
        products = findViewById(R.id.my_basket);
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
            intent.putExtra("adapter_type", "Standard Sets");
            startActivity(intent);
        });

        my_sets_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProductSetActivity.class);
            intent.putExtra("adapter_type", "My Sets");
            startActivity(intent);
        });

        products_btn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            new AlertDialog.Builder(this)
                    .setTitle("About application")
                    .setMessage(getString(R.string.authors))
                    .setNegativeButton(android.R.string.ok, null).setIcon(getDrawable(R.drawable.grocery))
                    .show();
        } else if (id == R.id.action_log_out) {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
            Basket.clear();
            Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadBasket() {
        basket_products.clear();
        basket_products.addAll(Basket.getProducts());
        if (basket_products.isEmpty()) {
            instruction.setAlpha(1.0f);
        } else {
            instruction.setAlpha(0.0f);
            products.setAdapter(itemsAdapter);
        }
    }
}