package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.adapters.CategoriesAdapter;
import com.example.myapplication.adapters.FeaturedProductsAdapter;
import com.example.myapplication.adapters.ProductsAdapter;
import com.example.myapplication.data.DatabaseHelper;
import com.example.myapplication.data.model.Category;
import com.example.myapplication.data.model.Product;

import java.util.List;

public class HomeActivity extends BaseActivity {
    private ViewPager2 featuredProductsViewPager;
    private RecyclerView categoriesRecyclerView;
    private RecyclerView productsRecyclerView;
    private SearchView searchView;

    private DatabaseHelper dbHelper;
    private CategoriesAdapter categoriesAdapter;
    private ProductsAdapter productsAdapter;
    private FeaturedProductsAdapter featuredProductsAdapter;

    private List<Category> categories;
    private long currentCategoryId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize views
        initViews();
        
        // Setup bottom navigation from base activity
        setupBottomNavigation();
        
        // Setup toolbar
        setupToolbar("Shop");
        
        // Initialize components
        setupAdapters();
        setupSearch();
        loadData();
    }

    private void initViews() {
        featuredProductsViewPager = findViewById(R.id.featuredProductsViewPager);
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        searchView = findViewById(R.id.searchView);
    }

    private void setupAdapters() {
        // Categories adapter
        categoriesAdapter = new CategoriesAdapter((category, position) -> {
            currentCategoryId = category.getId();
            categoriesAdapter.setSelectedPosition(position);
            loadProductsForCategory(currentCategoryId);
        });
        categoriesRecyclerView.setLayoutManager(
            new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        categoriesRecyclerView.setAdapter(categoriesAdapter);

        // Products adapter
        productsAdapter = new ProductsAdapter(product -> {
            // TODO: Handle product click - open product details
        });
        productsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productsRecyclerView.setAdapter(productsAdapter);

        // Featured products adapter
        featuredProductsAdapter = new FeaturedProductsAdapter(product -> {
            // TODO: Handle featured product click - open product details
        });
        featuredProductsViewPager.setAdapter(featuredProductsAdapter);
    }

    private void setupSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchProducts(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO: Implement search suggestions
                return false;
            }
        });
    }

    private void loadData() {
        // Load categories
        categories = dbHelper.getAllCategories();
        categoriesAdapter.updateCategories(categories);

        // Load first category's products if categories exist
        if (!categories.isEmpty()) {
            currentCategoryId = categories.get(0).getId();
            categoriesAdapter.setSelectedPosition(0);
            loadProductsForCategory(currentCategoryId);

            // Set featured products (using first 5 products from first category)
            List<Product> products = dbHelper.getProductsByCategory(currentCategoryId);
            if (products.size() > 5) {
                products = products.subList(0, 5);
            }
            featuredProductsAdapter.updateFeaturedProducts(products);
        }
    }

    private void loadProductsForCategory(long categoryId) {
        List<Product> products = dbHelper.getProductsByCategory(categoryId);
        productsAdapter.updateProducts(products);
    }

    private void searchProducts(String query) {
        // TODO: Implement search logic using DatabaseHelper
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}