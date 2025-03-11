package com.example.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.data.model.CartItem;
import com.example.myapplication.data.model.Category;
import com.example.myapplication.data.model.Order;
import com.example.myapplication.data.model.OrderItem;
import com.example.myapplication.data.model.Product;
import com.example.myapplication.data.model.User;
import com.example.myapplication.data.model.WishlistItem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ShopLiDB";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_CART_ITEMS = "cart_items";
    private static final String TABLE_WISHLIST_ITEMS = "wishlist_items";
    private static final String TABLE_ORDERS = "orders";
    private static final String TABLE_ORDER_ITEMS = "order_items";

    // Common Column Names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // Users Table Columns
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    // Products Table Columns
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_PRICE = "price";
    private static final String KEY_IMAGE_URL = "image_url";
    private static final String KEY_CATEGORY_ID = "category_id";
    private static final String KEY_STOCK_QUANTITY = "stock_quantity";

    // Categories Table Columns
    // (name and description already defined)

    // Cart and Wishlist Items Columns
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_PRODUCT_ID = "product_id";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_ADDED_AT = "added_at";

    // Orders Table Columns
    private static final String KEY_TOTAL_AMOUNT = "total_amount";
    private static final String KEY_STATUS = "status";
    private static final String KEY_SHIPPING_ADDRESS = "shipping_address";
    private static final String KEY_ORDER_DATE = "order_date";

    // Order Items Table Columns
    private static final String KEY_ORDER_ID = "order_id";
    private static final String KEY_PRICE_AT_TIME = "price_at_time";

    // Create Table Statements
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NAME + " TEXT NOT NULL,"
            + KEY_EMAIL + " TEXT UNIQUE NOT NULL,"
            + KEY_PASSWORD + " TEXT NOT NULL,"
            + KEY_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ")";

    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE " + TABLE_CATEGORIES + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NAME + " TEXT NOT NULL,"
            + KEY_DESCRIPTION + " TEXT,"
            + KEY_IMAGE_URL + " TEXT"
            + ")";

    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NAME + " TEXT NOT NULL,"
            + KEY_DESCRIPTION + " TEXT,"
            + KEY_PRICE + " REAL NOT NULL,"
            + KEY_IMAGE_URL + " TEXT,"
            + KEY_CATEGORY_ID + " INTEGER NOT NULL,"
            + KEY_STOCK_QUANTITY + " INTEGER NOT NULL DEFAULT 0,"
            + KEY_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + "FOREIGN KEY(" + KEY_CATEGORY_ID + ") REFERENCES " + TABLE_CATEGORIES + "(" + KEY_ID + ")"
            + ")";

    private static final String CREATE_TABLE_CART_ITEMS = "CREATE TABLE " + TABLE_CART_ITEMS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_USER_ID + " INTEGER NOT NULL,"
            + KEY_PRODUCT_ID + " INTEGER NOT NULL,"
            + KEY_QUANTITY + " INTEGER NOT NULL DEFAULT 1,"
            + KEY_ADDED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + "FOREIGN KEY(" + KEY_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + "),"
            + "FOREIGN KEY(" + KEY_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + KEY_ID + ")"
            + ")";

    private static final String CREATE_TABLE_WISHLIST_ITEMS = "CREATE TABLE " + TABLE_WISHLIST_ITEMS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_USER_ID + " INTEGER NOT NULL,"
            + KEY_PRODUCT_ID + " INTEGER NOT NULL,"
            + KEY_ADDED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + "FOREIGN KEY(" + KEY_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + "),"
            + "FOREIGN KEY(" + KEY_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + KEY_ID + ")"
            + ")";

    private static final String CREATE_TABLE_ORDERS = "CREATE TABLE " + TABLE_ORDERS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_USER_ID + " INTEGER NOT NULL,"
            + KEY_TOTAL_AMOUNT + " REAL NOT NULL,"
            + KEY_STATUS + " TEXT NOT NULL,"
            + KEY_SHIPPING_ADDRESS + " TEXT NOT NULL,"
            + KEY_ORDER_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
            + "FOREIGN KEY(" + KEY_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + KEY_ID + ")"
            + ")";

    private static final String CREATE_TABLE_ORDER_ITEMS = "CREATE TABLE " + TABLE_ORDER_ITEMS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_ORDER_ID + " INTEGER NOT NULL,"
            + KEY_PRODUCT_ID + " INTEGER NOT NULL,"
            + KEY_QUANTITY + " INTEGER NOT NULL,"
            + KEY_PRICE_AT_TIME + " REAL NOT NULL,"
            + "FOREIGN KEY(" + KEY_ORDER_ID + ") REFERENCES " + TABLE_ORDERS + "(" + KEY_ID + "),"
            + "FOREIGN KEY(" + KEY_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + KEY_ID + ")"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_CATEGORIES);
        db.execSQL(CREATE_TABLE_PRODUCTS);
        db.execSQL(CREATE_TABLE_CART_ITEMS);
        db.execSQL(CREATE_TABLE_WISHLIST_ITEMS);
        db.execSQL(CREATE_TABLE_ORDERS);
        db.execSQL(CREATE_TABLE_ORDER_ITEMS);

        // Insert dummy data
        insertDummyData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WISHLIST_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(db);
    }

    private void insertDummyData(SQLiteDatabase db) {
        // Insert dummy users
        insertDummyUsers(db);
        
        // Insert categories
        insertDummyCategories(db);
        
        // Insert products
        insertDummyProducts(db);
        
        // Insert cart items
        insertDummyCartItems(db);
        
        // Insert wishlist items
        insertDummyWishlistItems(db);
        
        // Insert orders and order items
        insertDummyOrders(db);
    }

    private void insertDummyUsers(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        // Dummy user 1
        values.put(KEY_NAME, "John Doe");
        values.put(KEY_EMAIL, "john@example.com");
        values.put(KEY_PASSWORD, "password123");
        db.insert(TABLE_USERS, null, values);

        // Dummy user 2
        values.clear();
        values.put(KEY_NAME, "Jane Smith");
        values.put(KEY_EMAIL, "jane@example.com");
        values.put(KEY_PASSWORD, "password456");
        db.insert(TABLE_USERS, null, values);

        // Admin user
        values.clear();
        values.put(KEY_NAME, "Admin User");
        values.put(KEY_EMAIL, "admin@example.com");
        values.put(KEY_PASSWORD, "admin123");
        db.insert(TABLE_USERS, null, values);
    }

    private void insertDummyCategories(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        // Perfumes
        values.put(KEY_NAME, "Perfumes");
        values.put(KEY_DESCRIPTION, "Luxury fragrances and perfumes");
        values.put(KEY_IMAGE_URL, "https://example.com/images/perfumes.jpg");
        db.insert(TABLE_CATEGORIES, null, values);

        // Chocolates
        values.clear();
        values.put(KEY_NAME, "Chocolates");
        values.put(KEY_DESCRIPTION, "Premium chocolate collections");
        values.put(KEY_IMAGE_URL, "https://example.com/images/chocolates.jpg");
        db.insert(TABLE_CATEGORIES, null, values);

        // Candies
        values.clear();
        values.put(KEY_NAME, "Candies");
        values.put(KEY_DESCRIPTION, "Sweet treats and confectionery");
        values.put(KEY_IMAGE_URL, "https://example.com/images/candies.jpg");
        db.insert(TABLE_CATEGORIES, null, values);
    }

    private void insertDummyProducts(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        // Perfume Products (Category ID: 1)
        values.put(KEY_NAME, "Chanel No. 5");
        values.put(KEY_DESCRIPTION, "Iconic luxury perfume with floral notes");
        values.put(KEY_PRICE, 129.99);
        values.put(KEY_IMAGE_URL, "https://example.com/images/chanel5.jpg");
        values.put(KEY_CATEGORY_ID, 1);
        values.put(KEY_STOCK_QUANTITY, 30);
        db.insert(TABLE_PRODUCTS, null, values);

        values.clear();
        values.put(KEY_NAME, "Dior Sauvage");
        values.put(KEY_DESCRIPTION, "Fresh and bold masculine fragrance");
        values.put(KEY_PRICE, 94.99);
        values.put(KEY_IMAGE_URL, "https://example.com/images/sauvage.jpg");
        values.put(KEY_CATEGORY_ID, 1);
        values.put(KEY_STOCK_QUANTITY, 25);
        db.insert(TABLE_PRODUCTS, null, values);

        // Chocolate Products (Category ID: 2)
        values.clear();
        values.put(KEY_NAME, "Godiva Gift Box");
        values.put(KEY_DESCRIPTION, "Luxury chocolate assortment box");
        values.put(KEY_PRICE, 49.99);
        values.put(KEY_IMAGE_URL, "https://example.com/images/godiva.jpg");
        values.put(KEY_CATEGORY_ID, 2);
        values.put(KEY_STOCK_QUANTITY, 50);
        db.insert(TABLE_PRODUCTS, null, values);

        values.clear();
        values.put(KEY_NAME, "Lindt Excellence Dark");
        values.put(KEY_DESCRIPTION, "90% cocoa dark chocolate bar");
        values.put(KEY_PRICE, 4.99);
        values.put(KEY_IMAGE_URL, "https://example.com/images/lindt.jpg");
        values.put(KEY_CATEGORY_ID, 2);
        values.put(KEY_STOCK_QUANTITY, 100);
        db.insert(TABLE_PRODUCTS, null, values);

        // Candy Products (Category ID: 3)
        values.clear();
        values.put(KEY_NAME, "Haribo Gummy Bears");
        values.put(KEY_DESCRIPTION, "Classic fruit-flavored gummy bears");
        values.put(KEY_PRICE, 2.99);
        values.put(KEY_IMAGE_URL, "https://example.com/images/haribo.jpg");
        values.put(KEY_CATEGORY_ID, 3);
        values.put(KEY_STOCK_QUANTITY, 150);
        db.insert(TABLE_PRODUCTS, null, values);

        values.clear();
        values.put(KEY_NAME, "M&M's Peanut");
        values.put(KEY_DESCRIPTION, "Chocolate-covered peanuts in a candy shell");
        values.put(KEY_PRICE, 3.99);
        values.put(KEY_IMAGE_URL, "https://example.com/images/mandms.jpg");
        values.put(KEY_CATEGORY_ID, 3);
        values.put(KEY_STOCK_QUANTITY, 120);
        db.insert(TABLE_PRODUCTS, null, values);
    }

    private void insertDummyCartItems(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        // Cart items for John Doe (user_id = 1)
        values.put(KEY_USER_ID, 1);
        values.put(KEY_PRODUCT_ID, 1); // Smartphone X
        values.put(KEY_QUANTITY, 1);
        db.insert(TABLE_CART_ITEMS, null, values);

        values.clear();
        values.put(KEY_USER_ID, 1);
        values.put(KEY_PRODUCT_ID, 3); // Classic T-Shirt
        values.put(KEY_QUANTITY, 2);
        db.insert(TABLE_CART_ITEMS, null, values);

        // Cart items for Jane Smith (user_id = 2)
        values.clear();
        values.put(KEY_USER_ID, 2);
        values.put(KEY_PRODUCT_ID, 5); // The Great Novel
        values.put(KEY_QUANTITY, 1);
        db.insert(TABLE_CART_ITEMS, null, values);
    }

    private void insertDummyWishlistItems(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        // Wishlist items for John Doe (user_id = 1)
        values.put(KEY_USER_ID, 1);
        values.put(KEY_PRODUCT_ID, 2); // Laptop Pro
        db.insert(TABLE_WISHLIST_ITEMS, null, values);

        values.clear();
        values.put(KEY_USER_ID, 1);
        values.put(KEY_PRODUCT_ID, 6); // Coffee Maker
        db.insert(TABLE_WISHLIST_ITEMS, null, values);

        // Wishlist items for Jane Smith (user_id = 2)
        values.clear();
        values.put(KEY_USER_ID, 2);
        values.put(KEY_PRODUCT_ID, 4); // Denim Jeans
        db.insert(TABLE_WISHLIST_ITEMS, null, values);
    }

    private void insertDummyOrders(SQLiteDatabase db) {
        ContentValues orderValues = new ContentValues();
        ContentValues itemValues = new ContentValues();

        // Order for John Doe (user_id = 1)
        orderValues.put(KEY_USER_ID, 1);
        orderValues.put(KEY_TOTAL_AMOUNT, 719.98);
        orderValues.put(KEY_STATUS, Order.STATUS_DELIVERED);
        orderValues.put(KEY_SHIPPING_ADDRESS, "123 Main St, Anytown, USA");
        long orderId1 = db.insert(TABLE_ORDERS, null, orderValues);

        // Order items for John's order
        itemValues.put(KEY_ORDER_ID, orderId1);
        itemValues.put(KEY_PRODUCT_ID, 1); // Smartphone X
        itemValues.put(KEY_QUANTITY, 1);
        itemValues.put(KEY_PRICE_AT_TIME, 699.99);
        db.insert(TABLE_ORDER_ITEMS, null, itemValues);

        itemValues.clear();
        itemValues.put(KEY_ORDER_ID, orderId1);
        itemValues.put(KEY_PRODUCT_ID, 3); // Classic T-Shirt
        itemValues.put(KEY_QUANTITY, 1);
        itemValues.put(KEY_PRICE_AT_TIME, 19.99);
        db.insert(TABLE_ORDER_ITEMS, null, itemValues);

        // Order for Jane Smith (user_id = 2)
        orderValues.clear();
        orderValues.put(KEY_USER_ID, 2);
        orderValues.put(KEY_TOTAL_AMOUNT, 94.98);
        orderValues.put(KEY_STATUS, Order.STATUS_SHIPPED);
        orderValues.put(KEY_SHIPPING_ADDRESS, "456 Oak Rd, Other City, USA");
        long orderId2 = db.insert(TABLE_ORDERS, null, orderValues);

        // Order items for Jane's order
        itemValues.clear();
        itemValues.put(KEY_ORDER_ID, orderId2);
        itemValues.put(KEY_PRODUCT_ID, 5); // The Great Novel
        itemValues.put(KEY_QUANTITY, 1);
        itemValues.put(KEY_PRICE_AT_TIME, 14.99);
        db.insert(TABLE_ORDER_ITEMS, null, itemValues);

        itemValues.clear();
        itemValues.put(KEY_ORDER_ID, orderId2);
        itemValues.put(KEY_PRODUCT_ID, 6); // Coffee Maker
        itemValues.put(KEY_QUANTITY, 1);
        itemValues.put(KEY_PRICE_AT_TIME, 79.99);
        db.insert(TABLE_ORDER_ITEMS, null, itemValues);
    }

    // CRUD Operations for Users
    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PASSWORD, user.getPassword());

        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }

    public User getUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        Cursor cursor = db.query(TABLE_USERS,
                new String[]{KEY_ID, KEY_NAME, KEY_EMAIL, KEY_PASSWORD, KEY_CREATED_AT},
                KEY_EMAIL + "=?", new String[]{email},
                null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            user = new User(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
            cursor.close();
        }

        db.close();
        return user;
    }

    public boolean validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean isValid = false;

        Cursor cursor = db.query(TABLE_USERS,
                new String[]{KEY_ID},
                KEY_EMAIL + "=? AND " + KEY_PASSWORD + "=?",
                new String[]{email, password},
                null, null, null, null);

        if (cursor != null) {
            isValid = cursor.getCount() > 0;
            cursor.close();
        }

        db.close();
        return isValid;
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean exists = false;

        Cursor cursor = db.query(TABLE_USERS,
                new String[]{KEY_ID},
                KEY_EMAIL + "=?",
                new String[]{email},
                null, null, null, null);

        if (cursor != null) {
            exists = cursor.getCount() > 0;
            cursor.close();
        }

        db.close();
        return exists;
    }

    // Products CRUD Operations
    public List<Product> getProductsByCategory(long categoryId) {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PRODUCTS,
                null,
                KEY_CATEGORY_ID + "=?",
                new String[]{String.valueOf(categoryId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Product product = new Product(
                        cursor.getLong(cursor.getColumnIndex(KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(KEY_NAME)),
                        cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
                        cursor.getDouble(cursor.getColumnIndex(KEY_PRICE)),
                        cursor.getString(cursor.getColumnIndex(KEY_IMAGE_URL)),
                        cursor.getLong(cursor.getColumnIndex(KEY_CATEGORY_ID)),
                        cursor.getInt(cursor.getColumnIndex(KEY_STOCK_QUANTITY)),
                        cursor.getString(cursor.getColumnIndex(KEY_CREATED_AT))
                );
                products.add(product);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return products;
    }

    public Product getProduct(long productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Product product = null;

        Cursor cursor = db.query(TABLE_PRODUCTS,
                null,
                KEY_ID + "=?",
                new String[]{String.valueOf(productId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            product = new Product(
                    cursor.getLong(cursor.getColumnIndex(KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(KEY_NAME)),
                    cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
                    cursor.getDouble(cursor.getColumnIndex(KEY_PRICE)),
                    cursor.getString(cursor.getColumnIndex(KEY_IMAGE_URL)),
                    cursor.getLong(cursor.getColumnIndex(KEY_CATEGORY_ID)),
                    cursor.getInt(cursor.getColumnIndex(KEY_STOCK_QUANTITY)),
                    cursor.getString(cursor.getColumnIndex(KEY_CREATED_AT))
            );
            cursor.close();
        }

        db.close();
        return product;
    }

    // Categories CRUD Operations
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CATEGORIES,
                null,
                null,
                null,
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Category category = new Category(
                        cursor.getLong(cursor.getColumnIndex(KEY_ID)),
                        cursor.getString(cursor.getColumnIndex(KEY_NAME)),
                        cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(KEY_IMAGE_URL))
                );
                categories.add(category);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return categories;
    }

    // Cart Operations
    public List<CartItem> getCartItems(long userId) {
        List<CartItem> cartItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CART_ITEMS,
                null,
                KEY_USER_ID + "=?",
                new String[]{String.valueOf(userId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                CartItem item = new CartItem(
                        cursor.getLong(cursor.getColumnIndex(KEY_ID)),
                        cursor.getLong(cursor.getColumnIndex(KEY_USER_ID)),
                        cursor.getLong(cursor.getColumnIndex(KEY_PRODUCT_ID)),
                        cursor.getInt(cursor.getColumnIndex(KEY_QUANTITY)),
                        cursor.getString(cursor.getColumnIndex(KEY_ADDED_AT))
                );
                
                // Load associated product
                Product product = getProduct(item.getProductId());
                item.setProduct(product);
                
                cartItems.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return cartItems;
    }

    // Wishlist Operations
    public List<WishlistItem> getWishlistItems(long userId) {
        List<WishlistItem> wishlistItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_WISHLIST_ITEMS,
                null,
                KEY_USER_ID + "=?",
                new String[]{String.valueOf(userId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                WishlistItem item = new WishlistItem(
                        cursor.getLong(cursor.getColumnIndex(KEY_ID)),
                        cursor.getLong(cursor.getColumnIndex(KEY_USER_ID)),
                        cursor.getLong(cursor.getColumnIndex(KEY_PRODUCT_ID)),
                        cursor.getString(cursor.getColumnIndex(KEY_ADDED_AT))
                );
                
                // Load associated product
                Product product = getProduct(item.getProductId());
                item.setProduct(product);
                
                wishlistItems.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return wishlistItems;
    }

    // Order Operations
    public List<Order> getUserOrders(long userId) {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ORDERS,
                null,
                KEY_USER_ID + "=?",
                new String[]{String.valueOf(userId)},
                null, null, KEY_ORDER_DATE + " DESC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Order order = new Order(
                        cursor.getLong(cursor.getColumnIndex(KEY_ID)),
                        cursor.getLong(cursor.getColumnIndex(KEY_USER_ID)),
                        cursor.getDouble(cursor.getColumnIndex(KEY_TOTAL_AMOUNT)),
                        cursor.getString(cursor.getColumnIndex(KEY_STATUS)),
                        cursor.getString(cursor.getColumnIndex(KEY_SHIPPING_ADDRESS)),
                        cursor.getString(cursor.getColumnIndex(KEY_ORDER_DATE))
                );
                
                // Load order items
                order.setOrderItems(getOrderItems(order.getId()));
                
                orders.add(order);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return orders;
    }

    private List<OrderItem> getOrderItems(long orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ORDER_ITEMS,
                null,
                KEY_ORDER_ID + "=?",
                new String[]{String.valueOf(orderId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                OrderItem item = new OrderItem(
                        cursor.getLong(cursor.getColumnIndex(KEY_ID)),
                        cursor.getLong(cursor.getColumnIndex(KEY_ORDER_ID)),
                        cursor.getLong(cursor.getColumnIndex(KEY_PRODUCT_ID)),
                        cursor.getInt(cursor.getColumnIndex(KEY_QUANTITY)),
                        cursor.getDouble(cursor.getColumnIndex(KEY_PRICE_AT_TIME))
                );
                
                // Load associated product
                Product product = getProduct(item.getProductId());
                item.setProduct(product);
                
                orderItems.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return orderItems;
    }
}