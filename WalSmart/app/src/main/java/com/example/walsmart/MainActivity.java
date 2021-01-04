package com.example.walsmart;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.walsmart.Models.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // firebase do produktow
       /* StorageReference s = FirebaseStorage.getInstance().getReference();
        s.child("product_images/soup_greens.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                Log.d("DEBUG", "URL: " + url);
                addProduct("Soup greens", url, "1 unit", 9.89);
                //updateProduct("-MQDFU3Zxir-TYtrhiac", url);

                // biblioteka picasso do wyswietlania zdjec
                ImageView instruction = findViewById(R.id.instruction);
                Picasso.with(getApplicationContext()).load(url).into(instruction);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("DEBUG", "URL: error");
            }
        });*/

        StorageReference s = FirebaseStorage.getInstance().getReference();
        s.child("product_images/soup_greens.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                Log.d("DEBUG", "URL: " + url);
                addProduct("Soup greens", url, "1 unit", 9.89);
                //updateProduct("-MQDFU3Zxir-TYtrhiac", url);

                // biblioteka picasso do wyswietlania zdjec
                ImageView instruction = findViewById(R.id.instruction);
                Picasso.with(getApplicationContext()).load(url).into(instruction);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("DEBUG", "URL: error");
            }
        });


        Button start = (Button) findViewById(R.id.start_btn);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BasketActivity.class);
                startActivity(intent);
            }
        });
    }
    public void addProduct(String name, String photo, String size, double price) {
         Product product = new Product(name, photo, size, price);
         String id = mDatabase.push().getKey();
         mDatabase.child("products").child(id).setValue(product);
    }
    public void updateProduct(String id, String photo_url) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("products").child(id);
        Product product = new Product("Milk", photo_url, "1 liter", 2.59);
        dR.setValue(product);
        Toast.makeText(getApplicationContext(), "Artist Updated", Toast.LENGTH_LONG).show();
    }



}