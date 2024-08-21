package com.restaurant.model;

import java.util.List;

public class Order {
    private static int idCounter = 0;  // Static counter to generate unique order IDs
    private int orderId;
    private List<MenuItem> items;
    private double totalPrice;
    private Status status;

    // Enum to represent the status of the order
    public enum Status {
        WAITING,
        PREPARING,
        READY
    }

    // Constructor to create an order with a generated ID
    public Order(List<MenuItem> items) {
        this.orderId = ++idCounter;
        this.items = items;
        this.totalPrice = calculateTotalPrice();
        this.status = Status.WAITING;  // Default status when the order is created
    }

    // Constructor to create an order with a specified ID (useful when loading from a database)
    public Order(int orderId, List<MenuItem> items, double totalPrice, Status status) {
        this.orderId = orderId;
        this.items = items;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    // Method to calculate the total price of the order
    private double calculateTotalPrice() {
        return items.stream().mapToDouble(MenuItem::getPrice).sum();
    }

    // Getters and setters
    public int getOrderId() {
        return orderId;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
        this.totalPrice = calculateTotalPrice();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", items=" + items +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                '}';
    }
}
