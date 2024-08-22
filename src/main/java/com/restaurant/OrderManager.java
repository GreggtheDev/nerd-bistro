package com.restaurant;

import com.restaurant.dao.OrderDao;
import com.restaurant.model.MenuItem;
import com.restaurant.model.Order;

import java.util.List;
import java.util.ArrayList;

public class OrderManager {

    private OrderDao orderDao;
    private MenuManager menuManager;

    // Constructor with dependency injection
    public OrderManager(OrderDao orderDao, MenuManager menuManager) {
        this.orderDao = orderDao;
        this.menuManager = menuManager;
    }

    public void placeOrder(Order order) {
        if (order == null || order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order or Order Items cannot be null or empty");
        }
        orderDao.addOrder(order);
    }

    public void updateOrderStatus(int orderId, Order.Status status) {
        orderDao.updateOrderStatus(orderId, status);
    }

    public List<Order> getOrdersByStatus(Order.Status status) {
        List<Order> orders = orderDao.getOrdersByStatus(status);
        for (Order order : orders) {
            List<MenuItem> items = order.getItemsFromString(order.getItemsAsString());
            order.setItems(items);
        }
        return orders;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = orderDao.getAllOrders();
        for (Order order : orders) {
            List<MenuItem> items = order.getItemsFromString(order.getItemsAsString());
            order.setItems(items);
        }
        return orders;
    }

    public void deleteOrder(int orderId) {
        orderDao.deleteOrder(orderId);
        System.out.println("Order with ID " + orderId + " has been deleted successfully.");
    }

    public List<String> getCompletedOrderDetails() {
        List<Order> completedOrders = getOrdersByStatus(Order.Status.FINISHED);
        List<String> orderDetails = new ArrayList<>();

        for (Order order : completedOrders) {
            StringBuilder details = new StringBuilder();
            details.append("Order ID: ").append(order.getOrderId()).append("\n");
            details.append("Items:\n");

            for (MenuItem item : order.getItems()) {
                details.append("- ").append(item.getName())
                        .append(" x").append(item.getQuantity())
                        .append(" @ $").append(item.getPrice()).append("\n");
            }

            details.append("Total Price: $").append(order.getTotalPrice()).append("\n");
            orderDetails.add(details.toString());
        }

        return orderDetails;
    }
}
