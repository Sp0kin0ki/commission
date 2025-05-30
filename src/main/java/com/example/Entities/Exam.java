package com.example.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Exam {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExam;

    @Column(name="exam_name")
    private String examName;

    @Column(name="max_score")
    private Integer maxScore;

    public Long getIdExam() {return idExam;}
    public void setIdExam(Long idExam) {this.idExam = idExam;}
    public String getExamName() {return examName;}
    public void setExamName(String examName) {this.examName = examName;}
    public Integer getMaxScore() {return maxScore;}
    public void setMaxScore(Integer maxScore) {this.maxScore = maxScore;}
}
