package com.restaurant.menus;

import com.restaurant.dao.UserDAO;
import com.restaurant.model.User;

import java.util.List;
import java.util.Scanner;

public class UserManagementCLI {
    private static Scanner scanner = new Scanner(System.in);
    private static UserDAO userDAO = new UserDAO();

    public void startUserManagement() {
        boolean managingUsers = true;

        while (managingUsers) {
            System.out.println();
            System.out.println("\nUser Management");
            System.out.println("1. Create New User");
            System.out.println("2. List Users");
            System.out.println("3. Delete User");
            System.out.println("0. Back to Main Menu");
            System.out.print("Select an option: ");
            String option = scanner.nextLine().trim();  // Read the entire line and trim any whitespace

            switch (option) {
                case "1":
                    createNewUser();
                    break;
                case "2":
                    listUsers();
                    break;
                case "3":
                    deleteUser();
                    break;
                case "0":
                    managingUsers = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void createNewUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter role (manager/staff): ");
        String role = scanner.nextLine().toLowerCase();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String hashedPassword = User.hashPassword(password);

        // Create the new user with validation
        User newUser = new User(username, hashedPassword, role);
        userDAO.insert(newUser);
        System.out.println("New user created successfully.");
    }

    private void listUsers() {
        List<User> users = userDAO.listUsers();

        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("\nList of Users:");
            for (User user : users) {
                System.out.println("Username: " + user.getUsername() + ", Role: " + user.getRole());
            }
        }
    }

    private void deleteUser() {
        System.out.print("Enter the username of the user to delete: ");
        String username = scanner.nextLine();

        // Check if the user exists
        if (userDAO.isUsernameTaken(username)) {
            userDAO.deleteUser(username);
            System.out.println("User " + username + " has been deleted.");
        } else {
            System.out.println("User not found.");
        }
    }
}
