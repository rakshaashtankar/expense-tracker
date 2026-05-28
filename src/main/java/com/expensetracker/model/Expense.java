package com.expensetracker.model;

import com.expensetracker.model.Category;

import java.time.LocalDate;

public class Expense {

    private int id;
    private double amount;
    private Category category;
    private LocalDate date;
    private String description;

    public Expense(int id, double amount, Category category, LocalDate date, String description) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
    }
    public Expense(double amount, Category category, LocalDate date, String description) {
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "[Id: "+ this.id + ", Amount: " + this.amount+ ", Category: "+ this.category + ", Date: "+this.date+ ", Description: "+this.description+ "]";
    }

}
