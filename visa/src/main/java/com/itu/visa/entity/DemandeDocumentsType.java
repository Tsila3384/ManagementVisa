package com.itu.visa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "demandeur_documents_types")
public class DemandeDocumentsType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demandeur_documents")
    private Long idDemandeDocuments;

    @Column(name = "is_ok")
    private Boolean isOk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_documents_types", nullable = false)
    private DocumentsType documentsType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_demandeur", nullable = false)
    private Demandeur demandeur;

    public DemandeDocumentsType() {
    }

    public DemandeDocumentsType(Long idDemandeDocuments, Boolean isOk, DocumentsType documentsType,
            Demandeur demandeur) {
        this.idDemandeDocuments = idDemandeDocuments;
        this.isOk = isOk;
        this.documentsType = documentsType;
        this.demandeur = demandeur;
    }

    public Long getIdDemandeDocuments() {
        return idDemandeDocuments;
    }

    public void setIdDemandeDocuments(Long idDemandeDocuments) {
        this.idDemandeDocuments = idDemandeDocuments;
    }

    public Boolean getIsOk() {
        return isOk;
    }

    public void setIsOk(Boolean isOk) {
        this.isOk = isOk;
    }

    public DocumentsType getDocumentsType() {
        return documentsType;
    }

    public void setDocumentsType(DocumentsType documentsType) {
        this.documentsType = documentsType;
    }

    public Demandeur getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        this.demandeur = demandeur;
    }
}
