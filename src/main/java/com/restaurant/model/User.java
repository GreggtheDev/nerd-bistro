package com.restaurant.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** This is a POJO for User in nerdBistro
 *
 * @author cjgarcia12
 */

public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private String userDOA;

    public User(int id, String username, String password, String role) {
        this.id = id;
        if (userDOA.isUsernameTaken(username) == true) {
            this.username = username;
        } else {
            System.out.println("User " + username + " is already taken");
        }
        this.password = hashPassword(password);
        this.role = role;
    }

    public User() {
        System.out.println("Please enter a valid username");

    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public static String hashPassword(String password) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
