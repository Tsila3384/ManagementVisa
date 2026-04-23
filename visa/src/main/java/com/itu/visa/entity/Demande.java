package com.itu.visa.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "demande")
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demande")
    private Long idDemande;

    @Column(name = "date_demande")
    private LocalDate dateDemande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_demandeur", nullable = false)
    private Demandeur demandeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_statut_demande", nullable = false)
    private StatutDemande statutDemande;

    public Demande() {
    }

    public Demande(Long idDemande, LocalDate dateDemande, Demandeur demandeur, StatutDemande statutDemande) {
        this.idDemande = idDemande;
        this.dateDemande = dateDemande;
        this.demandeur = demandeur;
        this.statutDemande = statutDemande;
    }

    public Long getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(Long idDemande) {
        this.idDemande = idDemande;
    }

    public LocalDate getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDate dateDemande) {
        this.dateDemande = dateDemande;
    }

    public Demandeur getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        this.demandeur = demandeur;
    }

    public StatutDemande getStatutDemande() {
        return statutDemande;
    }

    public void setStatutDemande(StatutDemande statutDemande) {
        this.statutDemande = statutDemande;
    }
}
