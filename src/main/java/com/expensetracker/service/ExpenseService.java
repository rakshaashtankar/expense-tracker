package com.expensetracker.service;

import com.expensetracker.exception.ExpenseNotFoundException;
import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExpenseService {

    private final List<Expense> expenseList = new ArrayList<>();

    private static int count = 1;


    private void validateExpense(Expense expense) {
        if(expense == null) {
            throw new IllegalArgumentException("Expense must not be null.");
        };
        if(expense.getAmount() <= 0) {
            throw new IllegalArgumentException("Expense amount must be greater than 0.");
        };
        if(expense.getDate() ==null) {
            throw new IllegalArgumentException("Expense date must not be null.");
        }
        if(expense.getDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Expense date cannot be future dated.");
        }
        if(expense.getDescription() == null || expense.getDescription().isBlank()) {
            throw new IllegalArgumentException("Expense description cannot be null or blank.");
        }
        if(expense.getCategory() == null)  {
            throw new IllegalArgumentException("Expense category cannot be null.");
        }

    }

    public void addExpense(Expense newExpense){
        validateExpense(newExpense);
        Expense addExpense = new Expense(count++, newExpense.getAmount(), newExpense.getCategory(), newExpense.getDate(), newExpense.getDescription());
        expenseList.add(addExpense);
    }

    public List<Expense> viewExpenses() {
        return new ArrayList<>(expenseList);
    }

    public void deleteExpense(int id) {
        if(id <= 0) {
            throw new IllegalArgumentException("Expense Id should be greater than 0.");
        }
        boolean isRemoved = expenseList.removeIf(row -> row.getId() == id);
        if(!isRemoved) {
            throw new ExpenseNotFoundException("Expense does not exist with id " + id + ".");
        }
    }

    public void updateExpense(int id, Expense updatedExpense) {
        if(id <= 0) {
            throw new IllegalArgumentException("Expense id should be greater than 0.");
        }
        validateExpense(updatedExpense);
        Expense expenseSearch = expenseList.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found with id " + id));
        expenseSearch.setAmount(updatedExpense.getAmount());
        expenseSearch.setCategory(updatedExpense.getCategory());
        expenseSearch.setDate(updatedExpense.getDate());
        expenseSearch.setDescription(updatedExpense.getDescription());
    }

    public Optional<Expense> searchExpenseById(int id) {
        if(id <= 0) {
            throw new IllegalArgumentException("Expense id should be greater than 0.");
        }
        return expenseList.stream()
                .filter(e -> e.getId() == id)
                .findFirst();
    }

    public List<Expense> searchExpenseByCategory(String category) {
        return  expenseList.stream()
                .filter(e -> e.getCategory() == Category.valueOf(category.toUpperCase()))
                .toList();
    }

    public List<Expense> searchExpenseByDescription(String description) {
        return expenseList.stream()
                .filter(e -> e.getDescription().toLowerCase().contains(description.toLowerCase()))
                .toList();
    }



}
