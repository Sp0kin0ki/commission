package com.example.dto;

public class EntrantSpecialtyDTO {
    private final String name;
    private final String lastName;
    private final String surname;
    private final String applicationStatus;
    private final String specialtyName;

    public EntrantSpecialtyDTO(String name, String lastName, String surname, 
                              String applicationStatus, String specialtyName) {
        this.name = name;
        this.lastName = lastName;
        this.surname = surname;
        this.applicationStatus = applicationStatus;
        this.specialtyName = specialtyName;
    }

    // Геттеры
    public String getName() { return name; }
    public String getLastName() { return lastName; }
    public String getSurname() { return surname; }
    public String getApplicationStatus() { return applicationStatus; }
    public String getSpecialtyName() { return specialtyName; }
}