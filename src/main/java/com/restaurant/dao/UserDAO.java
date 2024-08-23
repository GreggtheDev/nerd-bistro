package com.restaurant.dao;

import com.restaurant.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.restaurant.model.User.hashPassword;


public class UserDAO {
    private static final String DATABASE_URL = "jdbc:sqlite:identifier.sqlite";

//    private Connection connect() {
//        Connection conn = null;
//        try {
//            conn = DriverManager.getConnection(DATABASE_URL);
//        } catch (SQLException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//        return conn;
//    }

    public UserDAO() {

    }

    public void insert(User user) {
        String sql = "INSERT INTO Users(username, role, hashed_password) VALUES(?, ?, ?)";

        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getRole());
            pstmt.setString(3, user.getHashedPassword());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public boolean isUsernameTaken(String username) {
        String sql = "SELECT user_id FROM Users WHERE username = ?";
        boolean isTaken = false;

        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            // If there is at least one result, the username is taken
            if (rs.next()) {
                isTaken = true;
            }
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return isTaken;
    }

    public User validateUser(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username = ? AND hashed_password = ?";
        User user = null;

        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("hashed_password"), rs.getString("role"));
            }
            pstmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return user;
    }

    public List<User> listUsers() {
        String query = "SELECT * FROM Users";
        List<User> userList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String username = rs.getString("username");
                String hashedPassword = rs.getString("hashed_password");
                String role = rs.getString("role");
                userList.add(new User(username, hashedPassword, role));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void deleteUser(String username) {
        String sql = "DELETE FROM Users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User " + username + " deleted successfully.");
            } else {
                System.out.println("User " + username + " not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}