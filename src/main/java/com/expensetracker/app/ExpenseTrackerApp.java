package com.expensetracker.app;

import com.expensetracker.exception.ExpenseNotFoundException;
import com.expensetracker.model.Category;
import com.expensetracker.model.Expense;
import com.expensetracker.service.ExpenseService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ExpenseTrackerApp {
    private final ExpenseService expenseService;
    private final Scanner sc;


    public ExpenseTrackerApp() {
        this.expenseService = new ExpenseService();
        this.sc  = new Scanner(System.in);
    }

    private double readAmount() {
        while(true) {
            try {
                System.out.println("Enter Amount");
                double amount = sc.nextDouble();
                sc.nextLine();
                if(amount <=0 ) {
                    System.out.println("Amount should be greater than 0.");
                    continue;
                }
                return amount;
            }  catch (InputMismatchException ex) {
                System.out.println("Please enter a valid amount.");
                sc.nextLine();
            }
        }
    }

    private String readCategory() {
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
            if(!isValidCategory) {
                System.out.println("Invalid Category");
            }
        }
        return category;
    }

    private LocalDate readDate(){
        while(true) {
            try {
                System.out.println("Enter Date(YYYY-MM-DD)");
                LocalDate date = LocalDate.parse(sc.nextLine());
                if(date.isAfter(LocalDate.now())) {
                    System.out.println("Future date not allowed.");
                    continue;
                }
                return date;
            } catch(DateTimeParseException ex) {
                System.out.println("Use Date format: YYYY-MM-DD");
            }
        }
    }

    public String readDescription() {
        while(true) {
            System.out.println("Enter Description");
            String description = sc.nextLine();
            if(description.isBlank()) {
                System.out.println("Description cannot be null or empty");
                continue;
            }
            return description;
        }
    }


    private Expense readExpenseInput() {
        System.out.println("\nEnter expense details");
        double amount = readAmount();
        String category = readCategory();
        LocalDate date = readDate();
        String description = readDescription();
        return new Expense(amount, Category.valueOf(category.toUpperCase()), date, description);
    }

    private int readInteger() {
        while(true) {
            try{
                int integerValue = sc.nextInt();
                sc.nextLine();
                return integerValue;
            } catch (InputMismatchException ex) {
                System.out.println("Please enter a valid integer value.");
                sc.nextLine();
            }
        }
    }

    private void handleAddExpense() {
        try {
            Expense newExpense = readExpenseInput();
            expenseService.addExpense(newExpense);
            System.out.println("Expense added successfully");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void handleViewExpenses() {
        List<Expense> expenseList= expenseService.viewExpenses();
        if(expenseList.isEmpty()) {
            System.out.println("No expense added till now.");
        } else {
            for(Expense e : expenseList) {
                System.out.println(e);
            }
        }
    }

    private void handleDeleteExpense() {
        try {
            System.out.println("Enter the expense id to be deleted");
            int deleteId = readInteger();
            expenseService.deleteExpense(deleteId);
            System.out.println("Expense deleted successfully");
        } catch (IllegalArgumentException | ExpenseNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void handleUpdateExpense() {
        try {
            System.out.println("Enter id to update details");
            int updateId = readInteger();
            Expense updatedExpense = readExpenseInput();
            expenseService.updateExpense(updateId, updatedExpense);
            System.out.println("Expenses updated successfully");
        } catch( IllegalArgumentException | ExpenseNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void handleSearchById() {
        try {
            System.out.println("Enter id to be searched");
            int searchId = readInteger();
            Optional<Expense> searchedExpense = expenseService.searchExpenseById(searchId);
            if(searchedExpense.isPresent()) {
                searchedExpense.ifPresent(System.out::println);
            } else {
                System.out.println("No expense present with id "+searchId+ ".");
            }
        } catch( IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void handleSearchByCategory() {
        String searchCategory = readCategory();
        List<Expense> searchedCategoryList = expenseService.searchExpenseByCategory(searchCategory);
        if(searchedCategoryList.isEmpty()) {
            System.out.println("No expenses exist with " + searchCategory.toUpperCase() + " category.");
        } else {
            for(Expense e : searchedCategoryList) {
                System.out.println(e);
            }
        }

    }

    private void handleSearchByDescription() {
        System.out.println("Enter description to be searched");
        String searchDescription = sc.nextLine();
        List<Expense> searchedDescriptionList = expenseService.searchExpenseByDescription(searchDescription);
        if(searchedDescriptionList.isEmpty()) {
            System.out.println("No expenses exist with " + searchDescription + " description.");
        } else {
            for(Expense e : searchedDescriptionList) {
                System.out.println(e);
            }
        }

    }

    private void handleSearchExpense() {
        boolean isBack  = false;
        while(!isBack) {
            System.out.println("\n=== Select Search Operation ===");
            System.out.println("1. Search By ID");
            System.out.println("2. Search By Category");
            System.out.println("3. Search By Description");
            System.out.println("4. Back");
            System.out.println("Enter your search choice");
            int searchType = readInteger();
            switch (searchType) {
                case 1:
                    handleSearchById();
                    break;
                case 2:
                    handleSearchByCategory();
                    break;
                case 3:
                    handleSearchByDescription();
                    break;
                case 4:
                    System.out.println("Exiting Search...");
                    isBack = true;
                    break;
                default:
                    System.out.println("Invalid search choice");
            }
        }


    }

    private void start() {

            System.out.println("==== Expense Tracker ====");
            boolean isExit = false;
            while(!isExit) {
                System.out.println("\nSelect the operation to perform");
                System.out.println("1. Add Expense");
                System.out.println("2. View Expense");
                System.out.println("3. Delete Expense");
                System.out.println("4. Update Expense");
                System.out.println("5. Search Expense");
                System.out.println("6. Exit");
                System.out.println("Enter your choice");

                int choice = readInteger();
                switch (choice) {
                    case 1:
                        handleAddExpense();
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
                        handleSearchExpense();
                        break;
                    case 6:
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
