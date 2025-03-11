package com.example.myapplication.data.model;

public class CartItem {
    private long id;
    private long userId;
    private long productId;
    private int quantity;
    private String addedAt;

    // Cache for product details (not stored in DB)
    private Product product;

    // Default constructor
    public CartItem() {
    }

    // Constructor without id and addedAt
    public CartItem(long userId, long productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Full constructor
    public CartItem(long id, long userId, long productId, int quantity, String addedAt) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.addedAt = addedAt;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(String addedAt) {
        this.addedAt = addedAt;
    }

    // Product cache methods
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    // Helper method to calculate total price
    public double getTotalPrice() {
        if (product != null) {
            return quantity * product.getPrice();
        }
        return 0.0;
    }
}