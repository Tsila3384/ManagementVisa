package com.itu.visa.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "etat_civil")
public class EtatCivil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_etat_civil")
    private Long idEtatCivil;

    @Column(name = "nom", length = 50)
    private String nom;

    @Column(name = "prenom", length = 50)
    private String prenom;

    @Column(name = "nom_jeune_fille", length = 50)
    private String nomJeuneFille;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "lieu_naissance", length = 50)
    private String lieuNaissance;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "contact", length = 50)
    private String contact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_situation_familiale", nullable = false)
    private SituationFamiliale situationFamiliale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sexe", nullable = false)
    private Sexe sexe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nationalite", nullable = false)
    private Nationalite nationalite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pays", nullable = false)
    private Pays pays;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_demandeur", nullable = false, unique = true)
    private Demandeur demandeur;

    public EtatCivil() {
    }

    public EtatCivil(Long idEtatCivil, String nom, String prenom, String nomJeuneFille, LocalDate dateNaissance,
            String lieuNaissance, String email, String contact, SituationFamiliale situationFamiliale, Sexe sexe,
            Nationalite nationalite, Pays pays, Demandeur demandeur) {
        this.idEtatCivil = idEtatCivil;
        this.nom = nom;
        this.prenom = prenom;
        this.nomJeuneFille = nomJeuneFille;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.email = email;
        this.contact = contact;
        this.situationFamiliale = situationFamiliale;
        this.sexe = sexe;
        this.nationalite = nationalite;
        this.pays = pays;
        this.demandeur = demandeur;
    }

    public Long getIdEtatCivil() {
        return idEtatCivil;
    }

    public void setIdEtatCivil(Long idEtatCivil) {
        this.idEtatCivil = idEtatCivil;
    }

    // Ajout : méthode pour formatage de la date de naissance pour Thymeleaf
    public String getDateNaissanceFormatee() {
        if (dateNaissance == null) return "";
        return java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy").format(dateNaissance);
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNomJeuneFille() {
        return nomJeuneFille;
    }

    public void setNomJeuneFille(String nomJeuneFille) {
        this.nomJeuneFille = nomJeuneFille;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public SituationFamiliale getSituationFamiliale() {
        return situationFamiliale;
    }

    public void setSituationFamiliale(SituationFamiliale situationFamiliale) {
        this.situationFamiliale = situationFamiliale;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Nationalite getNationalite() {
        return nationalite;
    }

    public void setNationalite(Nationalite nationalite) {
        this.nationalite = nationalite;
    }

    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public Demandeur getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        this.demandeur = demandeur;
    }
}
