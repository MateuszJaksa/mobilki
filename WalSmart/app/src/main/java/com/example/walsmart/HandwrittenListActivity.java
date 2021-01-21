package com.example.walsmart;

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
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.walsmart.Models.Basket;
import com.example.walsmart.Models.Product;
import com.example.walsmart.Models.ProductRecord;
import com.example.walsmart.Models.Stock;
import com.example.walsmart.ProductSet.ProductInSetAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.util.ArrayList;

public class HandwrittenListActivity extends AppCompatActivity {
    public static final int CAMERA_PERMISSION_CODE = 800;
    public static final int CAMERA_REQUEST_CODE = 801;
    Button cameraBtn, addBtn;
    Bitmap handwrittenList;
    public static RecyclerView products;
    private static ArrayList<ProductRecord> download_products = new ArrayList<>();
    private final ProductInSetAdapter itemsAdapter = new ProductInSetAdapter(R.layout.product_set_design, download_products);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handwritten_list);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        cameraBtn = findViewById(R.id.cameraButton);
        cameraBtn.setOnClickListener(view -> {
            askPermission();
        });

        addBtn = findViewById(R.id.add_btn);
        addBtn.setOnClickListener(view -> {
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
            handwrittenList = (Bitmap) data.getExtras().get("data");
            detectImg();
        }
    }

    private void detectImg() {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(handwrittenList);
        FirebaseVisionTextRecognizer textRecognizer =
                FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        textRecognizer.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                processText(firebaseVisionText);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Unable to detect text. Try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void processText(FirebaseVisionText firebaseVisionText) {
        // names of products from the picture
        ArrayList<String> names = new ArrayList<>();
        for (FirebaseVisionText.TextBlock tb : firebaseVisionText.getTextBlocks()) {
            tb.getText();
            for (FirebaseVisionText.Line l : tb.getLines()) {
                names.add(l.getText());
            }
        }
        // find those products in Stock
        download_products.clear();
        StringBuilder notFoundProducts = new StringBuilder();
        boolean isFound;
        for (String n : names) {
            isFound = false;
            for (Product p : Stock.getProducts()) {
                if (p.getName().toLowerCase().equals(n.toLowerCase())) {
                    ProductRecord pr = new ProductRecord(p, 1);
                    download_products.add(pr);
                    isFound = true;
                }
            }
            if (!isFound) {
                notFoundProducts.append(n).append('\n');
            }
        }
        products = findViewById(R.id.set_products);
        products.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        products.setItemAnimator(new DefaultItemAnimator());
        products.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        products.setAdapter(itemsAdapter);

        // not found products
        if (!notFoundProducts.toString().isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Sorry! We couldn't find these products:")
                    .setMessage(notFoundProducts.toString())
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(R.drawable.clover); // change!!!!!!!!!!!!!!!!!!!!!!!!!!
            AlertDialog dialog = builder.create();
            dialog.setOnShowListener(arg0 -> {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#C6FF6F00"));
            });
            dialog.show();
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
}