package com.example.walsmart.ProductSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.walsmart.Models.Basket;
import com.example.walsmart.Models.ProductSet;
import com.example.walsmart.Models.Product;
import com.example.walsmart.Order.MyOrdersActivity;
import com.example.walsmart.Product.ProductAdapter;
import com.example.walsmart.R;
import com.example.walsmart.User.LogInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateSetActivity extends AppCompatActivity {

    public static final int CAMERA_PERMISSION_CODE = 800;
    public static final int CAMERA_REQUEST_CODE = 801;
    Button cancel_btn, create_btn, attach_image;
    EditText set_name;
    ImageView image;
    private FirebaseAuth firebase_auth;
    public static RecyclerView products;
    private static ArrayList<Product> download_products = new ArrayList<>();
    private final ProductAdapter itemsAdapter = new ProductAdapter(R.layout.product_design, download_products, "Product Set");
    // tworzenie listy statycznej
    public static List<Product> staticProductList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_set);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        Bundle extras = getIntent().getExtras();
        String set_type = (String) extras.get("set_type");
        setSupportActionBar(myToolbar);
        staticProductList.clear(); // zacznij tworzenie setu z pusta lista
        firebase_auth = FirebaseAuth.getInstance();
        set_name = findViewById(R.id.set_name);
        create_btn = findViewById(R.id.create_btn);
        create_btn.setOnClickListener(v -> {
            createCustomSet();
            Intent intent = new Intent(getApplicationContext(), ProductSetActivity.class);
            intent.putExtra("set_type", set_type);
            startActivity(intent);
        });
        cancel_btn = findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(v -> {
            finish();
        });
        image = findViewById(R.id.photo);
        attach_image = findViewById(R.id.attach_photo);
        attach_image.setOnClickListener(v -> {
            askPermission();
        });


        products = findViewById(R.id.my_product_sets);
        products.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        products.setItemAnimator(new DefaultItemAnimator());
        products.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        getProductsFromDatabase();

        //search
        SearchView search_engine = findViewById(R.id.search_product_set);
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

    private void askPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            launchCamera();
        }
    }

    private void launchCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            Bitmap imagebtmp = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(imagebtmp);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length < 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchCamera();
            } else {
                Toast.makeText(this, "You need to allow for camera usage", Toast.LENGTH_SHORT).show();
            }
        }
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
        } else if (id == R.id.action_my_orders) {
            Intent intent = new Intent(getApplicationContext(), MyOrdersActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getProductsFromDatabase() {
        download_products.clear();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child("products").orderByKey();
        ValueEventListener queryValueListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                for (DataSnapshot next : snapshotIterator) {
                    Product p = new Product(
                            Objects.requireNonNull(next.child("name").getValue()).toString(),
                            Objects.requireNonNull(next.child("photo").getValue()).toString(),
                            Objects.requireNonNull(next.child("size").getValue()).toString(),
                            Double.parseDouble(Objects.requireNonNull(next.child("price").getValue()).toString()));
                    download_products.add(p);
                    products.setAdapter(itemsAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        query.addListenerForSingleValueEvent(queryValueListener);
    }

    public void createCustomSet() {
        // tworzenie setu i dodanie go do bazy danych
        StorageReference s = FirebaseStorage.getInstance().getReference();
        s.child("set_images/delivery_box.png").getDownloadUrl().addOnSuccessListener(uri -> {
            String photo = uri.toString();
            String userId = firebase_auth.getCurrentUser().getUid();
            //String userId = "standard";
            String name = set_name.getText().toString();
            double totalPrice = 0.0;
            ArrayList<Product> products = new ArrayList<>(staticProductList);
            for (Product p : products) {
                totalPrice += p.getPrice();
            }
            ProductSet productSet = new ProductSet(userId, name, photo, products, totalPrice);
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            String id = mDatabase.push().getKey();
            mDatabase.child("custom_sets").child(id).setValue(productSet);
        }).addOnFailureListener(exception -> Log.d("DEBUG", "URL: error"));

    }
}