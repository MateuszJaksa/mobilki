package com.example.walsmart.ProductSet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.walsmart.Models.Product;
import com.example.walsmart.Models.ProductSet;
import com.example.walsmart.R;

public class EditSet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_set);
        Bundle extras = getIntent().getExtras();
        ProductSet set = (ProductSet) extras.get("product_set");

    }
}