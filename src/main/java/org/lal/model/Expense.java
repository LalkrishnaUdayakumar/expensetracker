package org.lal.model;

import java.time.LocalDate;
import java.util.UUID;

public class Expense {
    private String id;
    private double amount;
    private String category;
    private LocalDate date;
    private String description;

    public Expense(double amount, String category, LocalDate date, String description) {
        this.id = UUID.randomUUID().toString(); // Generate a unique ID
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    // Constructor with existing ID (for loading from file)
    public Expense(String id, double amount, String category, LocalDate date, String description) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%s - ₹%.2f - %s", date, amount, category);
    }
}