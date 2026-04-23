package com.itu.visa.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "visa_transformable")
public class VisaTransformable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_visa_transformable")
    private Long idVisaTransformable;

    @Column(name = "reference", length = 50)
    private String reference;

    @Column(name = "date_entree_mada")
    private LocalDate dateEntreeMada;

    @Column(name = "lieu", length = 50)
    private String lieu;

    @Column(name = "date_expiration")
    private LocalDate dateExpiration;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_etat_civil", nullable = false, unique = true)
    private EtatCivil etatCivil;

    public VisaTransformable() {
    }

    public VisaTransformable(Long idVisaTransformable, String reference, LocalDate dateEntreeMada, String lieu,
            LocalDate dateExpiration, EtatCivil etatCivil) {
        this.idVisaTransformable = idVisaTransformable;
        this.reference = reference;
        this.dateEntreeMada = dateEntreeMada;
        this.lieu = lieu;
        this.dateExpiration = dateExpiration;
        this.etatCivil = etatCivil;
    }

    public Long getIdVisaTransformable() {
        return idVisaTransformable;
    }

    public void setIdVisaTransformable(Long idVisaTransformable) {
        this.idVisaTransformable = idVisaTransformable;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getDateEntreeMada() {
        return dateEntreeMada;
    }

    public void setDateEntreeMada(LocalDate dateEntreeMada) {
        this.dateEntreeMada = dateEntreeMada;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public EtatCivil getEtatCivil() {
        return etatCivil;
    }

    public void setEtatCivil(EtatCivil etatCivil) {
        this.etatCivil = etatCivil;
    }
}
