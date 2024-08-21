package com.restaurant.model;

public class Table {
    private int tableId;
    private int tableSize;
    private String status; // "Available", "Reserved", "Occupied"

    // Constructor
    public Table(int tableId, int tableSize, String status) {
        this.tableId = tableId;
        this.tableSize = tableSize;
        this.status = status;
    }

    // Getters and Setters
    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getTableSize() {
        return tableSize;
    }

    public void setTableSize(int tableSize) {
        this.tableSize = tableSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Method to check if the table is available
    public boolean isAvailable() {
        return "Available".equalsIgnoreCase(this.status);
    }

    // Method to reserve the table
    public void reserveTable() {
        if (isAvailable()) {
            this.status = "Reserved";
        } else {
            System.out.println("Table is not available.");
        }
    }

    // Method to occupy the table
    public void occupyTable() {
        if ("Reserved".equalsIgnoreCase(this.status)) {
            this.status = "Occupied";
        } else {
            System.out.println("Table must be reserved before it can be occupied.");
        }
    }

    // Method to free up the table
    public void freeTable() {
        this.status = "Available";
    }
}
