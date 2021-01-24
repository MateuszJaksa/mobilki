package com.example.walsmart.User;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.walsmart.BasketActivity;
import com.example.walsmart.R;
import com.google.firebase.auth.FirebaseAuth;


public class SignUpActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private FirebaseAuth firebase_auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Button sign_up_btn = findViewById(R.id.sign_up_button);
        Button log_in_btn = findViewById(R.id.sign_in_button);
        firebase_auth = FirebaseAuth.getInstance();
        log_in_btn.setOnClickListener(v -> SignUpActivity.this.finish());
        sign_up_btn.setOnClickListener(v -> SignUpActivity.this.registerNewUser());
    }

    private void registerNewUser() {
        String user_email = email.getText().toString().trim();
        String user_password = password.getText().toString().trim();

        if (TextUtils.isEmpty(user_email)) {
            Toast("Enter email address");
            return;
        }
        if (TextUtils.isEmpty(user_password)) {
            Toast("Enter password");
            return;
        }
        if (user_password.length() < 6) {
            Toast("Password has to have at least 6 characters");
            return;
        }

        firebase_auth.createUserWithEmailAndPassword(user_email, user_password)
                .addOnCompleteListener(SignUpActivity.this, task -> {
                    if (!task.isSuccessful()) {
                        SignUpActivity.this.Toast("Authentication failed" + task.getException());
                    } else {
                        SignUpActivity.this.startActivity(new Intent(SignUpActivity.this, BasketActivity.class));
                        SignUpActivity.this.finish();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void Toast(String toast_text) {
        Toast.makeText(this, toast_text, Toast.LENGTH_SHORT).show();
    }

}