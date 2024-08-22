package com.restaurant.menus;

import com.restaurant.dao.InventoryDAO;
import com.restaurant.model.Ingredient;

import java.util.Scanner;

public class InventoryCLI {
    private final InventoryDAO inventoryDAO;

    public InventoryCLI() {
        this.inventoryDAO = new InventoryDAO();
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nInventory Management");
            System.out.println("1. View All Ingredients");
            System.out.println("2. View Ingredient by ID");
            System.out.println("3. Update Ingredient Quantity");
            System.out.println("4. Check Low Stock");
            System.out.println("5. Process Order");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewAllIngredients();
                    break;
                case 2:
                    viewIngredientById();
                    break;
                case 3:
                    updateIngredientQuantity();
                    break;
                case 4:
                    checkLowStock();
                    break;
                case 5:
                    processOrder();
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewAllIngredients() {
        inventoryDAO.getIngredients();
    }

    private void viewIngredientById() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Ingredient ID: ");
        int id = scanner.nextInt();
        Ingredient ingredient = inventoryDAO.getIngredientById(id);
        if (ingredient != null) {
            System.out.println(ingredient);
        } else {
            System.out.println("Ingredient not found.");
        }
    }

    private void updateIngredientQuantity() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Ingredient ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter new quantity: ");
        int quantity = scanner.nextInt();
        inventoryDAO.updateIngredientQuantity(id, quantity);
    }

    private void checkLowStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Ingredient ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter low stock threshold: ");
        int threshold = scanner.nextInt();
        inventoryDAO.checkLowStock(id, threshold);
    }

    private void processOrder() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Ingredient ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter quantity used: ");
        int quantityUsed = scanner.nextInt();
        System.out.print("Enter low stock threshold: ");
        int lowThreshold = scanner.nextInt();

        Ingredient ingredient = inventoryDAO.getIngredientById(id);
        if (ingredient == null) {
            System.out.println("Ingredient not found.");
            return;
        }

        int newQuantity = ingredient.getQuantity() - quantityUsed;
        if (newQuantity < 0) {
            System.out.println("Error: Not enough " + ingredient.getName() + " in stock.");
        } else {
            inventoryDAO.updateIngredientQuantity(id, newQuantity);
            System.out.println(ingredient.getName() + " updated. New quantity: " + newQuantity + " " + ingredient.getUnit());
        }

        inventoryDAO.checkLowStock(id, lowThreshold);
    }
}

