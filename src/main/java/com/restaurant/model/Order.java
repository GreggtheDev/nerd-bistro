package com.restaurant.model;

import java.util.List;

/**
 * The Order class represents a customer order in the restaurant management system.
 * It contains details about the order such as the items ordered, the total price,
 * and the current status of the order.
 */
public class Order {

    /**
     * Unique identifier for the order.
     */
    private int id;

    /**
     * List of menu items that have been ordered.
     */
    private List<MenuItem> itemsOrdered;

    /**
     * The total price of all the items in the order.
     */
    private double totalPrice;

    /**
     * The current status of the order. Can be "Waiting", "Preparing", or "Ready".
     */
    private String status; // Waiting, Preparing, Ready

    /**
     * Constructs an Order object with the specified id, list of items ordered,
     * total price, and status.
     *
     * @param id            The unique identifier for the order.
     * @param itemsOrdered  The list of menu items that have been ordered.
     * @param totalPrice    The total price of the order.
     * @param status        The current status of the order.
     */
    public Order(int id, List<MenuItem> itemsOrdered, double totalPrice, String status) {
        this.id = id;
        this.itemsOrdered = itemsOrdered;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    /**
     * Returns the unique identifier for the order.
     *
     * @return The order ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the list of menu items that have been ordered.
     *
     * @return The list of items ordered.
     */
    public List<MenuItem> getItemsOrdered() {
        return itemsOrdered;
    }

    /**
     * Returns the total price of all the items in the order.
     *
     * @return The total price of the order.
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Returns the current status of the order.
     *
     * @return The order status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the order.
     *
     * @param status The new status of the order.
     */
    public void setStatus(String status) {
        this.status = status;
    }
}

