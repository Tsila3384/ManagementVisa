package com.itu.visa.dto;

import java.time.LocalDate;
import java.util.List;

public class DemandeVisaDTO {
    // Étape 1 : État civil
    private String nom;
    private String prenom;
    private String nomJeuneFille;
    private LocalDate dateNaissance;
    private String lieuNaissance;
    private String email;
    private String contact;
    private Long idSexe;
    private Long idSituationFamiliale;
    private Long idNationalite;
    private Long idPays;

    // Étape 2 : Passeport
    private String numero;
    private LocalDate dateDelivrance;
    private LocalDate dateExpirationPasseport;

    // Étape 3 : Visa transformable
    private String reference;
    private LocalDate dateEntreeMada;
    private String lieu;
    private LocalDate dateExpirationVisa;
    private Long idTypeDemandeVisa;

    // Étape 4 : Type de visa
    private Long idTypeVisa;

    // Étape 5 : Documents
    private List<String> docCommun;
    private List<String> docType;

    public DemandeVisaDTO() {
    }

    public DemandeVisaDTO(String nom, String prenom, String nomJeuneFille, LocalDate dateNaissance,
            String lieuNaissance, String email, String contact, Long idSexe, Long idSituationFamiliale,
            Long idNationalite, Long idPays, String numero, LocalDate dateDelivrance, LocalDate dateExpirationPasseport,
            String reference, LocalDate dateEntreeMada, String lieu, LocalDate dateExpirationVisa,
            Long idTypeDemandeVisa, Long idTypeVisa, List<String> docCommun, List<String> docType) {
        this.nom = nom;
        this.prenom = prenom;
        this.nomJeuneFille = nomJeuneFille;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.email = email;
        this.contact = contact;
        this.idSexe = idSexe;
        this.idSituationFamiliale = idSituationFamiliale;
        this.idNationalite = idNationalite;
        this.idPays = idPays;
        this.numero = numero;
        this.dateDelivrance = dateDelivrance;
        this.dateExpirationPasseport = dateExpirationPasseport;
        this.reference = reference;
        this.dateEntreeMada = dateEntreeMada;
        this.lieu = lieu;
        this.dateExpirationVisa = dateExpirationVisa;
        this.idTypeDemandeVisa = idTypeDemandeVisa;
        this.idTypeVisa = idTypeVisa;
        this.docCommun = docCommun;
        this.docType = docType;
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

    public Long getIdSexe() {
        return idSexe;
    }

    public void setIdSexe(Long idSexe) {
        this.idSexe = idSexe;
    }

    public Long getIdSituationFamiliale() {
        return idSituationFamiliale;
    }

    public void setIdSituationFamiliale(Long idSituationFamiliale) {
        this.idSituationFamiliale = idSituationFamiliale;
    }

    public Long getIdNationalite() {
        return idNationalite;
    }

    public void setIdNationalite(Long idNationalite) {
        this.idNationalite = idNationalite;
    }

    public Long getIdPays() {
        return idPays;
    }

    public void setIdPays(Long idPays) {
        this.idPays = idPays;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getDateDelivrance() {
        return dateDelivrance;
    }

    public void setDateDelivrance(LocalDate dateDelivrance) {
        this.dateDelivrance = dateDelivrance;
    }

    public LocalDate getDateExpirationPasseport() {
        return dateExpirationPasseport;
    }

    public void setDateExpirationPasseport(LocalDate dateExpirationPasseport) {
        this.dateExpirationPasseport = dateExpirationPasseport;
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

    public LocalDate getDateExpirationVisa() {
        return dateExpirationVisa;
    }

    public void setDateExpirationVisa(LocalDate dateExpirationVisa) {
        this.dateExpirationVisa = dateExpirationVisa;
    }

    public Long getIdTypeDemandeVisa() {
        return idTypeDemandeVisa;
    }

    public void setIdTypeDemandeVisa(Long idTypeDemandeVisa) {
        this.idTypeDemandeVisa = idTypeDemandeVisa;
    }

    public Long getIdTypeVisa() {
        return idTypeVisa;
    }

    public void setIdTypeVisa(Long idTypeVisa) {
        this.idTypeVisa = idTypeVisa;
    }

    public List<String> getDocCommun() {
        return docCommun;
    }

    public void setDocCommun(List<String> docCommun) {
        this.docCommun = docCommun;
    }

    public List<String> getDocType() {
        return docType;
    }

    public void setDocType(List<String> docType) {
        this.docType = docType;
    }
}
