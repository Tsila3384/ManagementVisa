package com.itu.visa.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
