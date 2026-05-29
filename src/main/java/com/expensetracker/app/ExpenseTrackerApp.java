package com.expensetracker.app;

import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;
import com.expensetracker.service.ExpenseService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ExpenseTrackerApp {
    private final ExpenseService expenseService;
    private final Scanner sc;


    public ExpenseTrackerApp() {
        this.expenseService = new ExpenseService();
        this.sc  = new Scanner(System.in);
    }



    public Expense readExpenseInput() {
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
        return new Expense(amount, Category.valueOf(category.toUpperCase()), date, description);
    }



    public  String handleAddExpense() {
        Expense newExpense = readExpenseInput();
        if( expenseService.addExpense(newExpense)) {
            return "Expense added successfully";
        } else {
            return "Error adding expense";
        }
    }

    public void handleViewExpenses() {
        List<Expense> expenseList= expenseService.viewExpenses();
        if(expenseList.isEmpty()) {
            System.out.println("No expense added.");
        }
        else {
            for(Expense e : expenseList) {
                System.out.println(e);
            }
        }
    }

    public void handleDeleteExpense() {
        System.out.println("Enter the expense id to be deleted");
        int deleteId = sc.nextInt();
        sc.nextLine();
        boolean isDeleted = expenseService.deleteExpense(deleteId);
        if(isDeleted) {
            System.out.println("Expense deleted successfully");
        }
        else {
            System.out.println("Error deleting expense");
        }
    }

    public void handleUpdateExpense() {
        System.out.println("Enter id to update details");
        int updateId = sc.nextInt();
        sc.nextLine();
        Expense updatedExpense = readExpenseInput();
        boolean isUpdated = expenseService.updateExpense(updateId, updatedExpense);
        if(isUpdated) {
            System.out.println("Expenses updated successfully");
        } else {
            System.out.println("Error updating expense");
        }
    }

    public void start() {
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
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.println(handleAddExpense());
                    break;
                case 2:
                    handleViewExpenses();
                    break;
                case 3:
                    handleDeleteExpense();
                    break;
                case 4:
                    handleUpdateExpense();
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


    public static void main(String[] args) {
        ExpenseTrackerApp app = new ExpenseTrackerApp();
        app.start();

    }
}
