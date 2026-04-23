package com.itu.visa.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "etat_civil")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
