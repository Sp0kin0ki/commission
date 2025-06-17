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
@Table(name = "exam_results")
public class ExamResults {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idResult;

    @ManyToOne
    @JoinColumn(name = "id_entrant")
    private Entrant idEntrant;

    @ManyToOne
    @JoinColumn(name = "id_exam")
    private Exam idExam;

    @Column(name="score")
    private Integer score;

    public Long getIdResult() {return idResult;}
    public void setIdResult(Long idResult) {this.idResult = idResult;}
    public Entrant getIdEntrant() {return idEntrant;}
    public void setIdEntrant(Entrant idEntrant) {this.idEntrant = idEntrant;}
    public Exam getIdExam() {return idExam;}
    public void setIdExam(Exam idExam) {this.idExam = idExam;}
    public Integer getScore() {return score;}
    public void setScore(Integer score) {this.score = score;}
}
