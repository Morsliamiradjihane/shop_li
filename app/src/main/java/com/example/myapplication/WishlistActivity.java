package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;

public class WishlistActivity extends BaseActivity {
    private RecyclerView wishlistRecyclerView;
    private LinearLayout emptyWishlistView;
    private MaterialButton startShoppingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        // Initialize views
        initViews();
        
        // Setup bottom navigation from base activity
        setupBottomNavigation();
        
        // Setup toolbar
        setupToolbar("Wishlist");
        
        // Initialize components
        setupWishlist();
        setupClickListeners();
    }

    private void initViews() {
        wishlistRecyclerView = findViewById(R.id.wishlistRecyclerView);
        emptyWishlistView = findViewById(R.id.emptyWishlistView);
        startShoppingButton = findViewById(R.id.startShoppingButton);
    }

    private void setupWishlist() {
        wishlistRecyclerView.setLayoutManager(
            new GridLayoutManager(this, 2) // 2 columns
        );
        // TODO: Set up wishlist adapter
        // wishlistRecyclerView.setAdapter(new WishlistAdapter());
        
        // Check if wishlist is empty
        updateWishlistState();
    }

    private void setupClickListeners() {
        startShoppingButton.setOnClickListener(v -> {
            // Navigate to Home/Shop screen
            finish();
        });
    }

    private void updateWishlistState() {
        // TODO: Check if wishlist is empty based on actual data
        boolean isWishlistEmpty = true; // This should be determined by actual wishlist data
        
        if (isWishlistEmpty) {
            wishlistRecyclerView.setVisibility(View.GONE);
            emptyWishlistView.setVisibility(View.VISIBLE);
        } else {
            wishlistRecyclerView.setVisibility(View.VISIBLE);
            emptyWishlistView.setVisibility(View.GONE);
        }
    }

    // TODO: Add methods for wishlist item management
    private void removeFromWishlist(long productId) {
        // Implement remove from wishlist functionality
    }

    private void moveToCart(long productId) {
        // Implement move to cart functionality
    }

    private void shareWishlist() {
        // Implement wishlist sharing functionality
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