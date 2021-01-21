package com.example.walsmart.ProductSet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.walsmart.BasketActivity;
import com.example.walsmart.Models.Basket;
import com.example.walsmart.Models.ProductSet;
import com.example.walsmart.Models.Product;
import com.example.walsmart.Models.ProductRecord;
import com.example.walsmart.Order.MyOrdersActivity;
import com.example.walsmart.R;
import com.example.walsmart.User.LogInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class EditSet extends AppCompatActivity {
    public static RecyclerView products;
    private static ArrayList<ProductRecord> download_products = new ArrayList<>();
    private final ProductInSetAdapter itemsAdapter = new ProductInSetAdapter(R.layout.product_set_design, download_products);

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_set);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Bundle extras = getIntent().getExtras();
        ProductSet set = (ProductSet) extras.get("custom_set");
        ArrayList<Product> products_list = extras.getParcelableArrayList("custom_set_products");
        Log.d("Debug", "Msg: set " + set.getName());
        products = findViewById(R.id.set_products);
        products.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        products.setItemAnimator(new DefaultItemAnimator());
        products.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        convertProductsToProductRecords(products_list);

        ImageView img = findViewById(R.id.set_image);
        Picasso.get().load(set.getPhoto()).into(img);
        TextView setName = findViewById(R.id.set_name);
        setName.setText(set.getName());
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        nf.setMaximumFractionDigits(2);
        DecimalFormat df = (DecimalFormat) nf;
        TextView setPrice = findViewById(R.id.set_price);
        setPrice.setText("PLN " + df.format(set.getTotalPrice()));
        Button addAndReturnToBasket = findViewById(R.id.add_btn_sets);
        addAndReturnToBasket.setOnClickListener(v -> {
            for (ProductRecord p : download_products) {
                boolean isInBasket = false;
                for (ProductRecord inBasket : Basket.getProducts()) {
                    if (p.getProduct().getName().equals(inBasket.getProduct().getName())) {
                        inBasket.setAmount(inBasket.getAmount() + p.getAmount());
                        isInBasket = true;
                    }
                }
                if (!isInBasket) {
                    Basket.addProductRecord(p);
                }
            }
            Intent intent = new Intent(getApplicationContext(), BasketActivity.class);
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
        } else if(id == R.id.action_my_orders) {
            Intent intent = new Intent(getApplicationContext(), MyOrdersActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void convertProductsToProductRecords(ArrayList<Product> list) {
        download_products.clear();
        boolean wasAdded;
        for (Product p : list) {
            wasAdded = false;
            for (ProductRecord p1 : download_products) {
                if (p1.getProduct().getName().equals(p.getName())) {
                    p1.increase();
                    wasAdded = true;
                }
            }
            if (!wasAdded) {
                ProductRecord pr = new ProductRecord(p, 1);
                download_products.add(pr);
            }
        }
        products.setAdapter(itemsAdapter);
    }
}
