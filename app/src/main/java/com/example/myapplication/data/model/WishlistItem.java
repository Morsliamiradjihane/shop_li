package com.example.myapplication.data.model;

public class WishlistItem {
    private long id;
    private long userId;
    private long productId;
    private String addedAt;

    // Cache for product details (not stored in DB)
    private Product product;

    // Default constructor
    public WishlistItem() {
    }

    // Constructor without id and addedAt
    public WishlistItem(long userId, long productId) {
        this.userId = userId;
        this.productId = productId;
    }

    // Full constructor
    public WishlistItem(long id, long userId, long productId, String addedAt) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
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
}