package com.itu.visa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pays")
public class Pays {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pays")
    private Long idPays;

    @Column(name = "libelle", length = 50)
    private String libelle;

    public Pays() {
    }

    public Pays(Long idPays, String libelle) {
        this.idPays = idPays;
        this.libelle = libelle;
    }

    public Long getIdPays() {
        return idPays;
    }

    public void setIdPays(Long idPays) {
        this.idPays = idPays;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
