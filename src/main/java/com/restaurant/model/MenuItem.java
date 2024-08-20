package com.restaurant.model;

import java.util.List;

public class MenuItem {
    private String name;
    private String description;
    private int prepTime;
    private double price;
    private List<String> ingredients;
    private String category;  // New field for category

    // Updated constructor to include category
    public MenuItem(String name, String description, int prepTime, double price, List<String> ingredients, String category) {
        this.name = name;
        this.description = description;
        this.prepTime = prepTime;
        this.price = price;
        this.ingredients = ingredients;
        this.category = category;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Updated toString method for better formatting
    @Override
    public String toString() {
        return String.format(
                "Category: %s\nName: %s\nDescription: %s\nPreparation Time: %d minutes\nPrice: $%.2f\nIngredients: %s\n",
                category, name, description, prepTime, price, ingredients);
    }
}