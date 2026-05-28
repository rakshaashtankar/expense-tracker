package com.expensetracker.service;

import com.expensetracker.model.Expense;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseService {

    public ExpenseService() {}

    private final List<Expense> expenseList = new ArrayList<>();

    private static int count = 1;

    public String addExpense(Expense newExpense){
        if(newExpense.getAmount() <=0) {
            return "Amount should be greater than 0.";
        }
        if(newExpense.getDate().isAfter(LocalDate.now())) {
            return "The date is in the future";
        }
        if(newExpense.getDescription() == null || newExpense.getDescription().isBlank()) {
            return "Description should not be null or empty.";
        }
        Expense addExpense = new Expense(count++, newExpense.getAmount(), newExpense.getCategory(), newExpense.getDate(), newExpense.getDescription());
        expenseList.add(addExpense);
        return  "Expense added successfully";
    }

    public ArrayList<Expense> viewExpenses() {
        return new ArrayList<>(expenseList);
    }

    public String deleteExpense(int id) {
        if(id <= 0) {
            return "Id should be greater than 0.";
        }
        if(expenseList.isEmpty()) {
            return "Expense list is empty nothing to be deleted.";
        }
        boolean removed = expenseList.removeIf(row -> row.getId() == id);

        if(!removed) {
            return "Expense does not exist with id " + id;
        } else {
            return "Expense with id " + id + " deleted successfully";
        }
    }


}
