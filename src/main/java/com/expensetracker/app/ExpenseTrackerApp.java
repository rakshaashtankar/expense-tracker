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
            System.out.println("3. Delete Expense");
            System.out.println("4. Update Expense");
            System.out.println("5. Exit");
            System.out.println("Enter your choice");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("\nEnter expense details");
                    System.out.println("Enter Amount");
                    double amount = sc.nextDouble();
                    sc.nextLine();
                    boolean isValidCategory = false;
                    String category = null;
                    while(!isValidCategory) {
                        System.out.println("Select from Category");
                        for (Category c: Category.values()) {
                            System.out.println(c);
                        }
                        category=sc.nextLine();
                        for (Category c: Category.values()) {
                            if(c.name().equalsIgnoreCase(category)) {
                                isValidCategory = true;
                                break;
                            }
                        }
                    }
                    System.out.println("Enter Date(YYYY-MM-DD)");
                    LocalDate date = LocalDate.parse(sc.nextLine());
                    System.out.println("Enter Description");
                    String description = sc.nextLine();
                    Expense newExpense = new Expense(amount, Category.valueOf(category.toUpperCase()), date, description);
                    String addMessage = expenseService.addExpense(newExpense);
                    System.out.println(addMessage);
                    break;
                case 2:
                    List<Expense> expenseList= expenseService.viewExpenses();
                    if(expenseList.isEmpty()) {
                        System.out.println("No expense added.");
                    }
                    else {
                        for(Expense e : expenseList) {
                            System.out.println(e);
                        }
                    }
                    break;
                case 3:
                    System.out.println("Enter the expense id to be deleted");
                    int deleteId = sc.nextInt();
                    String deleteMessage = expenseService.deleteExpense(deleteId);
                    System.out.println(deleteMessage);
                    break;
                case 4:
                    System.out.println("Enter the expense id to be updated");
                    int updateId = sc.nextInt();
                    System.out.println("\nEnter the updatedexpense details");
                    System.out.println("Enter Amount");
                    double updatedAmount = sc.nextDouble();
                    sc.nextLine();
                    boolean isValidUpdatedCategory = false;
                    String updatedCategory = null;
                    while(!isValidUpdatedCategory) {
                        System.out.println("Select from Category");
                        for (Category c: Category.values()) {
                            System.out.println(c);
                        }
                        updatedCategory=sc.nextLine();
                        for (Category c: Category.values()) {
                            if(c.name().equalsIgnoreCase(updatedCategory)) {
                                isValidUpdatedCategory = true;
                                break;
                            }
                        }
                    }
                    System.out.println("Enter Date(YYYY-MM-DD)");
                    LocalDate updatedDate = LocalDate.parse(sc.nextLine());
                    System.out.println("Enter Description");
                    String updatedDescription = sc.nextLine();
                    Expense updatedExpense = new Expense(updatedAmount, Category.valueOf(updatedCategory.toUpperCase()), updatedDate, updatedDescription);
                    boolean isUpdated = expenseService.updateExpense(updateId, updatedExpense);
                    if(isUpdated) {
                        System.out.println("Expenses updated successfully");
                    } else {
                        System.out.println("Error updating expense");
                    }
                    break;
                case 5:
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
