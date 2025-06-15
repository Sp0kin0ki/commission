package com.example.dto;

public class EntrantFacultyDTO {
        private final String name;
        private final String lastName;
        private final String surname;
        private final String applicationStatus;
        private final String facultyName;

        public EntrantFacultyDTO(String name, String lastName, String surname, 
                                  String applicationStatus, String facultyName) {
            this.name = name;
            this.lastName = lastName;
            this.surname = surname;
            this.applicationStatus = applicationStatus;
            this.facultyName = facultyName;
        }

        // Геттеры
        public String getName() { return name; }
        public String getLastName() { return lastName; }
        public String getSurname() { return surname; }
        public String getApplicationStatus() { return applicationStatus; }
        public String getFacultyName() { return facultyName; }
    }