package com.restaurant.service;

import com.restaurant.OrderManager;
import com.restaurant.model.MenuItem;
import com.restaurant.model.Order;

import java.util.List;

public class OrderService {

    private final OrderManager orderManager;

    public OrderService(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public void createOrder(int tableId, List<MenuItem> items) { // Added tableId parameter
        double totalPrice = calculateTotalPrice(items);
        Order order = new Order(tableId, items, totalPrice, Order.Status.WAITING); // Using the correct constructor
        orderManager.placeOrder(order);
    }

    public void updateOrderStatus(int orderId, Order.Status status) {
        orderManager.updateOrderStatus(orderId, status);
    }

    private double calculateTotalPrice(List<MenuItem> items) {
        return items.stream().mapToDouble(MenuItem::getPrice).sum();
    }
}
