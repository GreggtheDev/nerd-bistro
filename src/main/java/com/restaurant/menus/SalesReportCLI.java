package com.restaurant.menus;

import com.restaurant.service.SalesReportService;
import java.util.Scanner;

public class SalesReportCLI {

    private SalesReportService salesReportService = new SalesReportService();
    private Scanner scanner = new Scanner(System.in);

    public void startSalesReport() {
        boolean exit = false;

        while (!exit) {
            System.out.println();
            System.out.println("Sales Report Service:");
            System.out.println("1. Generate Daily Sales Report");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    salesReportService.generateDailySalesReport();
                    System.out.println("Daily Sales Report generated successfully!");
                    break;
                case 2:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }
}
