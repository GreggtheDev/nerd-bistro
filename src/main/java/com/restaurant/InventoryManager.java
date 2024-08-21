package com.restaurant;

import java.sql.SQLException;
import com.restaurant.dao.InventoryDAO;
import com.restaurant.model.Ingredient;

public class InventoryManager {
    private InventoryDAO inventoryDAO;

    public InventoryManager(InventoryDAO inventoryDAO) {
        this.inventoryDAO = inventoryDAO;
    }

    public void processOrder(int ingredientId, int quantityUsed, int lowThreshold) {
        Ingredient ingredient = inventoryDAO.getIngredientById(ingredientId);
        if (ingredient == null) {
            System.out.println("Ingredient not found.");
            return;
        }

        int newQuantity = ingredient.getQuantity() - quantityUsed;
        if (newQuantity < 0) {
            System.out.println("Error: Not enough " + ingredient.getName() + " in stock.");
        } else {
            inventoryDAO.updateIngredientQuantity(ingredientId, newQuantity);
            System.out.println(ingredient.getName() + " updated. New quantity: " + newQuantity + " " + ingredient.getUnit());
        }

        inventoryDAO.checkLowStock(ingredientId, lowThreshold);
    }
}

