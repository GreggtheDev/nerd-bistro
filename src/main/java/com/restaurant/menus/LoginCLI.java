package com.restaurant.menus;

import com.restaurant.DatabaseInit;
import com.restaurant.dao.UserDAO;
import com.restaurant.model.User;

import static com.restaurant.model.User.hashPassword;

import java.util.Scanner;

public class LoginCLI {
    private static final UserDAO userDAO = new UserDAO();

    public LoginCLI() {
        // Empty
    }

    public static int validInput(int input, int range) {
        Scanner scanner = new Scanner(System.in);
        boolean stop = false;

        while (!stop) {
            if (input > range || input < 0) {
                System.out.println("Please enter a valid number between " + range + " and " + range);
                input = scanner.nextInt();
            } else {
                stop = true;
            }
        }
        return input;
    }

    public static User login() {
        Scanner scanner = new Scanner(System.in);
        User user = null;

        boolean stop = false;
        while (!stop) {
            System.out.println("What is your username?");
            String username = scanner.nextLine();
            System.out.println("What is your password?");
            String password = scanner.nextLine();
            user = userDAO.validateUser(username, User.hashPassword(password));
            if (user != null) {
                stop = true;
            } else {
                System.out.println("Invalid username or password");
            }
        }

        return user;
    }

    public static User register() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a new username: ");
        String username = scanner.nextLine();

        boolean stop = false;
        while (!stop) {
            if (userDAO.isUsernameTaken(username)) {
                System.out.println("User " + username + " is already taken please choose another one");
                username = scanner.nextLine();
            } else {
                stop = true;
            }
        }

        System.out.println("What is your role: ");
        String role = scanner.nextLine();

        boolean stop2 = false;
        while (!stop2) {
            if (role.equalsIgnoreCase("manager") || role.equalsIgnoreCase("staff")) {
                stop2 = true;
            } else {
                System.out.println("Invalid role, please choose Manager or Staff");
                role = scanner.nextLine();
            }
        }

        System.out.println("Enter a password: ");
        String password = scanner.nextLine();

        User user = new User(username, hashPassword(password), role);
        userDAO.insert(user);
        return user;

    }

    public void LoginMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you an existing user?");
        System.out.println("1. Yes\n2. No");
        int input = validInput(scanner.nextInt(), 2);
        User user = null;

        switch (input) {
            case 1:
                user = login();
                break;
            case 2:
                user = register();
                break;
        }
        System.out.println(user.toString());


    }
}
