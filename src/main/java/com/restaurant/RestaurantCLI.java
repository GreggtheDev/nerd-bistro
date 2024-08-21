package com.restaurant;

import com.restaurant.dao.OrderDao;
import com.restaurant.model.MenuItem;
import com.restaurant.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RestaurantCLI {

    private static MenuManager menuManager = new MenuManager();
    private static OrderDao orderDao = new OrderDao();  // Using OrderDao
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        DatabaseInit databaseInit = new DatabaseInit();
        boolean running = true;

        while (running) {
            System.out.println("Restaurant Management System");
            System.out.println("1. View Menu");
            System.out.println("2. Add Menu Item");
            System.out.println("3. Remove Menu Item");
            System.out.println("4. Get Menu Item by Name");
            System.out.println("5. Place Order");
            System.out.println("6. Update Order Status");
            System.out.println("7. View Orders by Status");
            System.out.println("8. Delete Order");
            System.out.println("9. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    viewMenu();
                    break;
                case 2:
                    addMenuItem();
                    break;
                case 3:
                    removeMenuItem();
                    break;
                case 4:
                    getMenuItemByName();
                    break;
                case 5:
                    placeOrder();
                    break;
                case 6:
                    updateOrderStatus();
                    break;
                case 7:
                    viewOrdersByStatus();
                    break;
                case 8:
                    deleteOrder();
                    break;
                case 9:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void viewMenu() {
        List<MenuItem> menuItems = menuManager.getMenuItems();
        if (menuItems.isEmpty()) {
            System.out.println("The menu is currently empty.");
        } else {
            for (MenuItem item : menuItems) {
                System.out.println(item);
                System.out.println("------------------------");  // Adds a separator between items
            }
        }
    }

    private static void addMenuItem() {
        System.out.print("Enter item name: ");
        String name = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter preparation time (in minutes): ");
        int prepTime = scanner.nextInt();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter ingredients (comma-separated): ");
        String ingredients = scanner.nextLine();
        System.out.print("Enter category (Breakfast, Lunch, Dinner): ");
        String category = scanner.nextLine();

        MenuItem item = new MenuItem(name, description, prepTime, price, List.of(ingredients.split(",")), category);
        menuManager.addMenuItem(item);
        System.out.println("Menu item added successfully.");
    }

    private static void removeMenuItem() {
        System.out.print("Enter the name of the item to remove: ");
        String name = scanner.nextLine();
        menuManager.removeMenuItem(name);
        System.out.println("Menu item removed successfully.");
    }

    private static void getMenuItemByName() {
        System.out.print("Enter the name of the item: ");
        String name = scanner.nextLine();
        MenuItem item = menuManager.getMenuItemByName(name);
        if (item != null) {
            System.out.println(item);
        } else {
            System.out.println("Item not found.");
        }
    }

    private static void placeOrder() {
        List<MenuItem> items = new ArrayList<>();
        String choice;
        do {
            System.out.print("Enter item name to add to order (or 'done' to finish): ");
            choice = scanner.nextLine();
            if (!choice.equalsIgnoreCase("done")) {
                MenuItem item = menuManager.getMenuItemByName(choice);
                if (item != null) {
                    items.add(item);
                    System.out.println("Item added to order.");
                } else {
                    System.out.println("Item not found.");
                }
            }
        } while (!choice.equalsIgnoreCase("done"));

        if (!items.isEmpty()) {
            double totalPrice = items.stream().mapToDouble(MenuItem::getPrice).sum();
            Order order = new Order(items, totalPrice, Order.Status.WAITING);
            orderDao.addOrder(order);
            System.out.println("Order placed successfully.");
        } else {
            System.out.println("No items in the order. Order not placed.");
        }
    }

    private static void updateOrderStatus() {
        System.out.print("Enter order ID: ");
        int orderId = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter new status (WAITING, PREPARING, READY): ");
        String status = scanner.nextLine();
        orderDao.updateOrderStatus(orderId, Order.Status.valueOf(status.toUpperCase()));
        System.out.println("Order status updated successfully.");
    }

    private static void viewOrdersByStatus() {
        System.out.print("Enter status to filter by (WAITING, PREPARING, READY): ");
        String status = scanner.nextLine();
        List<Order> orders = orderDao.getOrdersByStatus(Order.Status.valueOf(status.toUpperCase()));
        if (orders.isEmpty()) {
            System.out.println("No orders found with the status " + status);
        } else {
            for (Order order : orders) {
                System.out.println(order);
                System.out.println("------------------------");  // Adds a separator between orders
            }
        }
    }

    private static void deleteOrder() {
        System.out.print("Enter order ID to delete: ");
        int orderId = scanner.nextInt();
        orderDao.deleteOrder(orderId);
        System.out.println("Order deleted successfully.");
    }
}
