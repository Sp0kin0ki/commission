package com.example.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "entrant_achievements")
public class EntrantAchievements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDocs;

    @ManyToOne
    @JoinColumn(name = "id_entrant")
    private Entrant idEntrant;

    @Column(name = "name_achievements")
    private String nameAchievements;

    @Column(name = "extra_points")
    private Boolean extraPoints;

    
    public Long getIdDocs() { return idDocs; }
    public void setIdDocs(Long idDocs) { this.idDocs = idDocs; }
    public Entrant getIdEntrant() { return idEntrant; }
    public void setIdEntrant(Entrant idEntrant) { this.idEntrant = idEntrant; }
    public String getNameAchievements() { return nameAchievements; }
    public void setNameAchievements(String nameAchievements) { this.nameAchievements = nameAchievements; }
    public Boolean getExtraPoints() { return extraPoints; }
    public void setExtraPoints(Boolean extraPoints) { this.extraPoints = extraPoints; }
}
