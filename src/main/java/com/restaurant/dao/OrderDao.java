package com.restaurant.dao;



import com.restaurant.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The OrderDao class provides data access functionality for managing orders
 * in the database. It includes methods to add new orders, update order status,
 * and retrieve orders by their status.
 */
public class OrderDao {
    private static final String DATABASE_URL = "jdbc:sqlite:identifier.sqlite";

    /**
     * Adds a new order to the database.
     *
     * @param order The order to be added to the database.
     */
    public void addOrder(Order order) {
        String query = "INSERT INTO orders (total_price, status) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set the total price and status parameters for the prepared statement
            stmt.setDouble(1, order.getTotalPrice());
            stmt.setString(2, order.getStatus().toString());

            // Execute the insert statement
            stmt.executeUpdate();

        } catch (SQLException e) {
            // Print the stack trace if an SQL exception occurs
            e.printStackTrace();
        }
    }

    /**
     * Updates the status of an existing order in the database.
     *
     * @param orderId The ID of the order to be updated.
     * @param status  The new status of the order.
     */
    public void updateOrderStatus(int orderId, String status) {
        String query = "UPDATE orders SET status = ? WHERE order_id = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set the new status and the order ID for the prepared statement
            stmt.setString(1, status);
            stmt.setInt(2, orderId);

            // Execute the update statement
            stmt.executeUpdate();

        } catch (SQLException e) {
            // Print the stack trace if an SQL exception occurs
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a list of orders from the database based on their status.
     *
     * @param status The status of the orders to retrieve.
     * @return A list of orders matching the specified status.
     */
    public List<Order> getOrdersByStatus(String status) {
        String query = "SELECT * FROM orders WHERE status = ?";
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set the status parameter for the prepared statement
            stmt.setString(1, status);

            // Execute the query and process the result set
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Create a new Order object and add it to the list
                    orders.add(new Order(
                            rs.getInt("id"),
                            null, // Items would need to be fetched separately
                            rs.getDouble("total_price"),
                            rs.getString("status")
                    ));
                }
            }

        } catch (SQLException e) {
            // Print the stack trace if an SQL exception occurs
            e.printStackTrace();
        }
        return orders;
    }
}

