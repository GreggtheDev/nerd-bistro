package com.restaurant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.io.File;

public class DatabaseInit {

    private static final String URL = "jdbc:sqlite:identifier.sqlite";

    // Establishing a connection to the database
    private static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public DatabaseInit() {
        createNewDatabase();
        createAllTables();
    }

    public static void createNewDatabase() {
        File databaseFile = new File("identifier.sqlite");
        if (!databaseFile.exists()) {
            try (Connection conn = DriverManager.getConnection(URL)) {
                if (conn != null) {
                    System.out.println("A new database has been created.");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Database already exists.");
        }
    }

    // Method to create all necessary tables
    public static void createAllTables() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS Users (\n"
                + "    user_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    username VARCHAR(255) NOT NULL UNIQUE,\n"
                + "    hashed_password VARCHAR(255) NOT NULL,\n"
                + "    role VARCHAR NOT NULL\n"
                + ");";


        String createMenuItemsTable = "CREATE TABLE IF NOT EXISTS MenuItems (\n"
                + "    item_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    name VARCHAR NOT NULL UNIQUE,\n"
                + "    description VARCHAR NOT NULL,\n"
                + "    prep_time INTEGER NOT NULL,\n"
                + "    ingredients VARCHAR,\n"
                + "    price REAL NOT NULL,\n"
                + "    category TEXT\n"
                + ");";

        String createIngredientsTable = "CREATE TABLE IF NOT EXISTS Ingredients (\n"
                + "    ingredient_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    ingredient_name VARCHAR NOT NULL UNIQUE,\n"
                + "    quantity REAL NOT NULL,\n"
                + "    unit VARCHAR(20) NOT NULL\n"
                + ");";

        String createIngredientsListTable = "CREATE TABLE IF NOT EXISTS IngredientsList (\n"
                + "    item_id INTEGER,\n"
                + "    ingredient_id INTEGER,\n"
                + "    quantity REAL NOT NULL,\n"
                + "    PRIMARY KEY (item_id, ingredient_id),\n"
                + "    FOREIGN KEY (item_id) REFERENCES MenuItems(item_id) ON DELETE CASCADE,\n"
                + "    FOREIGN KEY (ingredient_id) REFERENCES Ingredients(ingredient_id) ON DELETE CASCADE\n"
                + ");";

        String createOrdersTable = "CREATE TABLE IF NOT EXISTS Orders (\n"
                + "    order_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    order_time REAL NOT NULL,\n"
                + "    total_price REAL NOT NULL,\n"
                + "    status VARCHAR NOT NULL\n"
                + ");";

        String createOrderItemsTable = "CREATE TABLE IF NOT EXISTS OrderItems (\n"
                + "    order_item_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    order_id INTEGER NOT NULL,\n"
                + "    item_id INTEGER NOT NULL,\n"
                + "    quantity INTEGER NOT NULL,\n"
                + "    FOREIGN KEY (order_id) REFERENCES Orders(order_id) ON DELETE CASCADE,\n"
                + "    FOREIGN KEY (item_id) REFERENCES MenuItems(item_id) ON DELETE CASCADE\n"
                + ");";

        String createTablesTable = "CREATE TABLE IF NOT EXISTS Tables (\n"
                + "    table_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    table_size INTEGER NOT NULL,\n"
                + "    status VARCHAR(20) NOT NULL CHECK(status IN ('available', 'reserved', 'occupied'))\n"
                + ");";

        String createTablesReservationsTable = "CREATE TABLE IF NOT EXISTS Reservations (\n"
                + "    table_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    table_size INTEGER NOT NULL,\n"
                + "    party_size INTEGER NOT NULL,\n"
                + "    reservation_name VARCHAR NOT NULL,\n"
                + "    reservation_time REAL NOT NULL\n"
                + ");";

        String createTablesSpecialOffersTable = "CREATE TABLE IF NOT EXISTS special_offers (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    offer_name VARCHAR NOT NULL,\n"
                + "    discount_amount INTEGER NOT NULL,\n"
                + "    start_date VARCHAR NOT NULL,\n"
                + "    end_date VARCHAR NOT NULL\n"
                + ");";

        String createIngredientUsageTable = "CREATE TABLE IF NOT EXISTS IngredientUsage (\n"
                + "    usage_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    ingredient_id INTEGER NOT NULL,\n"
                + "    order_id INTEGER NOT NULL,\n"
                + "    quantity_used REAL NOT NULL,\n"
                + "    FOREIGN KEY (ingredient_id) REFERENCES Ingredients(ingredient_id) ON DELETE CASCADE,\n"
                + "    FOREIGN KEY (order_id) REFERENCES Orders(order_id) ON DELETE CASCADE\n"
                + ");";

        String createSalesReportsTable = "CREATE TABLE IF NOT EXISTS SalesReports (\n"
                + "    report_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    report_date DATE NOT NULL,\n"
                + "    total_revenue REAL NOT NULL,\n"
                + "    most_popular_items TEXT,\n"
                + "    tables_with_most_orders TEXT\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            // Execute the SQL statements to create tables
            stmt.execute(createUsersTable);
            stmt.execute(createMenuItemsTable);
            stmt.execute(createIngredientsTable);
            stmt.execute(createIngredientsListTable);
            stmt.execute(createOrdersTable);
            stmt.execute(createOrderItemsTable);
            stmt.execute(createTablesTable);
            stmt.execute(createTablesReservationsTable);
            stmt.execute(createTablesSpecialOffersTable);
            stmt.execute(createIngredientUsageTable);
            stmt.execute(createSalesReportsTable);

            System.out.println("All tables are ready.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
