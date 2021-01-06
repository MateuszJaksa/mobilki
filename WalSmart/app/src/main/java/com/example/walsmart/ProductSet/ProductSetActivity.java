package com.example.walsmart.ProductSet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SearchView;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductSetActivity extends AppCompatActivity {

    public static RecyclerView sets;
    private static ArrayList<ProductSet> download_sets = new ArrayList<>();
    private SearchView search_engine;
    private final ProductSetAdapter itemsAdapter = new ProductSetAdapter(R.layout.product_design, download_sets);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_sets);

        Button basket = findViewById(R.id.basket_btn_sets);
        basket.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BasketActivity.class);
            startActivity(intent);
        });
        sets = findViewById(R.id.my_product_sets);
        sets.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        sets.setItemAnimator(new DefaultItemAnimator());
        sets.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        getProductSetsFromDatabase();

        //search
        search_engine = findViewById(R.id.search_product_set);
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

    private void getProductSetsFromDatabase() {
        download_sets.clear();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("product_sets").orderByKey();
        ValueEventListener queryValueListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                for (DataSnapshot next : snapshotIterator) {
                    Iterable<DataSnapshot> productsIterator = next.child("products").getChildren();
                    List<String> products = new ArrayList<>();
                    for(DataSnapshot next_product : productsIterator) {
                        products.add(Objects.requireNonNull(next_product.getValue()).toString());
                    }
                    ProductSet ps = new ProductSet(
                            Objects.requireNonNull(next.child("name").getValue()).toString(),
                            Objects.requireNonNull(next.child("photo").getValue()).toString(),
                            products,
                            Double.parseDouble(Objects.requireNonNull(next.child("totalPrice").getValue()).toString()));
                    Log.d("Debug", "Msg: " + products.get(1));
                    download_sets.add(ps);
                    sets.setAdapter(itemsAdapter);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        query.addListenerForSingleValueEvent(queryValueListener);
    }
}
