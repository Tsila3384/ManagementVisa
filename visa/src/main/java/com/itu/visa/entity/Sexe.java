package com.itu.visa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sexe")
public class Sexe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sexe")
    private Long idSexe;

    @Column(name = "libelle", length = 50)
    private String libelle;

    public Sexe() {
    }

    public Sexe(Long idSexe, String libelle) {
        this.idSexe = idSexe;
        this.libelle = libelle;
    }

    public Long getIdSexe() {
        return idSexe;
    }

    public void setIdSexe(Long idSexe) {
        this.idSexe = idSexe;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
