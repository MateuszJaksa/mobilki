package com.example.walsmart.User;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.walsmart.R;
import com.google.firebase.auth.FirebaseAuth;

public class ResetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        Button reset_password_btn = findViewById(R.id.btn_reset_password);
        Button back_btn = findViewById(R.id.btn_back);
        EditText email = findViewById(R.id.email);
        FirebaseAuth firebase_auth = FirebaseAuth.getInstance();

        back_btn.setOnClickListener(v -> ResetActivity.this.finish());

        reset_password_btn.setOnClickListener(v -> {
            String user_email = email.getText().toString().trim();
            if (TextUtils.isEmpty(user_email)) {
                Toast.makeText(ResetActivity.this, "Enter correct e-mail", Toast.LENGTH_SHORT).show();
                return;
            }
            firebase_auth.sendPasswordResetEmail(user_email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetActivity.this, "We've sent you confirmation email", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ResetActivity.this, "Incorrect email", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}