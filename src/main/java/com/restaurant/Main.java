package com.restaurant;

import com.restaurant.dao.InventoryDAO;
import com.restaurant.service.*;
import com.restaurant.utility.ColorUtility;
import com.restaurant.utility.SoundUtility;

public class Main {
    public static void main(String[] args) {
//        DatabaseInit databaseInit = new DatabaseInit();

        InventoryDAO inventoryDAO = new InventoryDAO();
        InventoryManager inventoryManager = new InventoryManager(inventoryDAO);

        inventoryManager.processOrder(1, 5, 10);
    }
}

