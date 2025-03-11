package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategoriesActivity extends BaseActivity {
    private RecyclerView featuredCategoriesRecyclerView;
    private RecyclerView allCategoriesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // Initialize views
        initViews();
        
        // Setup bottom navigation from base activity
        setupBottomNavigation();
        
        // Setup toolbar
        setupToolbar("Categories");
        
        // Initialize components
        setupFeaturedCategories();
        setupAllCategories();
    }

    private void initViews() {
        featuredCategoriesRecyclerView = findViewById(R.id.featuredCategoriesRecyclerView);
        allCategoriesRecyclerView = findViewById(R.id.allCategoriesRecyclerView);
    }

    private void setupFeaturedCategories() {
        featuredCategoriesRecyclerView.setLayoutManager(
            new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        // TODO: Set up featured categories adapter
        // featuredCategoriesRecyclerView.setAdapter(new FeaturedCategoriesAdapter(getFeaturedCategories()));
    }

    private void setupAllCategories() {
        allCategoriesRecyclerView.setLayoutManager(
            new GridLayoutManager(this, 2) // 2 columns
        );
        // TODO: Set up all categories adapter
        // allCategoriesRecyclerView.setAdapter(new AllCategoriesAdapter(getAllCategories()));
    }

    // TODO: Replace with actual data from backend
    private void getFeaturedCategories() {
        // Sample categories
        /*
        return Arrays.asList(
            new Category("Electronics", R.drawable.ic_electronics),
            new Category("Fashion", R.drawable.ic_fashion),
            new Category("Home", R.drawable.ic_home),
            new Category("Sports", R.drawable.ic_sports)
        );
        */
    }

    // TODO: Replace with actual data from backend
    private void getAllCategories() {
        // Sample categories
        /*
        return Arrays.asList(
            new Category("Electronics", R.drawable.ic_electronics),
            new Category("Fashion", R.drawable.ic_fashion),
            new Category("Home", R.drawable.ic_home),
            new Category("Sports", R.drawable.ic_sports),
            new Category("Books", R.drawable.ic_books),
            new Category("Beauty", R.drawable.ic_beauty),
            new Category("Toys", R.drawable.ic_toys),
            new Category("Food", R.drawable.ic_food)
        );
        */
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