package com.restaurant.service;

import com.restaurant.dao.TableDao;
import com.restaurant.model.Table;

import java.util.List;
public class TableService {
    private TableDao tableDao = new TableDao();

    // Assign a customer to a table by reserving and then occupying it
    public void assignCustomerToTable(int tableId) {
        Table table = tableDao.getTableById(tableId);

        if (table != null && table.isAvailable()) {
            table.reserveTable();
            tableDao.updateTableStatus(tableId, table.getStatus());

            table.occupyTable();
            tableDao.updateTableStatus(tableId, table.getStatus());

            System.out.println("Customer assigned to table " + tableId);
        } else {
            System.out.println("Table " + tableId + " is not available.");
        }
    }

    // Free up a table after customers leave
    public void freeTable(int tableId) {
        Table table = tableDao.getTableById(tableId);

        if (table != null) {
            table.freeTable();
            tableDao.updateTableStatus(tableId, table.getStatus());
            System.out.println("Table " + tableId + " is now available.");
        } else {
            System.out.println("Table " + tableId + " not found.");
        }
    }
}
