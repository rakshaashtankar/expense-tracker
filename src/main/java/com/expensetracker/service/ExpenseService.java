package com.expensetracker.service;

import com.expensetracker.exception.ExpenseNotFoundException;
import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;
import com.expensetracker.repository.ExpenseRepository;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExpenseService {

    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }



    private void validateExpenseNotNull(Expense expense) {
        if(expense == null) {
            throw new IllegalArgumentException("Expense must not be null.");
        }
    }

    private void validateId(int id){
        if(id <= 0) {
            throw new IllegalArgumentException("Expense id should be greater than 0.");
        }
    }

    private void validateAmount(double amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Expense amount must be greater than 0.");
        }
    }

    private void validateDate(LocalDate date) {
        if(date ==null) {
            throw new IllegalArgumentException("Expense date must not be null.");
        }
        if(date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Expense date cannot be future dated.");
        }
    }

    private void validateDescription(String description) {
        if(description == null || description.isBlank()) {
            throw new IllegalArgumentException("Expense description cannot be null or blank.");
        }
    }

    //Defensive validation
    private void validateCategory(Category category){
        if(category == null)  {
            throw new IllegalArgumentException("Expense category cannot be null.");
        }
    }

    private void validExpense(Expense expense) {
        validateExpenseNotNull(expense);
        validateCategory(expense.getCategory());
        validateAmount(expense.getAmount());
        validateDate(expense.getDate());
        validateDescription(expense.getDescription());

    }

    public void addExpense(Expense newExpense){
        validExpense(newExpense);
        repository.save(newExpense);
    }

    public List<Expense> viewExpenses() {
        return repository.findAll();

    }

    public void deleteExpense(int id) {
        validateId(id);
        boolean isRemoved = repository.deleteById(id);
        if(!isRemoved) {
            throw new ExpenseNotFoundException("Expense does not exist with id " + id + ".");
        }
    }

    public void updateExpense(int id, Expense updatedExpense) {
        validateId(id);
        validExpense(updatedExpense);
        Expense expense = repository.findById(id).orElseThrow(() -> new ExpenseNotFoundException("Expense does not exist with id " + id + "."));
        expense.setAmount(updatedExpense.getAmount());
        expense.setCategory(updatedExpense.getCategory());
        expense.setDate(updatedExpense.getDate());
        expense.setDescription(updatedExpense.getDescription());
    }

    public Expense searchExpenseById(int id) {
        validateId(id);
        return repository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense does not exist with id " + id + "."));
    }

    public List<Expense> searchExpenseByCategory(Category category) {
        validateCategory(category);
        return repository.findByCategory(category);
    }

    public List<Expense> searchExpenseByDescription(String description) {
        validateDescription(description);
        return repository.findByDescription(description);
    }

    public Map<Category, Double> getCategorySummary() {
        List<Expense> allExpenses = viewExpenses();
        Map<Category, Double> categorySummary = new LinkedHashMap<>();
        for(Expense expense: allExpenses) {
            Category category = expense.getCategory();
            categorySummary.put(category, categorySummary.getOrDefault(category, 0.0) + expense.getAmount());
        }
        return categorySummary;
    }

    public double calculateExpensesTotalAmount() {
        List<Expense> allExpenses = viewExpenses();
        double totalAmount  = 0;
        for(Expense expense : allExpenses) {
            totalAmount += expense.getAmount();
        }
        return totalAmount;
    }


}
