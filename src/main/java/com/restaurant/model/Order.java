package com.restaurant.model;

import com.restaurant.MenuManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Order {
    private int orderId;
    private long orderTime; // Time stored in milliseconds
    private double totalPrice;
    private Status status;
    private List<MenuItem> items;

    public enum Status {
        WAITING, PREPARING, FINISHED
    }

    // Constructor for a new order
    public Order(List<MenuItem> items, double totalPrice, Status status) {
        this.items = items;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderTime = System.currentTimeMillis();
    }

    // Constructor for retrieving an order from the database
    public Order(int orderId, long orderTime, double totalPrice, Status status, List<MenuItem> items) {
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.totalPrice = totalPrice;
        this.status = status;
        this.items = items;
    }

    // Getters and Setters

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public String getFormattedOrderTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(new Date(orderTime));
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

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    // Converts the list of MenuItem objects to a comma-separated string of item IDs
    public String getItemsAsString() {
        return items.stream()
                .map(item -> String.valueOf(item.getId()))
                .collect(Collectors.joining(","));
    }

    // Converts a comma-separated string of item IDs back to a list of MenuItem objects
    public List<MenuItem> getItemsFromString(String itemsAsString) {
        MenuManager menuManager = new MenuManager();
        return itemsAsString.isEmpty() ?
                List.of() :
                List.of(itemsAsString.split(","))
                        .stream()
                        .map(id -> menuManager.getMenuItemById(Integer.parseInt(id)))
                        .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.format("Order ID: %d, Order Time: %s, Total Price: %.2f, Status: %s, Items: %s",
                orderId, getFormattedOrderTime(), totalPrice, status, getItemsAsString());
    }
}
