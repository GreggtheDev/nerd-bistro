package com.restaurant;

import com.restaurant.dao.OrderDao;
import com.restaurant.model.Order;
import com.restaurant.model.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {

    private final OrderDao orderDao;
    private final MenuManager menuManager;

    public OrderManager(MenuManager menuManager, OrderDao orderDao) {
        this.menuManager = menuManager;
        this.orderDao = orderDao;
    }

    public void placeOrder(Order order) {
        orderDao.addOrder(order);
    }

    public List<Order> getOrdersByStatus(Order.Status status) {
        List<Order> orders = orderDao.getOrdersByStatus(status);
        // Convert the items string back into MenuItem objects
        for (Order order : orders) {
            List<MenuItem> items = getItemsFromString(order.getItemsAsString());
            order.setItems(items);
        }
        return orders;
    }

    public void updateOrderStatus(int orderId, Order.Status status) {
        orderDao.updateOrderStatus(orderId, status);
    }

    private String getItemsAsString(List<MenuItem> items) {
        StringBuilder sb = new StringBuilder();
        for (MenuItem item : items) {
            sb.append(item.getName()).append(",");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    private List<MenuItem> getItemsFromString(String itemsStr) {
        List<MenuItem> items = new ArrayList<>();
        for (String itemName : itemsStr.split(",")) {
            MenuItem menuItem = menuManager.getMenuItemByName(itemName.trim());
            if (menuItem != null) {
                items.add(menuItem);
            }
        }
        return items;
    }
}
