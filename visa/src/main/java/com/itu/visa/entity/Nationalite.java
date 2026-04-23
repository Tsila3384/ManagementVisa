package com.itu.visa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "nationalite")
public class Nationalite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nationalite")
    private Long idNationalite;

    @Column(name = "libelle", length = 50)
    private String libelle;

    public Nationalite() {
    }

    public Nationalite(Long idNationalite, String libelle) {
        this.idNationalite = idNationalite;
        this.libelle = libelle;
    }

    public Long getIdNationalite() {
        return idNationalite;
    }

    public void setIdNationalite(Long idNationalite) {
        this.idNationalite = idNationalite;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
