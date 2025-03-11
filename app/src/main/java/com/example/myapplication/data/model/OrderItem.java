package com.example.myapplication.data.model;

public class OrderItem {
    private long id;
    private long orderId;
    private long productId;
    private int quantity;
    private double priceAtTime;

    // Cache for product details (not stored in DB)
    private Product product;

    // Default constructor
    public OrderItem() {
    }

    // Constructor without id
    public OrderItem(long orderId, long productId, int quantity, double priceAtTime) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.priceAtTime = priceAtTime;
    }

    // Full constructor
    public OrderItem(long id, long orderId, long productId, int quantity, double priceAtTime) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.priceAtTime = priceAtTime;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
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

    public double getPriceAtTime() {
        return priceAtTime;
    }

    public void setPriceAtTime(double priceAtTime) {
        this.priceAtTime = priceAtTime;
    }

    // Product cache methods
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    // Helper method to calculate subtotal
    public double getSubtotal() {
        return quantity * priceAtTime;
    }
}