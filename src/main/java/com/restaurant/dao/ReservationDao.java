package com.restaurant.dao;

import com.restaurant.model.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The ReservationDao class provides data access functionality for managing reservations
 * in the restaurant management system. It includes methods to add, remove, and retrieve
 * reservations from the database.
 */
public class ReservationDao {

    /**
     * Adds a new reservation to the database.
     *
     * @param reservation The Reservation object containing the details to be added.
     */
    public void addReservation(Reservation reservation) {
        String query = "INSERT INTO reservations (customer_name, reservation_time, table_id) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set the customer name, reservation time, and table ID parameters for the prepared statement
            stmt.setString(1, reservation.getCustomerName());
            stmt.setString(2, reservation.getReservationTime().toString());
            stmt.setInt(3, reservation.getTableId());

            // Execute the insert statement
            stmt.executeUpdate();

        } catch (SQLException e) {
            // Print the stack trace if an SQL exception occurs
            e.printStackTrace();
        }
    }

    /**
     * Removes an existing reservation from the database by its ID.
     *
     * @param reservationId The ID of the reservation to be removed.
     */
    public void removeReservation(int reservationId) {
        String query = "DELETE FROM reservations WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set the reservation ID parameter for the prepared statement
            stmt.setInt(1, reservationId);

            // Execute the delete statement
            stmt.executeUpdate();

        } catch (SQLException e) {
            // Print the stack trace if an SQL exception occurs
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all reservations from the database.
     *
     * @return A list of Reservation objects representing all reservations in the database.
     */
    public List<Reservation> getAllReservations() {
        String query = "SELECT * FROM reservations";
        List<Reservation> reservations = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Iterate through the result set and create Reservation objects for each record
            while (rs.next()) {
                reservations.add(new Reservation(
                        rs.getInt("id"),
                        rs.getString("customer_name"),
                        rs.getTimestamp("reservation_time").toLocalDateTime(),
                        rs.getInt("table_id")
                ));
            }

        } catch (SQLException e) {
            // Print the stack trace if an SQL exception occurs
            e.printStackTrace();
        }
        return reservations;
    }
}

