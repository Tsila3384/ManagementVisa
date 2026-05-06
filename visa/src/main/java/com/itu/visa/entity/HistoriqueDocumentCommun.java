package com.itu.visa.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class HistoriqueDocumentCommun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "demandeur_id")
    private Demandeur demandeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_commun_id")
    private DocumentsCommun document;

    private LocalDateTime dateRemise;

    public HistoriqueDocumentCommun() {
    }

    public HistoriqueDocumentCommun(Demandeur demandeur, DocumentsCommun document, LocalDateTime dateRemise) {
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

    public DocumentsCommun getDocument() {
        return document;
    }

    public void setDocument(DocumentsCommun document) {
        this.document = document;
    }

    public LocalDateTime getDateRemise() {
        return dateRemise;
    }

    public void setDateRemise(LocalDateTime dateRemise) {
        this.dateRemise = dateRemise;
    }
}
