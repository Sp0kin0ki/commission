package com.example.dto;

public class ExamAverageScoreDTO {
    private String examName;
    private double averageScore;

    public ExamAverageScoreDTO(String examName, double averageScore) {
        this.examName = examName;
        this.averageScore = averageScore;
    }

    public String getExamName() {
        return examName;
    }

    public double getAverageScore() {
        return averageScore;
    }
}