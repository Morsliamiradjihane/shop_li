package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProfileActivity extends BaseActivity {
    private TextView userNameText;
    private TextView userEmailText;
    private TextView accountSettingsButton;
    private TextView addressButton;
    private TextView paymentButton;
    private RecyclerView orderHistoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        initViews();
        
        // Setup bottom navigation from base activity
        setupBottomNavigation();
        
        // Setup toolbar
        setupToolbar("Profile");
        
        // Initialize components
        setupUserInfo();
        setupClickListeners();
        setupOrderHistory();
    }

    private void initViews() {
        userNameText = findViewById(R.id.userNameText);
        userEmailText = findViewById(R.id.userEmailText);
        accountSettingsButton = findViewById(R.id.accountSettingsButton);
        addressButton = findViewById(R.id.addressButton);
        paymentButton = findViewById(R.id.paymentButton);
        orderHistoryRecyclerView = findViewById(R.id.orderHistoryRecyclerView);
    }

    private void setupUserInfo() {
        // TODO: Load user info from preferences or backend
        userNameText.setText("John Doe");
        userEmailText.setText("john.doe@example.com");
    }

    private void setupClickListeners() {
        accountSettingsButton.setOnClickListener(v -> {
            // TODO: Launch account settings activity
            // startActivity(new Intent(this, AccountSettingsActivity.class));
        });

        addressButton.setOnClickListener(v -> {
            // TODO: Launch address management activity
            // startActivity(new Intent(this, AddressActivity.class));
        });

        paymentButton.setOnClickListener(v -> {
            // TODO: Launch payment methods activity
            // startActivity(new Intent(this, PaymentMethodsActivity.class));
        });
    }

    private void setupOrderHistory() {
        orderHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // TODO: Set up order history adapter
        // orderHistoryRecyclerView.setAdapter(new OrderHistoryAdapter());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}