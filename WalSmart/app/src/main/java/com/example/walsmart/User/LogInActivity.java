package com.example.walsmart.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.walsmart.MainActivity;
import com.example.walsmart.R;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    private FirebaseAuth firebase_auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebase_auth = FirebaseAuth.getInstance();
        if (firebase_auth.getCurrentUser() != null) {
            startActivity(new Intent(LogInActivity.this, MainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_log_in);
        Button reset_password_btn = findViewById(R.id.reset_button);
        Button log_in_btn = findViewById(R.id.login_button);
        Button sign_up_btn = findViewById(R.id.btn_sign_up);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        firebase_auth = FirebaseAuth.getInstance();
        sign_up_btn.setOnClickListener(v -> LogInActivity.this.startActivity(new Intent(LogInActivity.this, SignUpActivity.class)));

        reset_password_btn.setOnClickListener(v -> LogInActivity.this.startActivity(new Intent(LogInActivity.this, ResetActivity.class)));

        log_in_btn.setOnClickListener(v -> {
            String user_email = email.getText().toString();
            String user_password = password.getText().toString();

            if (TextUtils.isEmpty(user_email)) {
                Toast.makeText(LogInActivity.this.getApplicationContext(), "enter email address", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(user_password)) {
                Toast.makeText(LogInActivity.this.getApplicationContext(), "enter password", Toast.LENGTH_SHORT).show();
                return;
            }

            firebase_auth.signInWithEmailAndPassword(user_email, user_password)
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            if (user_password.length() < 6) {
                                password.setError("Password has to have at least 6 characters");
                            } else {
                                Toast.makeText(LogInActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            LogInActivity.this.startActivity(new Intent(LogInActivity.this, MainActivity.class));
                            LogInActivity.this.finish();
                        }
                    });
        });
    }
}