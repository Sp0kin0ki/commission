package com.example.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "entrant")
public class Entrant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEntrant;

    @Column(name = "series_and_number")
    private String seriesAndNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "surname")
    private String surname;

    @Column(name = "gold_medal")
    private Boolean goldMedal;

    @Column(name = "year_of_admission")
    private Integer yearOfAdmission;

    @Column(name = "final_score")
    private Integer finalScore;


    public Long getId_entrant() {return idEntrant;}
    public void setId_entrant(Long id_entrant) {this.idEntrant = id_entrant;}
    public String getSeries_and_number() {return seriesAndNumber;}
    public void setSeries_and_number(String series_and_number) {this.seriesAndNumber = series_and_number;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getLast_name() {return lastName;}
    public void setLast_name(String last_name) {this.lastName = last_name;}
    public String getSurname() {return surname;}
    public void setSurname(String surname) {this.surname = surname;}
    public Boolean getGold_medal() {return goldMedal;}
    public void setGold_medal(Boolean gold_medal) {this.goldMedal = gold_medal;}
    public Integer getYear_of_admission() {return yearOfAdmission;}
    public void setYear_of_admission(Integer year_of_admission) {this.yearOfAdmission = year_of_admission;}
    public Integer getFinal_score() {return finalScore;}
    public void setFinal_score(Integer final_score) {this.finalScore = final_score;}
}
