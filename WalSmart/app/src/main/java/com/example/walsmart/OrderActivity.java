package com.example.walsmart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.walsmart.Models.Order;
import com.example.walsmart.User.LogInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OrderActivity extends AppCompatActivity  implements OnItemSelectedListener {
    private final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private static final DecimalFormat df2 = new DecimalFormat("#.##");
    Spinner shops, cities;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Button back = (Button) findViewById(R.id.back_btn);
        Button submit = (Button) findViewById(R.id.submit_btn);

        EditText phoneNumber = (EditText) findViewById(R.id.editTextPhone);

        TextView priceTextView = (TextView) findViewById(R.id.priceTextView);

        priceTextView.setText(getResources().getString(R.string.total_price) + df2.format(Basket.getTotalPrice()) + " PLN");

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
                    Date d = Calendar.getInstance().getTime();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    String dateString = sdf.format(new Date());
                    Order order = new Order(Basket.getProducts(), Basket.getTotalPrice(), phoneNumber.getText().toString(), dateString, cities.getSelectedItem().toString(), shops.getSelectedItem().toString());
                    String id = mDatabase.push().getKey();
                    mDatabase.child("orders").child(id).setValue(order);

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
                    .setTitle(getString(R.string.authors))
                    .setNegativeButton(android.R.string.ok, null).setIcon(getDrawable(R.drawable.grocery))
                    .show();
        } else if (id == R.id.action_log_out) {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();
            Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}