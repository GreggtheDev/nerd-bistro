package com.restaurant.model;

import com.restaurant.dao.UserDAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/** This is a POJO for User in nerdBistro
 *
 * @author cjgarcia12
 */

public class User {
    private String username;
    private String password;
    private String role;

    public User(String username, String password, String role) {
        UserDAO userDOA = new UserDAO();
        Scanner scanner = new Scanner(System.in);
        boolean stop = false;
        while (!stop) {
            if (userDOA.isUsernameTaken(username)) {
                System.out.println("User " + username + " is already taken please choose another one");
                username = scanner.nextLine();
            } else {
                this.username = username;
                stop = true;
            }
        }
        boolean stop2 = false;
        while (!stop2) {
            if (role.equals("manager") || role.equals("staff")) {
                this.role = role;
                stop2 = true;
            } else {
                System.out.println("Invalid role, please choose Manager or Staff");
                role = scanner.nextLine();
            }
        }

        this.password = password;
    }

    public User() {
        System.out.println("Please enter a valid username");

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
