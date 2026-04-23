package com.itu.visa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "type_visa")
public class TypeVisa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_visa")
    private Long idTypeVisa;

    @Column(name = "libelle", length = 50)
    private String libelle;

    public TypeVisa() {
    }

    public TypeVisa(Long idTypeVisa, String libelle) {
        this.idTypeVisa = idTypeVisa;
        this.libelle = libelle;
    }

    public Long getIdTypeVisa() {
        return idTypeVisa;
    }

    public void setIdTypeVisa(Long idTypeVisa) {
        this.idTypeVisa = idTypeVisa;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
