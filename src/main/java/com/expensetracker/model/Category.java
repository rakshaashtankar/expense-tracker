package com.expensetracker.model;

public enum Category {
    FOOD,
    TRAVEL,
    SHOPPING,
    BILLS,
    ENTERTAINMENT,
    HEALTH,
    EDUCATION,
    OTHER;

    public static Category from(String value) {
        if(value == null || value.isBlank()) {
            throw new IllegalArgumentException("Category should not be blank.");
        }
        value = value.trim();
        for(Category category : values()) {
            if(category.name().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw  new IllegalArgumentException("Invalid category. Please choose one of: FOOD, TRAVEL, SHOPPING, BILLS, ENTERTAINMENT, HEALTH, EDUCATION, OTHER.");
    }

}
