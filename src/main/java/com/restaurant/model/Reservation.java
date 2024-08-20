package com.restaurant.model;

import java.time.LocalDateTime;

/**
 * This class represents a reservation made by a customer at the restaurant.
 * It keeps track of the reservation details such as the customer name,
 * reservation time, and the table assigned to the reservation.
 */
public class Reservation {

    // The unique identifier for this reservation
    private int id;

    // The name of the customer who made the reservation
    private String customerName;

    // The date and time when the reservation is scheduled
    private LocalDateTime reservationTime;

    // The ID of the table assigned to this reservation
    private int tableId;

    /**
     * Constructor to create a new Reservation with all necessary details.
     *
     * @param id               The unique identifier for this reservation.
     * @param customerName     The name of the customer.
     * @param reservationTime  The time and date of the reservation.
     * @param tableId          The ID of the table reserved.
     */
    public Reservation(int id, String customerName, LocalDateTime reservationTime, int tableId) {
        this.id = id;
        this.customerName = customerName;
        this.reservationTime = reservationTime;
        this.tableId = tableId;
    }

    /**
     * Gets the unique identifier for this reservation.
     *
     * @return The reservation ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the customer who made the reservation.
     *
     * @return The customer's name.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Gets the date and time of the reservation.
     *
     * @return The reservation time.
     */
    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    /**
     * Gets the ID of the table assigned to this reservation.
     *
     * @return The table ID.
     */
    public int getTableId() {
        return tableId;
    }
}

