/*
* TODO
  * usuwanie z koszyka sie odswieza i nie zapisuje (edit: chyba dziala ale moze lepiej do koszyka ten osobny adapter uzyc
* cena ogolna set i basket zeby sie aktualizowala
* cena double w bazie do zaokraglenia
* dodawanie setow!
* praca nad optymalizacja (np create set i product activities sa takie same prawie)
* Custom set i Product set jest balagan bo to to samo
* */

package com.example.walsmart;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;
import android.widget.Toast;

import com.example.walsmart.Basket.BasketActivity;
import com.example.walsmart.Models.Product;
import com.example.walsmart.Models.ProductSet;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // firebase do produktow
        /*StorageReference s = FirebaseStorage.getInstance().getReference();
        s.child("product_images/wild_garlic.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                Log.d("DEBUG", "URL: " + url);
                addProduct("Wild garlic", url, "1 unit", 4.22);
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

        // firebase do setów produktow
        /*
        List<String> ids = new ArrayList<>();
        //ids.add("-MQDZSex4Km6fFKGzgY-"); // eggs
        ids.add("-MQDZjnLgGrc0a46QADJ"); // milk
        //ids.add("-MQDikbjy-nIe92mnWVC"); // sugar
        ids.add("-MQDnzw4WbJEnBKI38Nk"); // butter
        //ids.add("-MQHzkwXG5SPdAox8L91"); // flour
        //ids.add("-MQDmLmR8JskqwS6ZWcq"); // pasta
        ids.add("-MQDn-CTrY__fIf3OubO"); // rice
        ids.add("-MQDnPFeICXXEQtw8A6-"); // chicken
        //ids.add("-MQDokn92pOf65jRDMSZ"); // cheese
        //ids.add("-MQDpKmXbKzgLrzA6kts"); // ground meat
        ids.add("-MQDxSKTi-lHWUQ3T60m"); // spinach
        //ids.add("-MQDxqMy758AaN7euHVN"); // tomato paste
        //ids.add("-MQDyPcuN9kXS7OecZzY"); // pumpkin
        //ids.add("-MQDz8PttUUNSKPB1AMO"); // soup greens

      //  ProductSet chocolate_cake = new ProductSet("Chocolate cake", null, ids);
      //  Log.d("Debug", "Msg: total --> " + chocolate_cake.getTotalPrice());

        StorageReference s = FirebaseStorage.getInstance().getReference();
        s.child("set_images/chicken_with_spinach.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                Log.d("DEBUG", "URL: " + url);

                addProductSet("Chicken with spinach", url, ids);
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


        Button start = (Button) findViewById(R.id.start_btn);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BasketActivity.class);
                startActivity(intent);
            }
        });
    }

    //metody do firebase
    public void addProduct(String name, String photo, String size, double price) {
         Product product = new Product(name, photo, size, price);
         String id = mDatabase.push().getKey();
         mDatabase.child("products").child(id).setValue(product);
    }
    public void addProductSet(String name, String photo_url, List<String> products) {
        ProductSet productSet = new ProductSet(name, photo_url, products);
        String id = mDatabase.push().getKey();
        mDatabase.child("product_sets").child(id).setValue(productSet);
    }
    public void updateProduct(String id, String photo_url) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("products").child(id);
        Product product = new Product("Milk", photo_url, "1 liter", 2.59);
        dR.setValue(product);
        Toast.makeText(getApplicationContext(), "Artist Updated", Toast.LENGTH_LONG).show();
    }
}