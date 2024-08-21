package com.restaurant.menus;

import java.util.Scanner;

public class MainCLI {

    private static Scanner scanner = new Scanner(System.in);
    private static MenuManagementCLI menuManagementCLI = new MenuManagementCLI();
    private static OrderManagementCLI orderManagementCLI = new OrderManagementCLI();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            System.out.println("Restaurant Management System");
            System.out.println("1. Menu Management");
            System.out.println("2. Order Management");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    menuManagementCLI.startMenuManagement();
                    break;
                case 2:
                    orderManagementCLI.startOrderManagement();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
