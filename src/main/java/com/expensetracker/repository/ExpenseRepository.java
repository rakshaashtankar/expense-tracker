package com.expensetracker.repository;

import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExpenseRepository {
    private final List<Expense> expenseList = new ArrayList<>();

    private static int count = 1;

    public void save(Expense newExpense){
        Expense addExpense = new Expense(count++, newExpense.getAmount(), newExpense.getCategory(), newExpense.getDate(), newExpense.getDescription());
        expenseList.add(addExpense);
    }

    public List<Expense> findAll() {
        return new ArrayList<>(expenseList);
    }

    public boolean deleteById(int id) {
        return expenseList.removeIf(row -> row.getId() == id);
    }


    public Optional<Expense> findById(int id) {
        return expenseList.stream()
                .filter(e -> e.getId() == id)
                .findFirst();
    }

    public List<Expense> findByCategory(Category category) {
        return  expenseList.stream()
                .filter(e -> e.getCategory() == category)
                .toList();
    }

    public List<Expense> findByDescription(String description) {
        return expenseList.stream()
                .filter(e -> e.getDescription().toLowerCase().contains(description.toLowerCase()))
                .toList();
    }

}
