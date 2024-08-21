package com.restaurant.dao;

import com.restaurant.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private static final String DATABASE_URL = "jdbc:sqlite:identifier.sqlite";

    // Method to add a new order to the database
    public void addOrder(Order order) {
        String query = "INSERT INTO orders (total_price, status) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setDouble(1, order.getTotalPrice());
            stmt.setString(2, order.getStatus().toString());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the status of an existing order
    public void updateOrderStatus(int orderId, Order.Status status) {
        String query = "UPDATE orders SET status = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, status.toString());
            stmt.setInt(2, orderId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve orders by their status
    public List<Order> getOrdersByStatus(Order.Status status) {
        String query = "SELECT * FROM orders WHERE status = ?";
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, status.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(new Order(
                            null,
                            rs.getDouble("total_price"),
                            Order.Status.valueOf(rs.getString("status").toUpperCase())
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // method to delete an order by its ID
    public void deleteOrder(int orderId) {
        String query = "DELETE FROM orders WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, orderId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
