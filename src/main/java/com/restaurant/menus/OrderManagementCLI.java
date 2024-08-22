package com.restaurant.menus;

import com.restaurant.MenuManager;
import com.restaurant.OrderManager;
import com.restaurant.dao.OrderDao;
import com.restaurant.model.Order;
import com.restaurant.model.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderManagementCLI {
    private OrderManager orderManager;
    private Scanner scanner = new Scanner(System.in);

    // Constructor to initialize OrderManager with its dependencies
    public OrderManagementCLI() {
        OrderDao orderDao = new OrderDao();
        MenuManager menuManager = new MenuManager();
        this.orderManager = new OrderManager(orderDao, menuManager);
    }

    public void startOrderManagement() {
        boolean managingOrders = true;

        while (managingOrders) {
            System.out.println();
            System.out.println("\nOrder Management");
            System.out.println("1. Place Order");
            System.out.println("2. Update Order Status");
            System.out.println("3. View Orders by Status");
            System.out.println("4. View All Open Orders");
            System.out.println("5. Delete Order");
            System.out.println("6. View Finished Orders");
            System.out.println("7. View Orders by Table Number");
            System.out.println("8. Back to Main Menu");
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
                    viewAllOrders();  // Only show open orders
                    break;
                case 5:
                    deleteOrder();
                    break;
                case 6:
                    viewFinishedOrders();
                    break;
                case 7:
                    viewOrdersByTable();
                    break;
                case 8:
                    managingOrders = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void placeOrder() {
        // Display tables without orders
        displayTablesWithoutOrders();

        // Display tables with open orders
        displaySeatedTablesWithOpenOrders();

        // Prompt for table ID
        System.out.print("Enter Table ID: ");
        int tableId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Check if the table already has an open order
        List<Order> openOrdersForTable = orderManager.getOrdersByTable(tableId);
        boolean hasOpenOrder = openOrdersForTable.stream().anyMatch(order -> order.getStatus() != Order.Status.FINISHED);

        if (hasOpenOrder) {
            System.out.print("This table already has an open order. Add to order? (Y/N): ");
            String choice = scanner.nextLine().trim().toUpperCase();
            if (choice.equals("Y")) {
                // Add items to the existing open order
                addToExistingOrder(openOrdersForTable);
                return;
            } else {
                System.out.println("Cancelling operation...");
                return;
            }
        }

        // If no open order or the user chooses to place a new order
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
            Order order = new Order(tableId, orderItems, totalPrice, Order.Status.WAITING);
            orderManager.placeOrder(order);
            System.out.println("Order placed successfully. Total price: $" + totalPrice);
        } else {
            System.out.println("No items ordered.");
        }
    }

    private void addToExistingOrder(List<Order> openOrders) {
        // Assuming there is only one open order per table; otherwise, you could prompt the user to select which order to add to.
        Order existingOrder = openOrders.stream()
                .filter(order -> order.getStatus() != Order.Status.FINISHED)
                .findFirst()
                .orElse(null);

        if (existingOrder == null) {
            System.out.println("No open order found to add items.");
            return;
        }

        List<MenuItem> orderItems = existingOrder.getItems();
        double totalPrice = existingOrder.getTotalPrice();  // Start with the existing total price
        boolean ordering = true;

        // Display the menu before placing an order
        displayMenu();

        while (ordering) {
            System.out.print("Enter the ID of the item to add (or type 0 to finish): ");
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

                // Check if item already exists in the order, update quantity if it does
                boolean itemExists = false;
                for (MenuItem existingItem : orderItems) {
                    if (existingItem.getId() == item.getId()) {
                        existingItem.setQuantity(existingItem.getQuantity() + quantity);
                        itemExists = true;
                        break;
                    }
                }

                // If the item doesn't exist in the order, add it
                if (!itemExists) {
                    orderItems.add(item);
                }

                totalPrice += item.getPrice() * quantity;

                System.out.println("Added to order: " + item.getName() + " x" + quantity);
            } else {
                System.out.println("Item not found. Please try again.");
            }
        }

        // Update the order with the new items, new total price, and change status to PREPARING
        existingOrder.setItems(orderItems); // Update the items in the order
        existingOrder = new Order(existingOrder.getOrderId(), existingOrder.getTableId(), existingOrder.getOrderTime(), totalPrice, Order.Status.PREPARING, orderItems);

        orderManager.updateOrder(existingOrder);  // Persist the updated order to the database

        System.out.println("Items added to the existing order successfully.");
        System.out.println("New total price: $" + totalPrice);
        System.out.println("Updated items:");
        for (MenuItem item : existingOrder.getItems()) {
            System.out.println(item.getName() + " x" + item.getQuantity());
        }
        System.out.println("Order status has been updated to PREPARING.");
    }

    private void displaySeatedTablesWithOpenOrders() {
        List<Order> openOrders = orderManager.getAllOrders();

        System.out.println("\n--- Seated Tables with Open Orders ---");

        boolean hasOpenOrders = false;
        for (Order order : openOrders) {
            if (order.getStatus() != Order.Status.FINISHED) {
                System.out.println("Table ID: " + order.getTableId() + " (Order ID: " + order.getOrderId() + ", Status: " + order.getStatus() + ")");
                hasOpenOrders = true;
            }
        }

        if (!hasOpenOrders) {
            System.out.println("No tables with open orders.");
        }

        System.out.println("---------------------------------------\n");
    }

    private void displayTablesWithoutOrders() {
        List<Order> openOrders = orderManager.getAllOrders();
        List<Integer> tablesWithOrders = new ArrayList<>();

        for (Order order : openOrders) {
            if (order.getStatus() != Order.Status.FINISHED) {
                tablesWithOrders.add(order.getTableId());
            }
        }

        System.out.println("\n--- Tables Without Open Orders ---");
        for (int i = 1; i <= 10; i++) { // Assuming 10 tables; adjust based on your setup
            if (!tablesWithOrders.contains(i)) {
                System.out.println("Table ID: " + i);
            }
        }
        System.out.println("---------------------------------\n");
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
        System.out.println("\n--- Open Orders ---");
        for (Order order : orders) {
            if (order.getStatus() != Order.Status.FINISHED) {
                System.out.println(order);
            }
        }
        System.out.println("-------------------\n");
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

    private void viewOrdersByTable() {
        System.out.print("Enter the Table ID to view orders: ");
        int tableId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        List<Order> orders = orderManager.getOrdersByTable(tableId);

        if (orders.isEmpty()) {
            System.out.println("No orders found for Table ID: " + tableId);
        } else {
            System.out.println("Orders for Table ID: " + tableId);
            for (Order order : orders) {
                System.out.println(order);
            }
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
