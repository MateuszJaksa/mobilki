package com.example.walsmart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;

import java.lang.reflect.Array;

public class Order extends AppCompatActivity  implements OnItemSelectedListener {
    Spinner shops, cities;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Button back = (Button) findViewById(R.id.back_btn);
        Button submit = (Button) findViewById(R.id.submit_btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Basket.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Confirmation.class);
                startActivity(intent);
            }
        });


        shops = (Spinner) findViewById(R.id.shops_spinner);
        cities = (Spinner) findViewById(R.id.cities_spinner);
        cities.setOnItemSelectedListener(this);

         ArrayAdapter<CharSequence> cities_adapter = ArrayAdapter.createFromResource(this,
                R.array.cities_array, android.R.layout.simple_spinner_item);

        cities_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cities.setAdapter(cities_adapter);

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        int arr = 0;
        switch (item) {
            case "Warsaw":
                arr = R.array.shops_array_warsaw;
                break;
            case "Lodz":
                arr = R.array.shops_array_lodz;
                break;
            case "Torun":
                arr = R.array.shops_array_torun;
                break;
        }
        ArrayAdapter<CharSequence> shops_adapter = ArrayAdapter.createFromResource(this,
                arr, android.R.layout.simple_spinner_item);
        shops_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shops_adapter.notifyDataSetChanged();
        shops.setAdapter(shops_adapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}