package com.example.walsmart;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.walsmart.Basket.BasketActivity;
import com.example.walsmart.Models.Basket;

public class OrderActivity extends AppCompatActivity  implements OnItemSelectedListener {
    Spinner shops, cities;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Button back = (Button) findViewById(R.id.back_btn);
        Button submit = (Button) findViewById(R.id.submit_btn);

        EditText phoneNumber = (EditText) findViewById(R.id.editTextPhone);

        TextView priceTextView = (TextView) findViewById(R.id.priceTextView);

        priceTextView.setText(getResources().getString(R.string.total_price) + Basket.getTotalPrice() + " PLN");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BasketActivity.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = phoneNumber.getText().toString();
                if (!input.matches("^(\\d{3}[- ]?){2}\\d{3}$")) {
                    Toast.makeText(getApplicationContext(), "That is not a correct phone number", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), ConfirmationActivity.class);
                    startActivity(intent);
                }
            }
        });


        shops = (Spinner) findViewById(R.id.shops_spinner);
        cities = (Spinner) findViewById(R.id.cities_spinner);
        cities.setOnItemSelectedListener(this);

         ArrayAdapter<CharSequence> cities_adapter = ArrayAdapter.createFromResource(this,
                R.array.cities_array, R.layout.my_spinner);
        cities_adapter.setDropDownViewResource(R.layout.my_spinner);
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
                arr, R.layout.my_spinner);
        shops_adapter.setDropDownViewResource(R.layout.my_spinner);
        shops_adapter.notifyDataSetChanged();
        shops.setAdapter(shops_adapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}