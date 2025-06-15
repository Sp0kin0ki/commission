package com.example.dto;

import java.sql.Date;

public class OrderOfEnrollmentDTO {
    private Long idOrder;
    private Long entrantId;
    private Long facultyId;
    private Boolean isEnrolled;
    private Date date;
    private Double price;
    private String status;

    public OrderOfEnrollmentDTO(Long idOrder, Long entrantId, Long facultyId, Boolean isEnrolled,
     Date date, Double price, String status) {
        this.idOrder = idOrder;
        this.entrantId = entrantId;
        this.facultyId = facultyId;
        this.isEnrolled = isEnrolled;
        this.date = date;
        this.price = price;
        this.status = status;
    }

    public Long getIdOrder() { return idOrder; }
    public Long getEntrantId() { return entrantId; }
    public Long getFacultyId() { return facultyId; }
    public Boolean getIsEnrolled() { return isEnrolled; }
    public Date getDate() { return date; }
    public Double getPrice() { return price; }
    public String getStatus() { return status; }
}