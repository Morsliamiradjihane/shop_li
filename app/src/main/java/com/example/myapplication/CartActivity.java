package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;

public class CartActivity extends BaseActivity {
    private RecyclerView cartItemsRecyclerView;
    private LinearLayout emptyCartView;
    private MaterialButton startShoppingButton;
    private MaterialButton checkoutButton;
    private TextView subtotalText;
    private TextView deliveryText;
    private TextView totalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialize views
        initViews();
        
        // Setup bottom navigation from base activity
        setupBottomNavigation();
        
        // Setup toolbar
        setupToolbar("Cart");
        
        // Initialize components
        setupCartList();
        setupClickListeners();
        updateCartState();
    }

    private void initViews() {
        cartItemsRecyclerView = findViewById(R.id.cartItemsRecyclerView);
        emptyCartView = findViewById(R.id.emptyCartView);
        startShoppingButton = findViewById(R.id.startShoppingButton);
        checkoutButton = findViewById(R.id.checkoutButton);
        subtotalText = findViewById(R.id.subtotalText);
        deliveryText = findViewById(R.id.deliveryText);
        totalText = findViewById(R.id.totalText);
    }

    private void setupCartList() {
        cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // TODO: Set up cart items adapter
        // cartItemsRecyclerView.setAdapter(new CartItemsAdapter());
    }

    private void setupClickListeners() {
        startShoppingButton.setOnClickListener(v -> {
            // Navigate to Home/Shop screen
            finish();
        });

        checkoutButton.setOnClickListener(v -> {
            // TODO: Implement checkout process
            startCheckout();
        });
    }

    private void updateCartState() {
        // TODO: Check if cart is empty
        boolean isCartEmpty = true; // This should be determined by actual cart data
        
        if (isCartEmpty) {
            cartItemsRecyclerView.setVisibility(View.GONE);
            emptyCartView.setVisibility(View.VISIBLE);
            checkoutButton.setEnabled(false);
        } else {
            cartItemsRecyclerView.setVisibility(View.VISIBLE);
            emptyCartView.setVisibility(View.GONE);
            checkoutButton.setEnabled(true);
            updatePrices();
        }
    }

    private void updatePrices() {
        // TODO: Calculate actual prices from cart items
        double subtotal = 0.00;
        double delivery = 0.00;
        double total = subtotal + delivery;

        subtotalText.setText(String.format("$%.2f", subtotal));
        deliveryText.setText(String.format("$%.2f", delivery));
        totalText.setText(String.format("$%.2f", total));
    }

    private void startCheckout() {
        // TODO: Start checkout process
        // Intent intent = new Intent(this, CheckoutActivity.class);
        // startActivity(intent);
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