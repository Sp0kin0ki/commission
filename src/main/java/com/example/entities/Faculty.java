package com.example.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "faculty")
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_faculty")
    private Long idFaculty;

    @Column(name="name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_speciality")
    private Speciality speciality;

    @Column(name="number_of_budget_places")
    private int numberOfBudgetPlaces;

    @Column(name="number_of_paid_places")
    private int numberOfPaidPlaces;

    @Column(name="number_of_special_places")
    private int numberOfSpecialPlaces;

    @OneToMany(mappedBy = "faculty")
    private List<Application> applications = new ArrayList<>();

    public Long getIdFaculty() {return idFaculty;}
    public void setIdFaculty(Long idFaculty) {this.idFaculty = idFaculty;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public Speciality getIdSpeciality() {return speciality;}
    public void setIdSpeciality(Speciality idSpeciality) {this.speciality = idSpeciality;}
    public int getNumberOfBudgetPlaces() {return numberOfBudgetPlaces;}
    public void setNumberOfBudgetPlaces(int numberOfBudgetPlaces) {this.numberOfBudgetPlaces = numberOfBudgetPlaces;}
    public int getNumberOfPaidPlaces() {return numberOfPaidPlaces;}
    public void setNumberOfPaidPlaces(int numberOfPaidPlaces) {this.numberOfPaidPlaces = numberOfPaidPlaces;}
    public int getNumberOfSpecialPlaces() {return numberOfSpecialPlaces;}
    public void setNumberOfSpecialPlaces(int numberOfSpecialPlaces) {this.numberOfSpecialPlaces = numberOfSpecialPlaces;}
    public List<Application> geApplications() {return applications;}
}
