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
@Table(name = "application")
public class Application {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idApplication;

    @ManyToOne
    @JoinColumn(name = "id_entrant")
    private Entrant idEntrant;

    @ManyToOne
    @JoinColumn(name = "id_faculty")
    private Faculty faculty;

    @Column(name="priority")
    private Integer priority;

    @Column(name="application_status")
    private String applicationStatus;

    public Long getIdApplication() {return idApplication;}
    public void setIdApplication(Long idApplication) {this.idApplication = idApplication;}
    public Entrant getIdEntrant() {return idEntrant;}
    public void setIdEntrant(Entrant idEntrant) {this.idEntrant = idEntrant;}
    public Faculty getIdFaculty() {return faculty;}
    public void setIdFaculty(Faculty idFaculty) {this.faculty = idFaculty;}
    public Integer getPriority() {return priority;}
    public void setPriority(Integer priority) {this.priority = priority;}
    public String getApplicationStatus() {return applicationStatus;}
    public void setApplicationStatus(String applicationStatus) {this.applicationStatus = applicationStatus;}
}
