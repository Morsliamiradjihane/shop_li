package com.example.myapplication.data.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_CONFIRMED = "CONFIRMED";
    public static final String STATUS_SHIPPED = "SHIPPED";
    public static final String STATUS_DELIVERED = "DELIVERED";
    public static final String STATUS_CANCELLED = "CANCELLED";

    private long id;
    private long userId;
    private double totalAmount;
    private String status;
    private String shippingAddress;
    private String orderDate;

    // Cache for order items (not stored in DB)
    private List<OrderItem> orderItems;

    // Default constructor
    public Order() {
        this.orderItems = new ArrayList<>();
    }

    // Constructor without id and orderDate
    public Order(long userId, double totalAmount, String status, String shippingAddress) {
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.orderItems = new ArrayList<>();
    }

    // Full constructor
    public Order(long id, long userId, double totalAmount, String status, 
                String shippingAddress, String orderDate) {
        this.id = id;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.orderDate = orderDate;
        this.orderItems = new ArrayList<>();
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    // Order items methods
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void addOrderItem(OrderItem item) {
        if (this.orderItems == null) {
            this.orderItems = new ArrayList<>();
        }
        this.orderItems.add(item);
    }

    // Helper method to calculate total from items
    public void calculateTotalFromItems() {
        if (orderItems != null) {
            this.totalAmount = orderItems.stream()
                    .mapToDouble(item -> item.getQuantity() * item.getPriceAtTime())
                    .sum();
        }
    }
}