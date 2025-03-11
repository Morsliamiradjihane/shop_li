package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.example.myapplication.data.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private MaterialButton loginButton;
    private MaterialButton signupButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Initialize views
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupButton);

        // Set click listeners
        loginButton.setOnClickListener(v -> attemptLogin());
        signupButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        // For testing: Display dummy user credentials
        Toast.makeText(this, "Test User: john@example.com / password123", Toast.LENGTH_LONG).show();
    }

    private void attemptLogin() {
        // Reset errors
        emailLayout.setError(null);
        passwordLayout.setError(null);

        // Get values
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validate input
        boolean cancel = false;

        if (TextUtils.isEmpty(password)) {
            passwordLayout.setError("Password is required");
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            emailLayout.setError("Email is required");
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailLayout.setError("Invalid email address");
            cancel = true;
        }

        if (!cancel) {
            // Validate user credentials against database
            if (databaseHelper.validateUser(email, password)) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                // Navigate to Home activity
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish(); // Close login activity
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}