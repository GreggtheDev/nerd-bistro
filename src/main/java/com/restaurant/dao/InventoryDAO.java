package com.restaurant.dao;

import com.restaurant.model.Ingredient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryDAO {
    private static final String DATABASE_URL = "jdbc:sqlite:identifier.sqlite";

    // Method to get all ingredients
    public void getIngredients() {
        String sql = "SELECT * FROM Ingredients";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("ingredient_id"));
                System.out.println("Name: " + resultSet.getString("ingredient_name"));
                System.out.println("Quantity: " + resultSet.getInt("quantity"));
                System.out.println("Unit: " + resultSet.getString("unit"));
                System.out.println("---------------------------");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to get a single ingredient by ID
    public Ingredient getIngredientById(int ingredientId) {
        String sql = "SELECT * FROM Ingredients WHERE ingredient_id = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, ingredientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Ingredient(
                            resultSet.getInt("ingredient_id"),
                            resultSet.getString("ingredient_name"),
                            resultSet.getInt("quantity"),
                            resultSet.getString("unit")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Method to get a single ingredient by Name
    public Ingredient getIngredientByName(String ingredientName) {
        String sql = "SELECT * FROM Ingredients WHERE ingredient_name = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, ingredientName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Ingredient(
                            resultSet.getInt("ingredient_id"),
                            resultSet.getString("ingredient_name"),
                            resultSet.getInt("quantity"),
                            resultSet.getString("unit")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // Method to add a new ingredient to the inventory
    public void addIngredient(Ingredient ingredient) {
        String sql = "INSERT INTO Ingredients (ingredient_name, quantity, unit) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, ingredient.getName());
            statement.setInt(2, ingredient.getQuantity());
            statement.setString(3, ingredient.getUnit());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to update the quantity of an ingredient
    public void updateIngredientQuantity(int ingredientId, int newQuantity) {
        String sql = "UPDATE Ingredients SET quantity = ? WHERE ingredient_id = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, newQuantity);
            statement.setInt(2, ingredientId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Ingredient quantity updated successfully.");
            } else {
                System.out.println("Ingredient not found.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to check if an ingredient is running low
    public void checkLowStock(int ingredientId, int lowThreshold) {
        Ingredient ingredient = getIngredientById(ingredientId);
        if (ingredient != null && ingredient.getQuantity() <= lowThreshold) {
            System.out.println("Alert: " + ingredient.getName() + " is running low!");
        }
    }
}

