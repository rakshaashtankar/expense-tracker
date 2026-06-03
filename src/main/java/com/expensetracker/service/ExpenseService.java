package com.expensetracker.service;

import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExpenseService {

    private final List<Expense> expenseList = new ArrayList<>();

    private static int count = 1;


    private boolean isExpenseValid(Expense expense) {
        if(expense == null) return false;
       return expense.getAmount() >0
               && expense.getDate() !=null
               && !expense.getDate().isAfter(LocalDate.now())
               && expense.getDescription() != null
               && !expense.getDescription().isBlank();
    }

    public boolean addExpense(Expense newExpense){
        if(!isExpenseValid(newExpense)) return false;
        Expense addExpense = new Expense(count++, newExpense.getAmount(), newExpense.getCategory(), newExpense.getDate(), newExpense.getDescription());
        expenseList.add(addExpense);
        return true;
    }

    public List<Expense> viewExpenses() {
        return new ArrayList<>(expenseList);
    }

    public boolean deleteExpense(int id) {
        if(id <= 0) {
            return false;
        }
        if(expenseList.isEmpty()) {
            return false;
        }
        return expenseList.removeIf(row -> row.getId() == id);
    }

    public boolean updateExpense(int id, Expense updatedExpense) {
        if(!isExpenseValid(updatedExpense)) return false;
        Expense expenseSearch = expenseList.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
        if(expenseSearch == null) {
            return false;
        }
        expenseSearch.setAmount(updatedExpense.getAmount());
        expenseSearch.setCategory(updatedExpense.getCategory());
        expenseSearch.setDate(updatedExpense.getDate());
        expenseSearch.setDescription(updatedExpense.getDescription());
        return true;
    }

    public Expense searchExpenseById(int id) {
        return expenseList.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Expense> searchExpenseByCategory(String category) {
        return expenseList.stream()
                .filter(e -> e.getCategory() == Category.valueOf(category.toUpperCase()))
                .toList();
    }

    public List<Expense> searchExpenseByDescription(String description) {
        return expenseList.stream()
                .filter(e -> e.getDescription().toLowerCase().contains(description.toLowerCase()))
                .toList();
    }





}
