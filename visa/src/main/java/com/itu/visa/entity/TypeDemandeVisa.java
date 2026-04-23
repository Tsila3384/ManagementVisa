package com.itu.visa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "type_demande_visa")
public class TypeDemandeVisa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_demande_visa")
    private Long idTypeDemandeVisa;

    @Column(name = "libelle", length = 50)
    private String libelle;

    public TypeDemandeVisa() {
    }

    public TypeDemandeVisa(Long idTypeDemandeVisa, String libelle) {
        this.idTypeDemandeVisa = idTypeDemandeVisa;
        this.libelle = libelle;
    }

    public Long getIdTypeDemandeVisa() {
        return idTypeDemandeVisa;
    }

    public void setIdTypeDemandeVisa(Long idTypeDemandeVisa) {
        this.idTypeDemandeVisa = idTypeDemandeVisa;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
