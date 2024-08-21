package com.restaurant.menus;

import com.restaurant.MenuManager;
import com.restaurant.OrderManager;
import com.restaurant.model.Order;
import com.restaurant.model.MenuItem;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class OrderManagementCLI {
    private static OrderManager orderManager = new OrderManager();
    private static Scanner scanner = new Scanner(System.in);

    public void startOrderManagement() {
        boolean managingOrders = true;

        while (managingOrders) {
            System.out.println("\nOrder Management");
            System.out.println("1. Place Order");
            System.out.println("2. Update Order Status");
            System.out.println("3. View Orders by Status");
            System.out.println("4. View All Orders");
            System.out.println("5. Delete Order");
            System.out.println("6. View Finished Orders");
            System.out.println("7. Back to Main Menu");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    placeOrder();
                    break;
                case 2:
                    updateOrderStatus();
                    break;
                case 3:
                    viewOrdersByStatus();
                    break;
                case 4:
                    viewAllOrders();
                    break;
                case 5:
                    deleteOrder();
                    break;
                case 6:
                    viewFinishedOrders();
                    break;
                case 7:
                    managingOrders = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void placeOrder() {
        List<MenuItem> orderItems = new ArrayList<>();
        double totalPrice = 0.0;
        boolean ordering = true;

        // Display the menu before placing an order
        displayMenu();

        while (ordering) {
            System.out.print("Enter the ID of the item to order (or type 0 to finish): ");
            int itemId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (itemId == 0) {
                ordering = false;
                continue;
            }

            MenuItem item = new MenuManager().getMenuItemById(itemId);
            if (item != null) {
                System.out.print("Enter quantity for this item: ");
                int quantity = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                item.setQuantity(quantity); // Set the quantity of the item

                orderItems.add(item);
                totalPrice += item.getPrice() * quantity;

                System.out.println("Added to order: " + item.getName() + " x" + quantity);
            } else {
                System.out.println("Item not found. Please try again.");
            }
        }

        if (!orderItems.isEmpty()) {
            Order order = new Order(orderItems, totalPrice, Order.Status.WAITING);
            orderManager.placeOrder(order);
            System.out.println("Order placed successfully. Total price: $" + totalPrice);
        } else {
            System.out.println("No items ordered.");
        }
    }

    private void updateOrderStatus() {
        // Fetch and display all open orders (WAITING or PREPARING)
        List<Order> openOrders = orderManager.getOrdersByStatus(Order.Status.WAITING);
        openOrders.addAll(orderManager.getOrdersByStatus(Order.Status.PREPARING));

        if (openOrders.isEmpty()) {
            System.out.println("There are no open orders to update.");
            return;
        }

        System.out.println("Open Orders:");
        for (Order order : openOrders) {
            System.out.println(order);
        }

        // Prompt the user to select an order ID to update
        System.out.print("Enter the Order ID to update: ");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        // Display the status options and prompt for the new status
        System.out.println("Choose new status: ");
        for (Order.Status status : Order.Status.values()) {
            System.out.println(status.ordinal() + 1 + ". " + status.name());
        }
        int statusChoice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        if (statusChoice >= 1 && statusChoice <= Order.Status.values().length) {
            Order.Status newStatus = Order.Status.values()[statusChoice - 1];
            orderManager.updateOrderStatus(orderId, newStatus);
            System.out.println("Order status updated to " + newStatus.name() + " successfully.");
        } else {
            System.out.println("Invalid status choice. Please try again.");
        }
    }

    private void viewOrdersByStatus() {
        System.out.println("Choose status to filter by: ");
        for (Order.Status status : Order.Status.values()) {
            System.out.println(status.ordinal() + 1 + ". " + status.name());
        }
        int statusChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Order.Status selectedStatus = Order.Status.values()[statusChoice - 1];
        List<Order> orders = orderManager.getOrdersByStatus(selectedStatus);

        if (orders.isEmpty()) {
            System.out.println("No orders with status: " + selectedStatus.name());
        } else {
            for (Order order : orders) {
                System.out.println(order);
            }
        }
    }

    private void viewAllOrders() {
        List<Order> orders = orderManager.getAllOrders();
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    private void deleteOrder() {
        System.out.print("Enter the Order ID to delete: ");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        orderManager.deleteOrder(orderId);
        System.out.println("Order deleted successfully.");
    }

    private void viewFinishedOrders() {
        List<Order> finishedOrders = orderManager.getOrdersByStatus(Order.Status.FINISHED);

        if (finishedOrders.isEmpty()) {
            System.out.println("No finished orders.");
        } else {
            System.out.println("\n--- Finished Orders ---");
            for (Order order : finishedOrders) {
                System.out.println(order);
            }
            System.out.println("-----------------------\n");
        }
    }

    private void displayMenu() {
        System.out.println("\n--- Menu ---");
        List<MenuItem> menuItems = new MenuManager().getMenuItems();
        for (MenuItem item : menuItems) {
            System.out.println(item);
        }
        System.out.println("------------\n");
    }


}