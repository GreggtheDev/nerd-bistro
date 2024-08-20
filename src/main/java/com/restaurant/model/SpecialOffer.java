package com.restaurant.model;

/**
 * The SpecialOffer class represents a promotional offer in the restaurant management system.
 * It includes details such as the offer ID, a description of the offer, and the discount amount.
 */
public class SpecialOffer {

    // The unique identifier for this special offer
    private int id;

    // A brief description of the special offer
    private String description;

    // The discount amount associated with this special offer
    private double discountAmount;

    /**
     * Constructs a SpecialOffer object with the specified details.
     *
     * @param id             The unique identifier for this offer.
     * @param description    A brief description of the offer.
     * @param discountAmount The discount amount applied by this offer.
     */
    public SpecialOffer(int id, String description, double discountAmount) {
        this.id = id;
        this.description = description;
        this.discountAmount = discountAmount;
    }

    /**
     * Gets the unique identifier for this special offer.
     *
     * @return The offer ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the description of the special offer.
     *
     * @return The offer description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the discount amount of the special offer.
     *
     * @return The discount amount.
     */
    public double getDiscountAmount() {
        return discountAmount;
    }
}

