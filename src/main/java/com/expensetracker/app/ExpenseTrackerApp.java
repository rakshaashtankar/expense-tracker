package com.expensetracker.app;

import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;
import com.expensetracker.service.ExpenseService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExpenseTrackerApp {
    public static void main(String[] args) {
        final ExpenseService expenseService = new ExpenseService();
        Scanner sc = new Scanner(System.in);
        System.out.println("==== Expense Tracker ====");
        boolean isExit = false;
        while(!isExit) {
            System.out.println("\nSelect the operation to perform");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expense");
            System.out.println("3. Exit");
            System.out.println("Enter your choice");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("\nEnter expense details");
                    System.out.println("Enter Amount");
                    double amount = sc.nextDouble();
                    sc.nextLine();
                    System.out.println("Select from Category");
                    for (Category category: Category.values()) {
                        System.out.println(category);
                    }
                    String category =sc.nextLine();
                    System.out.println("Enter Date(YYYY-MM-DD)");
                    LocalDate date = LocalDate.parse(sc.nextLine());
                    System.out.println("Enter Description");
                    String description = sc.nextLine();
                    System.out.println("Amount: " + amount);
                    System.out.println("Category: " + category);
                    System.out.println("Date: " + date);
                    System.out.println("Description: " + description);
                    Expense newExpense = new Expense(amount, Category.valueOf(category.toUpperCase()), date, description);
                    String message = expenseService.addExpense(newExpense);
                    System.out.println(message);
                    break;
                case 2:
                    List<Expense> expenseList= expenseService.viewExpenses();
                    if(expenseList.isEmpty()) {
                        System.out.println("No expense added.");
                    }
                    else {
                        for(Expense expense : expenseList) {
                            System.out.println(expense);
                        }
                    }
                    break;
                case 3:
                    System.out.println("Exiting the app.");
                    isExit = true;
                    sc.close();
                    break;
                default:
                    System.out.println("Invalid input");
            }
        }
    }
}
