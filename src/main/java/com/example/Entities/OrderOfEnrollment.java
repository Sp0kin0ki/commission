package com.example.Entities;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_of_enrollment")
public class OrderOfEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrder;

    @ManyToOne
    @JoinColumn(name = "id_entrant")
    private Entrant idEntrant;

    @ManyToOne
    @JoinColumn(name = "id_faculty")
    private Faculty idFaculty;

    @Column(name="is_enrolled")
    private Boolean isEnrolled;

    @Column(name="is_state_employee")
    private Boolean isStateEmployee;

    @Column(name="date")
    private Date date;

    @Column(name="price")
    private Double price;

    @Column(name="status")
    private String status;

    public Long getIdOrder() {return idOrder;}
    public void setIdOrder(Long idOrder) {this.idOrder = idOrder;}
    public Entrant getIdEntrant() {return idEntrant;}
    public void setIdEntrant(Entrant idEntrant) {this.idEntrant = idEntrant;}
    public Faculty getIdFaculty() {return idFaculty;}
    public void setIdFaculty(Faculty idFaculty) {this.idFaculty = idFaculty;}
    public Boolean getIsEnrolled() {return isEnrolled;}
    public void setIsEnrolled(Boolean isEnrolled) {this.isEnrolled = isEnrolled;}
    public Boolean getIsStateEmployee() {return isStateEmployee;}
    public void setIsStateEmployee(Boolean isStateEmployee) {this.isStateEmployee = isStateEmployee;}
    public Date getDate() {return date;}
    public void setDate(Date date) {this.date = date;}
    public Double getPrice() {return price;}
    public void setPrice(Double price) {this.price = price;}
    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}
}
