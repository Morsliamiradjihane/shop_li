package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    
    protected BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnNavigationItemSelectedListener(this);
            // Set the selected item based on the current activity
            updateNavigationSelection();
        }
    }

    private void updateNavigationSelection() {
        if (bottomNavigationView == null) return;

        int selectedItemId;
        if (this instanceof HomeActivity) {
            selectedItemId = R.id.navigation_home;
        } else if (this instanceof ProfileActivity) {
            selectedItemId = R.id.navigation_profile;
        } else if (this instanceof CartActivity) {
            selectedItemId = R.id.navigation_cart;
        } else if (this instanceof CategoriesActivity) {
            selectedItemId = R.id.navigation_categories;
        } else if (this instanceof WishlistActivity) {
            selectedItemId = R.id.navigation_wishlist;
        } else {
            return;
        }

        bottomNavigationView.setSelectedItemId(selectedItemId);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == bottomNavigationView.getSelectedItemId()) {
            return false;
        }

        Class<?> targetActivity = null;
        
        if (item.getItemId() == R.id.navigation_home) {
            targetActivity = HomeActivity.class;
        } else if (item.getItemId() == R.id.navigation_profile) {
            targetActivity = ProfileActivity.class;
        } else if (item.getItemId() == R.id.navigation_cart) {
            targetActivity = CartActivity.class;
        } else if (item.getItemId() == R.id.navigation_categories) {
            targetActivity = CategoriesActivity.class;
        } else if (item.getItemId() == R.id.navigation_wishlist) {
            targetActivity = WishlistActivity.class;
        }

        if (targetActivity != null && !targetActivity.isInstance(this)) {
            startActivity(new Intent(this, targetActivity));
            overridePendingTransition(0, 0);
            return true;
        }

        return false;
    }

    // Common toolbar setup method for child activities
    protected void setupToolbar(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}