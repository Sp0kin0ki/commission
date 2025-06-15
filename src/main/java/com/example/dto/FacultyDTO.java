package com.example.dto;

public class FacultyDTO {
    private final String name;
    private final int numberOfBudgetPlaces;
    private final int numberOfPaidPlaces;
    private final int numberOfSpecialPlaces;

    public FacultyDTO(String name, int budget, int paid, int special) {
        this.name = name;
        this.numberOfBudgetPlaces = budget;
        this.numberOfPaidPlaces = paid;
        this.numberOfSpecialPlaces = special;
    }

    public String getName() { return name; }
    public int getNumberOfBudgetPlaces() { return numberOfBudgetPlaces; }
    public int getNumberOfPaidPlaces() { return numberOfPaidPlaces; }
    public int getNumberOfSpecialPlaces() { return numberOfSpecialPlaces; }
}