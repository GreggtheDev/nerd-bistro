package com.restaurant.dao;
import com.restaurant.model.Table;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableDao {

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:sqlite:identifier.sqlite";
        return DriverManager.getConnection(jdbcURL);
    }

    public Table getTableById(int tableId) {
        String sql = "SELECT * FROM Tables WHERE table_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, tableId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("table_id");
                int size = resultSet.getInt("table_size");
                String status = resultSet.getString("status");

                return new Table(id, size, status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateTableStatus(int tableId, String status) {
        String sql = "UPDATE Tables SET status = ? WHERE table_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, tableId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
