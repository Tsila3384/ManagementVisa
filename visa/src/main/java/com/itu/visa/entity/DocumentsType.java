package com.itu.visa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "documents_types")
public class DocumentsType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documents_types")
    private Long idDocumentsTypes;

    @Column(name = "libelle", length = 50)
    private String libelle;

    @Column(name = "is_obligatoire")
    private Boolean isObligatoire;

    public DocumentsType() {
    }

    public DocumentsType(Long idDocumentsTypes, String libelle, Boolean isObligatoire) {
        this.idDocumentsTypes = idDocumentsTypes;
        this.libelle = libelle;
        this.isObligatoire = isObligatoire;
    }

    public Long getIdDocumentsTypes() {
        return idDocumentsTypes;
    }

    public void setIdDocumentsTypes(Long idDocumentsTypes) {
        this.idDocumentsTypes = idDocumentsTypes;
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
