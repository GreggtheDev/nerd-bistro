package com.restaurant.dao;

import com.restaurant.model.Order;
import com.restaurant.model.MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private static final String DATABASE_URL = "jdbc:sqlite:identifier.sqlite";

    // Method to add a new order to the database
    public void addOrder(Order order) {
        String query = "INSERT INTO orders (table_id, order_time, total_price, status) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, order.getTableId()); // Set table ID
            stmt.setLong(2, order.getOrderTime()); // Add order time
            stmt.setDouble(3, order.getTotalPrice());
            stmt.setString(4, order.getStatus().toString());
            stmt.executeUpdate();

            // Get the generated order ID
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);
                order.setOrderId(orderId);  // Set the orderId back to the order object

                // Add order items to the OrderItems table
                addOrderItems(orderId, order.getItems(), connection);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to add order items to the OrderItems table
    private void addOrderItems(int orderId, List<MenuItem> items, Connection connection) throws SQLException {
        String query = "INSERT INTO OrderItems (order_id, item_id, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (MenuItem item : items) {
                stmt.setInt(1, orderId);
                stmt.setInt(2, item.getId()); // Assuming MenuItem has an id field
                stmt.setInt(3, item.getQuantity()); // Assuming MenuItem has a quantity field
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    // Method to update the status of an existing order
    public void updateOrderStatus(int orderId, Order.Status status) {
        String query = "UPDATE orders SET status = ? WHERE order_id = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, status.toString());
            stmt.setInt(2, orderId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update an existing order in the database
    public void updateOrder(Order order) {
        String query = "UPDATE orders SET table_id = ?, order_time = ?, total_price = ?, status = ? WHERE order_id = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Update the orders table
            stmt.setInt(1, order.getTableId());
            stmt.setLong(2, order.getOrderTime());
            stmt.setDouble(3, order.getTotalPrice());
            stmt.setString(4, order.getStatus().toString());
            stmt.setInt(5, order.getOrderId());
            stmt.executeUpdate();

            // Update the order items in the OrderItems table
            updateOrderItems(order.getOrderId(), order.getItems(), connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method to update order items
    private void updateOrderItems(int orderId, List<MenuItem> items, Connection connection) throws SQLException {
        // First, delete existing items for the order
        String deleteQuery = "DELETE FROM OrderItems WHERE order_id = ?";
        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
            deleteStmt.setInt(1, orderId);
            deleteStmt.executeUpdate();
        }

        // Then, add the updated items
        String insertQuery = "INSERT INTO OrderItems (order_id, item_id, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
            for (MenuItem item : items) {
                insertStmt.setInt(1, orderId);
                insertStmt.setInt(2, item.getId());
                insertStmt.setInt(3, item.getQuantity());
                insertStmt.addBatch();
            }
            insertStmt.executeBatch();
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
                    int orderId = rs.getInt("order_id");
                    int tableId = rs.getInt("table_id"); // Retrieve table ID
                    List<MenuItem> items = getOrderItems(orderId, connection); // Fetch items for each order
                    orders.add(new Order(
                            orderId,
                            tableId, // Pass table ID to Order constructor
                            rs.getLong("order_time"),
                            rs.getDouble("total_price"),
                            Order.Status.valueOf(rs.getString("status").toUpperCase()),
                            items
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // Method to retrieve all orders
    public List<Order> getAllOrders() {
        String query = "SELECT * FROM orders";
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int tableId = rs.getInt("table_id"); // Retrieve table ID
                List<MenuItem> items = getOrderItems(orderId, connection); // Fetch items for each order
                orders.add(new Order(
                        orderId,
                        tableId, // Pass table ID to Order constructor
                        rs.getLong("order_time"),
                        rs.getDouble("total_price"),
                        Order.Status.valueOf(rs.getString("status").toUpperCase()),
                        items
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // Method to fetch items for a specific order
    private List<MenuItem> getOrderItems(int orderId, Connection connection) throws SQLException {
        String query = "SELECT m.item_id, m.name, m.description, m.prep_time, m.price, m.ingredients, m.category, o.quantity "
                + "FROM MenuItems m JOIN OrderItems o ON m.item_id = o.item_id WHERE o.order_id = ?";
        List<MenuItem> items = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MenuItem item = new MenuItem(
                            rs.getInt("item_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getInt("prep_time"),
                            rs.getDouble("price"),
                            List.of(rs.getString("ingredients").split(",")),
                            rs.getString("category")
                    );
                    item.setQuantity(rs.getInt("quantity")); // Assuming MenuItem has a quantity field
                    items.add(item);
                }
            }
        }
        return items;
    }

    // Method to retrieve orders by table ID
    public List<Order> getOrdersByTable(int tableId) {
        String query = "SELECT * FROM orders WHERE table_id = ?";
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, tableId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int orderId = rs.getInt("order_id");
                    List<MenuItem> items = getOrderItems(orderId, connection); // Fetch items for each order
                    orders.add(new Order(
                            orderId,
                            tableId,
                            rs.getLong("order_time"),
                            rs.getDouble("total_price"),
                            Order.Status.valueOf(rs.getString("status").toUpperCase()),
                            items
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // Method to delete an order by its ID
    public void deleteOrder(int orderId) {
        String query = "DELETE FROM orders WHERE order_id = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, orderId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
