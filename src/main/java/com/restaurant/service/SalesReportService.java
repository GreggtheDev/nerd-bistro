package com.restaurant.service;

import com.restaurant.dao.OrderDao;
import com.restaurant.model.Order;
import com.restaurant.model.MenuItem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesReportService {

    private static OrderDao orderDao;

    static {
        orderDao = new OrderDao();
        generateDailySalesReport();
    }

    // Method to generate the daily sales report
    public static void generateDailySalesReport() {
        List<Order> orders = orderDao.getAllOrders();
        double totalRevenue = 0;
        Map<MenuItem, Integer> itemPopularity = new HashMap<>();
        Map<String, Double> tableRevenue = new HashMap<>();

        // Calculate total revenue, item popularity, and table-wise revenue
        for (Order order : orders) {
            totalRevenue += order.getTotalPrice();

            for (MenuItem item : order.getItems()) {
                itemPopularity.put(item, itemPopularity.getOrDefault(item, 0) + item.getQuantity());
            }

            String tableId = "Table " + order.getOrderId();  // Assuming each order is associated with a table
            tableRevenue.put(tableId, tableRevenue.getOrDefault(tableId, 0.0) + order.getTotalPrice());
        }

        // Determine the most popular item and its associated revenue
        MenuItem mostPopularItem = itemPopularity.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        double revenueFromPopularItem = mostPopularItem != null ?
                mostPopularItem.getPrice() * itemPopularity.get(mostPopularItem) : 0.0;

        // Export the sales report to a text file
        exportSalesReport("daily_sales_report.txt", totalRevenue, mostPopularItem, revenueFromPopularItem, tableRevenue);
    }

    // Method to export the sales report to a text file
    private static void exportSalesReport(String reportFileName, double totalRevenue, MenuItem mostPopularItem,
                                          double revenueFromPopularItem, Map<String, Double> tableRevenue) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportFileName))) {
            writer.write("Daily Sales Report\n");
            writer.write("===================\n");
            writer.write("Total Revenue: $" + String.format("%.2f", totalRevenue) + "\n");
            writer.write("Most Popular Item: " + (mostPopularItem != null ? mostPopularItem.getName() : "None") + "\n");
            writer.write("Revenue from Most Popular Item: $" + String.format("%.2f", revenueFromPopularItem) + "\n");
            writer.write("\nTable-wise Revenue:\n");

            for (Map.Entry<String, Double> entry : tableRevenue.entrySet()) {
                writer.write(entry.getKey() + ": $" + String.format("%.2f", entry.getValue()) + "\n");
            }

            writer.write("===================\n");
            writer.write("End of Report\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
