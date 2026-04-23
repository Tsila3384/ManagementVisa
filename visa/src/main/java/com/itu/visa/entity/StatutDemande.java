package com.itu.visa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "statut_demande")
public class StatutDemande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_statut_demande")
    private Long idStatutDemande;

    @Column(name = "libelle", length = 50)
    private String libelle;

    public StatutDemande() {
    }

    public StatutDemande(Long idStatutDemande, String libelle) {
        this.idStatutDemande = idStatutDemande;
        this.libelle = libelle;
    }

    public Long getIdStatutDemande() {
        return idStatutDemande;
    }

    public void setIdStatutDemande(Long idStatutDemande) {
        this.idStatutDemande = idStatutDemande;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
