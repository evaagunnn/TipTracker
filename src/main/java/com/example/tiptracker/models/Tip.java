package com.example.tiptracker.models;

import java.time.LocalDate;

public class Tip {
    private LocalDate date;
    private double amount;
    private String notes;

    // Constructor
    public Tip(LocalDate date, double amount, String notes) {
        this.date = date;
        this.amount = amount;
        this.notes = notes;
    }

    // Getters and Setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
