package com.itu.visa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "documents_communs")
public class DocumentsCommun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documents_commune")
    private Long idDocumentsCommune;

    @Column(name = "libelle", length = 50)
    private String libelle;

    @Column(name = "is_obligatoire")
    private Boolean isObligatoire;

    public DocumentsCommun() {
    }

    public DocumentsCommun(Long idDocumentsCommune, String libelle, Boolean isObligatoire) {
        this.idDocumentsCommune = idDocumentsCommune;
        this.libelle = libelle;
        this.isObligatoire = isObligatoire;
    }

    public Long getIdDocumentsCommune() {
        return idDocumentsCommune;
    }

    public void setIdDocumentsCommune(Long idDocumentsCommune) {
        this.idDocumentsCommune = idDocumentsCommune;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Boolean getIsObligatoire() {
        return isObligatoire;
    }

    public void setIsObligatoire(Boolean isObligatoire) {
        this.isObligatoire = isObligatoire;
    }
}
