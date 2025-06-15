package com.example.entities;

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
@Table(name = "documents")
public class Documents {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDocuments;

    @ManyToOne
    @JoinColumn(name = "id_entrant")
    private Entrant idEntrant;

    @Column(name="document_type")
    private String documentType;

    @Column(name="path_to_scan")
    private String pathToScan;

    @Column(name="upload_date")
    private Date uploadDate;

    
    public Long getIdDocuments() {return idDocuments;}
    public void setIdDocuments(Long idDocuments) {this.idDocuments = idDocuments;}
    public Entrant getIdEntrant() {return idEntrant;}
    public void setIdEntrant(Entrant idEntrant) {this.idEntrant = idEntrant;}
    public String getDocumentType() {return documentType;}
    public void setDocumentType(String documentType) {this.documentType = documentType;}
    public String getPathToScan() {return pathToScan;}
    public void setPathToScan(String pathToScan) {this.pathToScan = pathToScan;}
    public Date getUploadDate() {return uploadDate;}
    public void setUploadDate(Date uploadDate) {this.uploadDate = uploadDate;}
}
