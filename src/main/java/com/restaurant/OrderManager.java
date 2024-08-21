package com.restaurant;

import com.restaurant.dao.OrderDao;
import com.restaurant.model.MenuItem;
import com.restaurant.model.Order;

import java.util.List;

public class OrderManager {

    private OrderDao orderDao;
    private MenuManager menuManager;

    // Constructor with dependency injection
    public OrderManager(OrderDao orderDao, MenuManager menuManager) {
        this.orderDao = orderDao;
        this.menuManager = menuManager;
    }

    public void placeOrder(Order order) {
        orderDao.addOrder(order);
    }

    public void updateOrderStatus(int orderId, Order.Status status) {
        orderDao.updateOrderStatus(orderId, status);
    }

    public List<Order> getOrdersByStatus(Order.Status status) {
        List<Order> orders = orderDao.getOrdersByStatus(status);
        for (Order order : orders) {
            order.setItems(order.getItemsFromString(order.getItemsAsString()));
        }
        return orders;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = orderDao.getAllOrders();
        for (Order order : orders) {
            order.setItems(order.getItemsFromString(order.getItemsAsString()));
        }
        return orders;
    }

    public void deleteOrder(int orderId) {
        orderDao.deleteOrder(orderId);
        System.out.println("Order with ID " + orderId + " has been deleted successfully.");
    }
}
