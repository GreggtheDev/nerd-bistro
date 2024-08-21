package com.restaurant.service;

import com.restaurant.dao.OrderDao;
import com.restaurant.model.Order;
import com.restaurant.model.MenuItem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SalesReportService {

    private OrderDao orderDao;
    private Map<String, Double> costPriceMap;

    // Constructor to initialize the OrderDao and cost price map
    public SalesReportService(OrderDao orderDao) {
        this.orderDao = orderDao;
        this.costPriceMap = initializeCostPriceMap(); // Initialize cost prices
    }

    // Method to initialize the cost price map
    private Map<String, Double> initializeCostPriceMap() {
        Map<String, Double> costPrices = new HashMap<>();
        // Populate the map with MenuItem names and their corresponding cost prices
        costPrices.put("Burger", 3.00);
        costPrices.put("Pizza", 4.50);
        costPrices.put("Pasta", 2.75);
        // Add more items as needed
        return costPrices;
    }

    // Method to generate the daily sales report
    public void generateDailySalesReport(LocalDate date) {
        List<Order> orders = orderDao.getOrdersByStatus(Order.Status.FINISHED);

        double totalRevenue = calculateTotalRevenue(orders);
        double grossProfit = calculateGrossProfit(orders);
        Map<String, Long> popularItems = calculatePopularItems(orders);
        Map<String, Double> popularItemsRevenue = calculatePopularItemsRevenue(orders);
        Map<Integer, Long> tableActivity = calculateTableActivity(orders);

        exportReportToFile(date, totalRevenue, grossProfit, popularItems, popularItemsRevenue, tableActivity);
    }

    // Method to calculate total revenue from the orders
    private double calculateTotalRevenue(List<Order> orders) {
        return orders.stream().mapToDouble(Order::getTotalPrice).sum();
    }

    // Method to calculate gross profit using the cost price map
    private double calculateGrossProfit(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> order.getItems().stream())
                .mapToDouble(item -> {
                    double costPrice = costPriceMap.getOrDefault(item.getName(), 0.0);
                    return item.getPrice() - costPrice;
                })
                .sum();
    }

    // Method to calculate the most popular items
    private Map<String, Long> calculatePopularItems(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(MenuItem::getName, Collectors.counting()));
    }

    // Method to calculate revenue from popular items
    private Map<String, Double> calculatePopularItemsRevenue(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(MenuItem::getName, Collectors.summingDouble(MenuItem::getPrice)));
    }

    // Method to calculate table activity
    private Map<Integer, Long> calculateTableActivity(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(Order::getOrderId, Collectors.counting()));
    }

    // Method to export the report to a text file
    private void exportReportToFile(LocalDate date, double totalRevenue, double grossProfit,
                                    Map<String, Long> popularItems,
                                    Map<String, Double> popularItemsRevenue,
                                    Map<Integer, Long> tableActivity) {
        String fileName = "SalesReport_" + date.toString() + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Sales Report for " + date.toString() + "\n");
            writer.write("==============================\n\n");

            writer.write("Total Revenue: $" + totalRevenue + "\n");
            writer.write("Gross Profit: $" + grossProfit + "\n\n");

            writer.write("Most Popular Items:\n");
            popularItems.forEach((item, count) -> {
                try {
                    writer.write(item + ": " + count + " orders, $" + popularItemsRevenue.get(item) + " revenue\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.write("\n");

            writer.write("Table Activity:\n");
            tableActivity.forEach((tableId, count) -> {
                try {
                    writer.write("Table " + tableId + ": " + count + " orders\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
