package com.restaurant;

import com.restaurant.service.*;
import com.restaurant.utility.ColorUtility;
import com.restaurant.utility.SoundUtility;

public class Main {
    public static void main(String[] args) {
        LoginService loginService = new LoginService();
        MenuService menuService = new MenuService();
        OrderService orderService = new OrderService();
        TableService tableService = new TableService();
        StaffService staffService = new StaffService();
        ReservationService reservationService = new ReservationService();
        SpecialOfferService specialOfferService = new SpecialOfferService();
        SalesReportService salesReportService = new SalesReportService();
        CustomerOrderingService customerOrderingService = new CustomerOrderingService();

        // Example usage:
        loginService.registerUser("user", "password", "staff");
        if (loginService.login("user", "password")) {
            ColorUtility.printColoredMessage(ColorUtility.GREEN, "Login successful!");
            SoundUtility.playBeep();
        } else {
            ColorUtility.printColoredMessage(ColorUtility.RED, "Login failed!");
        }
    }
}

