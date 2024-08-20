# Restaurant Management System

## Overview

The Restaurant Management System is a command-line interface (CLI) application developed in Java, designed to manage various aspects of restaurant operations. This system allows for efficient management of menus, orders, tables, staff, reservations, and special offers. The application is enhanced with color-coded CLI output, sound notifications, and integrates with an SQLite database for data persistence.

## Features

### 1. User Login and Role-Based Permissions
- **Secure Login:** Users can log in using a username and password.
- **Role-Based Access:** Differentiates between staff and manager roles, providing different levels of access.
- **Password Security:** Passwords are hashed before storage to ensure security.

### 2. Menu Management
- **CRUD Operations:** Add, edit, remove, and view menu items.
- **Persistence:** Menu data is stored in the SQLite database for persistence across sessions.

### 3. Order Processing
- **Order Management:** Handle customer orders with real-time status updates.
- **Order Details:** Manage order items, quantities, and total price.

### 4. Table Management
- **Table Status:** Manage table availability (available, reserved, occupied).
- **Assignments:** Assign customers to specific tables.

### 5. Staff Management
- **CRUD Operations:** Manage staff information, including roles and hours worked.
- **Role Management:** Add, delete, and edit staff information.

### 6. Reservations
- **Reservation Handling:** Manage table reservations with customer details and reservation times.
- **Integration:** Reservations are linked to specific tables.

### 7. Special Offers
- **Offer Management:** Create and manage special offers, such as discounts or meal deals.
- **Application:** Apply special offers to customer orders.

### 8. Sales Reporting
- **Daily Reports:** Generate and export daily sales reports.
- **Detailed Insights:** Reports include total revenue, popular items, and table activity.

### 9. CLI Enhancements
- **Color-Coded Output:** Enhance user experience with color-coded messages in the CLI.
- **Sound Notifications:** Use sound to alert users of specific actions (e.g., order placed).

### 10. Database Integration
- **SQLite Database:** All data is persisted in an SQLite database, ensuring durability and consistency.
- **DAO Pattern:** Data access is managed through a Data Access Object (DAO) layer.

## Installation

### Prerequisites
- Java 8 or higher
- SQLite (embedded, no additional installation required)
- Maven (optional, for building the project)

### Setup

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/yourusername/restaurant-management-system.git
   cd restaurant-management-system
   ```

2. **Set Up the Database:**
   - Run the `schema.sql` and `data.sql` scripts in the `resources/db/` folder to create the database schema and seed data.
     ```bash
     sqlite3 restaurant.db < resources/db/schema.sql
     sqlite3 restaurant.db < resources/db/data.sql
     ```

3. **Configure Database Connection:**
   - The SQLite database file is named `restaurant.db` and is located in the project root. No additional configuration is required unless you want to change the database file location.

4. **Build the Project (optional):**
   - If using Maven, build the project:
   ```bash
   mvn clean install
   ```

5. **Run the Application:**
   - Run the `Main.java` file from your IDE or command line.
   ```bash
   java -cp target/restaurant-management-system-1.0-SNAPSHOT.jar com.restaurant.Main
   ```

## Usage

- **Login:** Start by logging in with a username and password.
- **Menu Management:** Access the menu management options to add, edit, or remove menu items.
- **Order Processing:** Create and manage customer orders.
- **Table Management:** Check and update table statuses.
- **Staff Management:** Manage staff records, including adding, editing, or deleting staff.
- **Reservations:** Handle table reservations with customer details.
- **Special Offers:** Manage and apply special offers to customer orders.
- **Sales Reporting:** Generate daily sales reports and export them to a file.

## Project Structure

```
/RestaurantManagementSystem
│
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── com
│   │   │   │   ├── restaurant
│   │   │   │   │   ├── Main.java
│   │   │   │   │   ├── utility
│   │   │   │   │   │   ├── ColorUtility.java
│   │   │   │   │   │   ├── SoundUtility.java
│   │   │   │   │   ├── model
│   │   │   │   │   │   ├── User.java
│   │   │   │   │   │   ├── MenuItem.java
│   │   │   │   │   │   ├── Order.java
│   │   │   │   │   │   ├── Table.java
│   │   │   │   │   │   ├── Staff.java
│   │   │   │   │   │   ├── Reservation.java
│   │   │   │   │   │   ├── SpecialOffer.java
│   │   │   │   │   ├── service
│   │   │   │   │   │   ├── LoginService.java
│   │   │   │   │   │   ├── MenuService.java
│   │   │   │   │   │   ├── OrderService.java
│   │   │   │   │   │   ├── TableService.java
│   │   │   │   │   │   ├── StaffService.java
│   │   │   │   │   │   ├── ReservationService.java
│   │   │   │   │   │   ├── SpecialOfferService.java
│   │   │   │   │   │   ├── CustomerOrderingService.java
│   │   │   │   │   │   ├── SalesReportService.java
│   │   │   │   │   ├── dao
│   │   │   │   │   │   ├── DatabaseConnection.java
│   │   │   │   │   │   ├── UserDao.java
│   │   │   │   │   │   ├── MenuItemDao.java
│   │   │   │   │   │   ├── OrderDao.java
│   │   │   │   │   │   ├── TableDao.java
│   │   │   │   │   │   ├── StaffDao.java
│   │   │   │   │   │   ├── ReservationDao.java
│   │   │   │   │   │   ├── SpecialOfferDao.java
└── resources
    └── db
        ├── schema.sql
        ├── data.sql
```

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature-branch`).
5. Open a Pull Request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Special thanks to all contributors and the Java development community for their continuous support.
