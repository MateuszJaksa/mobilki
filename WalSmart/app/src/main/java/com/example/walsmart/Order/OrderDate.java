package com.example.walsmart.Order;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.walsmart.ConfirmationActivity;
import com.example.walsmart.R;

import org.w3c.dom.Text;

import java.sql.Struct;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class OrderDate extends AppCompatActivity {
    private boolean isPicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_date);

        TextView date = findViewById(R.id.date);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String dateString = sdf.format(new Date());
        date.setText(dateString);
        TextView time = findViewById(R.id.time);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
        String timeString = sdf2.format(new Date());
        time.setText(timeString);

        Button set_date = findViewById(R.id.set_date);
        set_date.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            DatePicker picker = new DatePicker(this);
            picker.setMinDate(System.currentTimeMillis() - 1000);
            builder.setView(picker);
            builder.setNegativeButton("Cancel", null);
            builder.setPositiveButton("Set", (dialog, which) -> {
                StringBuilder chosen_date_string = new StringBuilder();
                chosen_date_string.append(picker.getDayOfMonth()).append(".");
                if (picker.getMonth() < 9)
                    chosen_date_string.append("0").append(picker.getMonth() + 1).append(".");
                else chosen_date_string.append(picker.getMonth() + 1).append(".");
                chosen_date_string.append(picker.getYear());
                date.setText(chosen_date_string);
                isPicked = true;
            });
            AlertDialog dialog = builder.create();
            dialog.setOnShowListener(arg0 -> {
                dialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#C6FF6F00"));
                dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#C6FF6F00"));
            });
            dialog.show();
        });
        Button set_time = findViewById(R.id.set_time);
        set_time.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            TimePicker picker_time = new TimePicker(this);
            builder.setView(picker_time);
            builder.setNegativeButton("Cancel", null);
            builder.setPositiveButton("Set", (dialog, which) -> {
                String chosen_time_string = picker_time.getHour() + ":" +
                        picker_time.getMinute();
                Date curr = new Date();
                if (date.getText().equals(dateString) && picker_time.getHour() <= curr.getHours()) {
                    Toast.makeText(this, "You can pick order up at least 1 hour after placing the order", Toast.LENGTH_SHORT).show();
                } else {
                    time.setText(chosen_time_string);
                    isPicked = true;
                }
            });
            AlertDialog dialog = builder.create();
            dialog.setOnShowListener(arg0 -> {
                dialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#C6FF6F00"));
                dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#C6FF6F00"));
            });
            dialog.show();
        });

        Button set = findViewById(R.id.date_time_set);
        set.setOnClickListener(v -> {
            if (isPicked) {
                Intent intent = new Intent(this, OrderActivity.class);
                String finalDate = date.getText() + " " +
                        time.getText();
                intent.putExtra("pickup_date", finalDate);
                startActivity(intent);
            } else {
                Toast.makeText(this, "You can pick order up at least 1 hour after placing the order", Toast.LENGTH_SHORT).show();
            }

        });

    }
}