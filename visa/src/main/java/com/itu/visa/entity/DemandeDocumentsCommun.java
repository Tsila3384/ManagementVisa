package com.itu.visa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "demandeur_documents_communs")
public class DemandeDocumentsCommun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demandeur_documents_communs")
    private Long idDemandeDocumentsCommuns;

    @Column(name = "is_ok")
    private Boolean isOk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_demandeur", nullable = false)
    private Demandeur demandeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_documents_commune", nullable = false)
    private DocumentsCommun documentsCommun;

    public DemandeDocumentsCommun() {
    }

    public DemandeDocumentsCommun(Long idDemandeDocumentsCommuns, Boolean isOk, Demandeur demandeur,
            DocumentsCommun documentsCommun) {
        this.idDemandeDocumentsCommuns = idDemandeDocumentsCommuns;
        this.isOk = isOk;
        this.demandeur = demandeur;
        this.documentsCommun = documentsCommun;
    }

    public Long getIdDemandeDocumentsCommuns() {
        return idDemandeDocumentsCommuns;
    }

    public void setIdDemandeDocumentsCommuns(Long idDemandeDocumentsCommuns) {
        this.idDemandeDocumentsCommuns = idDemandeDocumentsCommuns;
    }

    public Boolean getIsOk() {
        return isOk;
    }

    public void setIsOk(Boolean isOk) {
        this.isOk = isOk;
    }

    public Demandeur getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        this.demandeur = demandeur;
    }

    public DocumentsCommun getDocumentsCommun() {
        return documentsCommun;
    }

    public void setDocumentsCommun(DocumentsCommun documentsCommun) {
        this.documentsCommun = documentsCommun;
    }
}
