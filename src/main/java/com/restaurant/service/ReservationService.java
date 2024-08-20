package com.restaurant.service;

import com.restaurant.dao.ReservationDao;
import com.restaurant.model.Reservation;

import java.util.List;

/**
 * The ReservationService class provides business logic for managing reservations
 * in the restaurant management system. It interacts with the ReservationDao to
 * perform CRUD operations on reservations.
 */
public class ReservationService {

    private final ReservationDao reservationDao;

    /**
     * Constructs a ReservationService instance and initializes the ReservationDao
     * for interacting with the database.
     */
    public ReservationService() {
        this.reservationDao = new ReservationDao();
    }

    /**
     * Adds a new reservation to the system by calling the DAO layer.
     *
     * @param reservation The reservation object containing the details to be added.
     */
    public void addReservation(Reservation reservation) {
        reservationDao.addReservation(reservation);
    }

    /**
     * Removes an existing reservation from the system by calling the DAO layer.
     *
     * @param reservationId The ID of the reservation to be removed.
     */
    public void removeReservation(int reservationId) {
        reservationDao.removeReservation(reservationId);
    }

    /**
     * Retrieves all reservations from the system by calling the DAO layer.
     *
     * @return A list of all reservations.
     */
    public List<Reservation> getAllReservations() {
        return reservationDao.getAllReservations();
    }
}

