package com.example.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "speciality")
public class Speciality {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_speciality")
    private Long idSpeciality;

    @Column(name="name_of_speciality")
    private String nameOfSpeciality;

    @Column(name="score")
    private Integer score;

    public Long getIdSpeciality() {return idSpeciality;}
    public void setIdSpeciality(Long idSpeciality) {this.idSpeciality = idSpeciality;}
    public String getNameOfSpeciality() {return nameOfSpeciality;}
    public void setNameOfSpeciality(String nameOfSpeciality) {this.nameOfSpeciality = nameOfSpeciality;}
    public Integer getScore() {return score;}
    public void setScore(Integer score) {this.score = score;}
}
