package com.restaurant.menus;

import com.restaurant.MenuManager;
import com.restaurant.dao.InventoryDAO;
import com.restaurant.model.MenuItem;
import com.restaurant.model.Ingredient;

import java.util.List;
import java.util.Scanner;

public class MenuManagementCLI {
    private static MenuManager menuManager = new MenuManager();
    private static InventoryDAO inventoryDAO = new InventoryDAO(); // Added InventoryDAO
    private static Scanner scanner = new Scanner(System.in);

    public void startMenuManagement() {
        boolean managingMenu = true;

        while (managingMenu) {
            System.out.println();
            System.out.println("\nMenu Management");
            System.out.println("1. View Menu");
            System.out.println("2. Add Menu Item");
            System.out.println("3. Remove Menu Item");
            System.out.println("4. Edit Menu Item");
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
                    editMenuItem();
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

        // Add new menu item
        MenuItem newItem = new MenuItem(name, description, prepTime, price, ingredients, category);
        menuManager.addMenuItem(newItem);
        System.out.println("Menu item added successfully.");

        // Populate ingredients in InventoryDAO
        populateIngredientsInInventory(ingredients);
    }

    private void removeMenuItem() {
        System.out.print("Enter the name of the menu item to remove: ");
        String itemName = scanner.nextLine();
        menuManager.removeMenuItem(itemName);
        System.out.println("Menu item removed successfully.");
    }

    private void editMenuItem() {
        System.out.println("Current Menu:");
        viewMenu();  // Display the entire menu

        System.out.print("Enter the ID of the menu item to edit: ");
        int itemId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        MenuItem item = menuManager.getMenuItemById(itemId);

        if (item != null) {
            System.out.println("Editing Menu Item: " + item.getName());
            System.out.println("Enter new values (or 0 to keep the current value):");

            System.out.print("Name (" + item.getName() + "): ");
            String name = scanner.nextLine();
            if (!name.equals("0")) {
                item.setName(name);
            }

            System.out.print("Description (" + item.getDescription() + "): ");
            String description = scanner.nextLine();
            if (!description.equals("0")) {
                item.setDescription(description);
            }

            System.out.print("Preparation Time (" + item.getPrepTime() + " minutes): ");
            int prepTime = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (prepTime != 0) {
                item.setPrepTime(prepTime);
            }

            System.out.print("Price ($" + item.getPrice() + "): ");
            double price = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            if (price != 0) {
                item.setPrice(price);
            }

            System.out.print("Ingredients (" + String.join(", ", item.getIngredients()) + "): ");
            String ingredientsInput = scanner.nextLine();
            if (!ingredientsInput.equals("0")) {
                List<String> ingredients = List.of(ingredientsInput.split(","));
                item.setIngredients(ingredients);
                populateIngredientsInInventory(ingredients); // Populate any new ingredients in inventory
            }

            System.out.print("Category (" + item.getCategory() + "): ");
            String category = scanner.nextLine();
            if (!category.equals("0")) {
                item.setCategory(category);
            }

            menuManager.updateMenuItem(item);
            System.out.println("Menu item updated successfully.");
        } else {
            System.out.println("Menu item not found.");
        }
    }

    // Method to populate ingredients in the InventoryDAO
    private void populateIngredientsInInventory(List<String> ingredients) {
        for (String ingredientName : ingredients) {
            Ingredient existingIngredient = inventoryDAO.getIngredientByName(ingredientName.trim());
            if (existingIngredient == null) {
                // If the ingredient does not exist, add it with a default quantity
                Ingredient newIngredient = new Ingredient(0, ingredientName.trim(), 100, "unit"); // Default quantity and unit
                inventoryDAO.addIngredient(newIngredient);
                System.out.println("Ingredient " + ingredientName + " added to inventory.");
            } else {
                System.out.println("Ingredient " + ingredientName + " already exists in inventory.");
            }
        }
    }
}
