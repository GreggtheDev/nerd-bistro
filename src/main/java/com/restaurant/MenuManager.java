package com.restaurant;

import com.restaurant.model.MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuManager {

    private static final String DATABASE_URL = "jdbc:sqlite:identifier.sqlite";

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return conn;
    }

    public void addMenuItem(MenuItem item) {
        String sql = "INSERT INTO MenuItems (name, description, prep_time, price, ingredients, category) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.getName());
            pstmt.setString(2, item.getDescription());
            pstmt.setInt(3, item.getPrepTime());
            pstmt.setDouble(4, item.getPrice());
            pstmt.setString(5, String.join(",", item.getIngredients()));
            pstmt.setString(6, item.getCategory());

            pstmt.execute();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<MenuItem> getMenuItems() {
        List<MenuItem> menuItems = new ArrayList<>();
        String sql = "SELECT * FROM MenuItems";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                MenuItem item = new MenuItem(
                        rs.getInt("item_id"),  // Assuming the column is item_id
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("prep_time"),
                        rs.getDouble("price"),
                        List.of(rs.getString("ingredients").split(",")),
                        rs.getString("category")
                );
                menuItems.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return menuItems;
    }

    public void removeMenuItem(String itemName) {
        String sql = "DELETE FROM MenuItems WHERE name = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, itemName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public MenuItem getMenuItemById(int id) {
        String sql = "SELECT * FROM MenuItems WHERE item_id = ?";
        MenuItem menuItem = null;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                menuItem = new MenuItem(
                        rs.getInt("item_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("prep_time"),
                        rs.getDouble("price"),
                        List.of(rs.getString("ingredients").split(",")),
                        rs.getString("category")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return menuItem;
    }

    public MenuItem getMenuItemByName(String name) {
        String sql = "SELECT * FROM MenuItems WHERE name = ?";
        MenuItem menuItem = null;

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                menuItem = new MenuItem(
                        rs.getInt("item_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("prep_time"),
                        rs.getDouble("price"),
                        List.of(rs.getString("ingredients").split(",")),
                        rs.getString("category")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return menuItem;
    }
}
