package com.example.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "exam")
public class Exam {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_exam")
    private Long idExam;

    @Column(name="exam_name")
    private String examName;

    @Column(name="max_score")
    private Integer maxScore;

    @OneToMany(mappedBy = "idExam")
    private List<ExamResults> results = new ArrayList<>();

    public Long getIdExam() {return idExam;}
    public void setIdExam(Long idExam) {this.idExam = idExam;}
    public String getExamName() {return examName;}
    public void setExamName(String examName) {this.examName = examName;}
    public Integer getMaxScore() {return maxScore;}
    public void setMaxScore(Integer maxScore) {this.maxScore = maxScore;}
    public List<ExamResults> getResults() {return results;}
}
