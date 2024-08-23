package com.restaurant;

import com.restaurant.dao.InventoryDAO;
import com.restaurant.model.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryManagerTest {

    private InventoryManager inventoryManager;
    private InventoryDAO mockInventoryDAO;

    @BeforeEach
    void setUp() {
        // Create a mock InventoryDAO
        mockInventoryDAO = Mockito.mock(InventoryDAO.class);

        // Create an InventoryManager with the mock DAO
        inventoryManager = new InventoryManager(mockInventoryDAO);
    }

    @Test
    void testProcessOrder_IngredientNotFound() {
        // Arrange: Set up the mock to return null when getting an ingredient by ID
        when(mockInventoryDAO.getIngredientById(1)).thenReturn(null);

        // Act: Call the processOrder method
        inventoryManager.processOrder(1, 5, 10);

        // Assert: Verify that "Ingredient not found." is printed (we assume some logging mechanism in real use)
        verify(mockInventoryDAO, never()).updateIngredientQuantity(anyInt(), anyInt());
        verify(mockInventoryDAO, never()).checkLowStock(anyInt(), anyInt());
    }

    @Test
    void testProcessOrder_NotEnoughStock() {
        // Arrange: Set up the mock to return an ingredient with insufficient stock
        Ingredient ingredient = new Ingredient(1, "Tomato", 3, "kg");
        when(mockInventoryDAO.getIngredientById(1)).thenReturn(ingredient);

        // Act: Call the processOrder method
        inventoryManager.processOrder(1, 5, 10);

        // Assert: Verify that the error message is printed (mocking or capturing print statements could be considered)
        verify(mockInventoryDAO, never()).updateIngredientQuantity(anyInt(), anyInt());
        verify(mockInventoryDAO, never()).checkLowStock(anyInt(), anyInt());
    }

    @Test
    void testProcessOrder_SufficientStock() {
        // Arrange: Set up the mock to return an ingredient with sufficient stock
        Ingredient ingredient = new Ingredient(1, "Tomato", 10, "kg");
        when(mockInventoryDAO.getIngredientById(1)).thenReturn(ingredient);

        // Act: Call the processOrder method
        inventoryManager.processOrder(1, 5, 10);

        // Assert: Verify that the ingredient quantity was updated and low stock was checked
        verify(mockInventoryDAO, times(1)).updateIngredientQuantity(1, 5);
        verify(mockInventoryDAO, times(1)).checkLowStock(1, 10);
    }

    @Test
    void testProcessOrder_ExactStock() {
        // Arrange: Set up the mock to return an ingredient with exactly the required stock
        Ingredient ingredient = new Ingredient(1, "Tomato", 5, "kg");
        when(mockInventoryDAO.getIngredientById(1)).thenReturn(ingredient);

        // Act: Call the processOrder method
        inventoryManager.processOrder(1, 5, 10);

        // Assert: Verify that the ingredient quantity was updated to 0 and low stock was checked
        verify(mockInventoryDAO, times(1)).updateIngredientQuantity(1, 0);
        verify(mockInventoryDAO, times(1)).checkLowStock(1, 10);
    }
}

