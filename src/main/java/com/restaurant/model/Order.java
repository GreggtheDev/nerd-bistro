package com.restaurant.model;

import com.restaurant.MenuManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class Order {
    private int orderId;
    private int tableId; // New field for table ID
    private long orderTime; // Time stored in milliseconds
    private double totalPrice;
    private Status status;
    private List<MenuItem> items;

    public enum Status {
        WAITING, PREPARING, READY, FINISHED
    }

    // Constructor for a new order
    public Order(int tableId, List<MenuItem> items, double totalPrice, Status status) {
        this.tableId = tableId; // Initialize the new field
        this.items = items;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderTime = System.currentTimeMillis();
    }

    // Constructor for retrieving an order from the database
    public Order(int orderId, int tableId, long orderTime, double totalPrice, Status status, List<MenuItem> items) {
        this.orderId = orderId;
        this.tableId = tableId; // Initialize the new field
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

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
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
                .map(item -> item.getId() + "x" + item.getQuantity())
                .collect(Collectors.joining(","));
    }

    // Converts a comma-separated string of item IDs back to a list of MenuItem objects
    public List<MenuItem> getItemsFromString(String itemsAsString) {
        MenuManager menuManager = new MenuManager();
        List<MenuItem> itemsList = new ArrayList<>();
        String[] itemsArray = itemsAsString.split(",");

        for (String itemString : itemsArray) {
            String[] parts = itemString.split("x");
            int id = Integer.parseInt(parts[0]);
            int quantity = Integer.parseInt(parts[1]);

            MenuItem item = menuManager.getMenuItemById(id);
            if (item != null) {
                item.setQuantity(quantity);
                itemsList.add(item);
            }
        }
        return itemsList;
    }

    // Method to format the display of order details, including item names and quantities
    public String getFormattedOrderDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Order ID: ").append(orderId)
                .append(", Table ID: ").append(tableId) // Include table ID
                .append(", Order Time: ").append(getFormattedOrderTime())
                .append(", Total Price: $").append(String.format("%.2f", totalPrice))
                .append(", Status: ").append(status)
                .append(", Items: ");
        for (MenuItem item : items) {
            details.append(item.getName())
                    .append(" x").append(item.getQuantity())
                    .append(", ");
        }
        return details.toString().replaceAll(", $", "");
    }

    @Override
    public String toString() {
        return getFormattedOrderDetails();
    }
}
