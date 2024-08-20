package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {

    private static final String DATABASE_URL = "jdbc:sqlite:identifier.sqlite";
    private MenuManager menuManager;

    public OrderManager(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    // Method to establish a connection to the database
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return conn;
    }

    // Method to place a new order in the database
    public void placeOrder(Order order) {
        String sql = "INSERT INTO Orders (order_id, items, total_price, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, order.getOrderId());
            pstmt.setString(2, getItemsAsString(order.getItems()));
            pstmt.setDouble(3, order.getTotalPrice());
            pstmt.setString(4, order.getStatus().toString());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method to get all orders from the database
    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                String items = rs.getString("items");
                double totalPrice = rs.getDouble("total_price");
                String status = rs.getString("status");

                Order order = new Order(orderId, getItemsFromString(items), totalPrice, Order.Status.valueOf(status));
                orders.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return orders;
    }

    // Method to update the status of an existing order in the database
    public void updateOrderStatus(int orderId, Order.Status status) {
        String sql = "UPDATE Orders SET status = ? WHERE order_id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status.toString());
            pstmt.setInt(2, orderId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method to delete an order from the database by its ID
    public void deleteOrder(int orderId) {
        String sql = "DELETE FROM Orders WHERE order_id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method to convert a List of MenuItem objects into a comma-separated string of item names
    private String getItemsAsString(List<MenuItem> items) {
        StringBuilder sb = new StringBuilder();
        for (MenuItem item : items) {
            sb.append(item.getName()).append(",");
        }
        return sb.toString();
    }

    // Method to convert a comma-separated string of item names into a List of MenuItem objects
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
