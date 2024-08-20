package com.restaurant.service;

import com.restaurant.dao.OrderDao;
import com.restaurant.model.MenuItem;
import com.restaurant.model.Order;

import java.util.List;

/**
 * The OrderService class provides business logic related to managing orders in the
 * restaurant management system. It interacts with the OrderDao to perform CRUD operations
 * on orders and handles the calculation of the total price for an order.
 */
public class OrderService {

    private final OrderDao orderDao;

    /**
     * Constructs an OrderService instance and initializes the OrderDao for database interaction.
     */
    public OrderService() {
        this.orderDao = new OrderDao();
    }

    /**
     * Creates a new order with the specified list of menu items.
     * The order is assigned a status of "waiting" by default.
     *
     * @param items The list of menu items to be included in the order.
     */
    public void createOrder(List<MenuItem> items) {
        Order order = new Order(0, items, calculateTotalPrice(items), "waiting");
        orderDao.addOrder(order);
    }

    /**
     * Updates the status of an existing order.
     *
     * @param orderId The ID of the order to be updated.
     * @param status  The new status of the order (e.g., "preparing", "ready").
     */
    public void updateOrderStatus(int orderId, String status) {
        orderDao.updateOrderStatus(orderId, status);
    }

    /**
     * Calculates the total price of a list of menu items.
     *
     * @param items The list of menu items whose prices are to be summed.
     * @return The total price of all the items in the list.
     */
    private double calculateTotalPrice(List<MenuItem> items) {
        return items.stream().mapToDouble(MenuItem::getPrice).sum();
    }
}
