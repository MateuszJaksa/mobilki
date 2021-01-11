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
import android.widget.SearchView;

import com.example.walsmart.Basket.BasketActivity;
import com.example.walsmart.Models.Basket;
import com.example.walsmart.Models.CustomSet;
import com.example.walsmart.Models.ProductSet;
import com.example.walsmart.R;
import com.example.walsmart.User.LogInActivity;
import com.google.firebase.auth.FirebaseAuth;
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
   // private static ArrayList<ProductSet> download_sets = new ArrayList<>();
    private static ArrayList<CustomSet> download_my_sets = new ArrayList<>();
    private SearchView search_engine;
   // private final ProductSetAdapter standardAdapter = new ProductSetAdapter(R.layout.product_design, download_sets);
    private final CustomSetAdapter customAdapter = new CustomSetAdapter(R.layout.product_design, download_my_sets);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_sets);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        // wybor adaptera setow
        Bundle extras = getIntent().getExtras();
        String set_type = (String) extras.get("set_type");

        Button basket = findViewById(R.id.basket_btn_sets);
        basket.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BasketActivity.class);
            startActivity(intent);
        });
        Button create_set = findViewById(R.id.create_set);
        create_set.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), CreateSetActivity.class);
            intent.putExtra("set_type", set_type);
            startActivity(intent);
        });

        sets = findViewById(R.id.my_product_sets);
        sets.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        sets.setItemAnimator(new DefaultItemAnimator());
        sets.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        getCustomSetsFromDatabase(set_type);

        //search
        search_engine = findViewById(R.id.search_product_set);
        search_engine.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                customAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                 customAdapter.getFilter().filter(query);
                return false;
            }
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

  /*  private void getProductSetsFromDatabase() {
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
                    for (DataSnapshot next_product : productsIterator) {
                        products.add(Objects.requireNonNull(next_product.getValue()).toString());
                    }
                    ProductSet ps = new ProductSet(
                            Objects.requireNonNull(next.child("name").getValue()).toString(),
                            Objects.requireNonNull(next.child("photo").getValue()).toString(),
                            products,
                            Double.parseDouble(Objects.requireNonNull(next.child("totalPrice").getValue()).toString()));
                    Log.d("Debug", "Msg: " + products.get(1));
                    download_sets.add(ps);
                    sets.setAdapter(standardAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        query.addListenerForSingleValueEvent(queryValueListener);
    }*/

    private void getCustomSetsFromDatabase(String set_type) {
        download_my_sets.clear();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth firebase_auth = FirebaseAuth.getInstance();
        String user_id = null;
        if(set_type.equals("Standard Sets")) user_id = "standard";
        else if(set_type.equals("My Sets")) user_id = firebase_auth.getCurrentUser().getUid();
        Query query = mDatabase.child("custom_sets").orderByChild("userId").equalTo(user_id);
        ValueEventListener queryValueListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                for (DataSnapshot next : snapshotIterator) {
                    download_my_sets.add(next.getValue(CustomSet.class));
                    sets.setAdapter(customAdapter);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        query.addListenerForSingleValueEvent(queryValueListener);
    }
}
