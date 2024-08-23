package com.restaurant.menus;

import com.restaurant.dao.TableDao;
import com.restaurant.service.TableService;
import com.restaurant.model.Table;

import java.util.List;
import java.util.Scanner;

public class TableCli {
    private TableService tableService;
    private TableDao tableDao;
    private Scanner scanner;
    // Constructor
    public TableCli() {
        tableDao = new TableDao();
        tableService = new TableService();
        scanner = new Scanner(System.in);
    }
    // Method to start the CLI
    public void start() {
        while (true) {
            System.out.println("Table Management CLI");
            System.out.println("1. List all tables");
            System.out.println("2. Set table status");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    listAllTables();
                    break;
                case 2:
                    setTableStatus();
                    break;
                case 3:
                    System.out.println("Exiting CLI.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Method to list all tables with their statuses
    private void listAllTables() {
        List<String> tableStatuses = Table.getAllTablesStatus();

        System.out.println("All Tables:");
        for (String status : tableStatuses) {
            System.out.println(status);
        }
    }
    // Method to set the status of a specific table
    private void setTableStatus() {
        System.out.print("Enter Table ID: ");
        int tableId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter new status (Available, Reserved, Occupied): ");
        String newStatus = scanner.nextLine();

        Table table = tableDao.getTableById(tableId);
        if (table != null) {
            tableDao.updateTableStatus(tableId, newStatus);
            System.out.println("Table " + tableId + " status updated to " + newStatus);
        } else {
            System.out.println("Table with ID " + tableId + " not found.");
        }
    }

    public static void main(String[] args) {
        TableCli cli = new TableCli();
        cli.start();
    }
}