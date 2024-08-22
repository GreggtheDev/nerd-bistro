package com.restaurant.menus;

import com.restaurant.DatabaseInit;
import com.restaurant.model.User;

import java.util.Scanner;

public class MainCLI {

    private static Scanner scanner = new Scanner(System.in);
    private static LoginCLI loginCLI = new LoginCLI();
    private static MenuManagementCLI menuManagementCLI = new MenuManagementCLI();
    private static OrderManagementCLI orderManagementCLI = new OrderManagementCLI();
    private static InventoryCLI inventoryCLI = new InventoryCLI();

    public static void main(String[] args) {
        DatabaseInit databaseInit = new DatabaseInit();
        User currUser = loginCLI.loginMenu();

        if (currUser != null) {
            if (currUser.getRole().equalsIgnoreCase("manager")) {
                boolean running = true;

                while (running) {
                    System.out.println("Restaurant Management System");
                    System.out.println("1. Menu Management");
                    System.out.println("2. Order Management");
                    System.out.println("3. Sales Report");
                    System.out.println("4. Table Management");
                    System.out.println("5. Inventory Management");
                    System.out.println("0. Exit");
                    System.out.print("Select an option: ");
                    int option = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (option) {
                        case 0:
                            running = false;
                            break;
                        case 1:
                            menuManagementCLI.startMenuManagement();
                            break;
                        case 2:
                            orderManagementCLI.startOrderManagement();
                            break;
                        case 3:
                            System.out.println("Sales stuff");
                            break;
                        case 4:
                            System.out.println("table stuff");
                            break;
                        case 5:
                            inventoryCLI.showMenu();


                        default:
                            System.out.println("Invalid option. Please try again.");
                            break;
                    }
                }
            } else if (currUser.getRole().equalsIgnoreCase("staff")) {
                boolean running = true;

                while (running) {
                    System.out.println("Restaurant Management System");
                    System.out.println("1. Menu Management");
                    System.out.println("2. Order Management");
                    System.out.println("3. Table Management");
                    System.out.println("4. Get Inventory");
                    System.out.println("0. Exit");
                    System.out.print("Select an option: ");
                    int option = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (option) {
                        case 0:
                            running = false;
                            break;
                        case 1:
                            menuManagementCLI.startMenuManagement();
                            break;
                        case 2:
                            orderManagementCLI.startOrderManagement();
                            break;


                        default:
                            System.out.println("Invalid option. Please try again.");
                            break;
                    }
                }
            }
        }
    }
}
