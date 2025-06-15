package com.example.dto;

public class EntrantDTO {
    private final String name;
    private final String lastName;
    private final String surname;
    private final String achievementName;

    public EntrantDTO(String name, String lastName, String surname, String achievementName) {
        this.name = name;
        this.lastName = lastName;
        this.surname = surname;
        this.achievementName = achievementName;
    }

    public String getName() { return name; }
    public String getLastName() { return lastName; }
    public String getSurname() { return surname; }
    public String getAchievementName() { return achievementName; }
}
