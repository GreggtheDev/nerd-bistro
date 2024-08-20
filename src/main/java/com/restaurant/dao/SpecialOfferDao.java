package com.restaurant.dao;

import com.restaurant.model.SpecialOffer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The SpecialOfferDao class provides data access functionality for managing special offers
 * in the restaurant management system. It includes methods to add, remove, and retrieve
 * special offers from the database.
 */
public class SpecialOfferDao {

    /**
     * Adds a new special offer to the database.
     *
     * @param offer The SpecialOffer object containing the details to be added.
     */
    public void addSpecialOffer(SpecialOffer offer) {
        String query = "INSERT INTO special_offers (description, discount_amount) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set the description and discount amount parameters for the prepared statement
            stmt.setString(1, offer.getDescription());
            stmt.setDouble(2, offer.getDiscountAmount());

            // Execute the insert statement
            stmt.executeUpdate();

        } catch (SQLException e) {
            // Print the stack trace if an SQL exception occurs
            e.printStackTrace();
        }
    }

    /**
     * Removes an existing special offer from the database by its ID.
     *
     * @param offerId The ID of the special offer to be removed.
     */
    public void removeSpecialOffer(int offerId) {
        String query = "DELETE FROM special_offers WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set the offer ID parameter for the prepared statement
            stmt.setInt(1, offerId);

            // Execute the delete statement
            stmt.executeUpdate();

        } catch (SQLException e) {
            // Print the stack trace if an SQL exception occurs
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all special offers from the database.
     *
     * @return A list of SpecialOffer objects representing all special offers in the database.
     */
    public List<SpecialOffer> getAllSpecialOffers() {
        String query = "SELECT * FROM special_offers";
        List<SpecialOffer> offers = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Iterate through the result set and create SpecialOffer objects for each record
            while (rs.next()) {
                offers.add(new SpecialOffer(
                        rs.getInt("id"),
                        rs.getString("description"),
                        rs.getDouble("discount_amount")
                ));
            }

        } catch (SQLException e) {
            // Print the stack trace if an SQL exception occurs
            e.printStackTrace();
        }
        return offers;
    }
}

