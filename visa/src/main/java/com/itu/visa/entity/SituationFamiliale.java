package com.itu.visa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "situation_familiale")
public class SituationFamiliale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_situation_familiale")
    private Long idSituationFamiliale;

    @Column(name = "libelle", length = 50)
    private String libelle;

    public SituationFamiliale() {
    }

    public SituationFamiliale(Long idSituationFamiliale, String libelle) {
        this.idSituationFamiliale = idSituationFamiliale;
        this.libelle = libelle;
    }

    public Long getIdSituationFamiliale() {
        return idSituationFamiliale;
    }

    public void setIdSituationFamiliale(Long idSituationFamiliale) {
        this.idSituationFamiliale = idSituationFamiliale;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
