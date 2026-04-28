package com.itu.visa.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class HistoriqueDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demandeur_id")
    private Demandeur demandeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private DocumentsType document;

    private LocalDateTime dateRemise;

    public HistoriqueDocument() {}

    public HistoriqueDocument(Demandeur demandeur, DocumentsType document, LocalDateTime dateRemise) {
        this.demandeur = demandeur;
        this.document = document;
        this.dateRemise = dateRemise;
    }

    public Long getId() {
        return id;
    }

    public Demandeur getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        this.demandeur = demandeur;
    }

    public DocumentsType getDocument() {
        return document;
    }

    public void setDocument(DocumentsType document) {
        this.document = document;
    }

    public LocalDateTime getDateRemise() {
        return dateRemise;
    }

    public void setDateRemise(LocalDateTime dateRemise) {
        this.dateRemise = dateRemise;
    }
}