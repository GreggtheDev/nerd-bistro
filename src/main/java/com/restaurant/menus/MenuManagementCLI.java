package com.restaurant.menus;

import com.restaurant.MenuManager;
import com.restaurant.model.MenuItem;

import java.util.List;
import java.util.Scanner;

public class MenuManagementCLI {
    private static MenuManager menuManager = new MenuManager();
    private static Scanner scanner = new Scanner(System.in);

    public void startMenuManagement() {
        boolean managingMenu = true;

        while (managingMenu) {
            System.out.println("\nMenu Management");
            System.out.println("1. View Menu");
            System.out.println("2. Add Menu Item");
            System.out.println("3. Remove Menu Item");
            System.out.println("4. Get Menu Item by Name");
            System.out.println("5. Back to Main Menu");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    viewMenu();
                    break;
                case 2:
                    addMenuItem();
                    break;
                case 3:
                    removeMenuItem();
                    break;
                case 4:
                    getMenuItemByName();
                    break;
                case 5:
                    managingMenu = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void viewMenu() {
        List<MenuItem> menuItems = menuManager.getMenuItems();
        for (MenuItem item : menuItems) {
            System.out.println(item);
        }
    }

    private void addMenuItem() {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter preparation time (in minutes): ");
        int prepTime = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter ingredients (comma-separated): ");
        String ingredientsInput = scanner.nextLine();
        List<String> ingredients = List.of(ingredientsInput.split(","));

        System.out.print("Enter category (Breakfast, Lunch, Dinner): ");
        String category = scanner.nextLine();

        MenuItem newItem = new MenuItem(name, description, prepTime, price, ingredients, category);
        menuManager.addMenuItem(newItem);
        System.out.println("Menu item added successfully.");
    }

    private void removeMenuItem() {
        System.out.print("Enter the name of the menu item to remove: ");
        String itemName = scanner.nextLine();
        menuManager.removeMenuItem(itemName);
        System.out.println("Menu item removed successfully.");
    }

    private void getMenuItemByName() {
        System.out.print("Enter the name of the menu item to view: ");
        String itemName = scanner.nextLine();
        MenuItem item = menuManager.getMenuItemByName(itemName);

        if (item != null) {
            System.out.println(item);
        } else {
            System.out.println("Menu item not found.");
        }
    }
}
