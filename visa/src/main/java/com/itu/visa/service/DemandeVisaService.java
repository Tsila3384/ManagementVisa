package com.itu.visa.service;

import com.itu.visa.dto.DemandeVisaDTO;
import com.itu.visa.entity.*;
import com.itu.visa.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class DemandeVisaService {

    private final EtatCivilRepository etatCivilRepository;
    private final PasseportRepository passeportRepository;
    private final VisaTransformableRepository visaTransformableRepository;
    private final DemandeurRepository demandeurRepository;
    private final DemandeRepository demandeRepository;
    private final StatutDemandeRepository statutDemandeRepository;
    private final SexeRepository sexeRepository;
    private final SituationFamilialeRepository situationFamilialeRepository;
    private final NationaliteRepository nationaliteRepository;
    private final PaysRepository paysRepository;
    private final TypeVisaRepository typeVisaRepository;
    private final TypeDemandeVisaRepository typeDemandeVisaRepository;

    /**
     * Traite la soumission du formulaire de demande de visa
     * 
     * @param demandeVisa Données du formulaire
     * @return Code de référence de la demande
     */
    public String traiterDemande(DemandeVisaDTO demandeVisa) {
        // 1. Créer la demande de visa PREMIER (elle n'a pas de dépendance)
        TypeVisa typeVisa = demandeVisa.getIdTypeVisa() != null ? 
            typeVisaRepository.findById(demandeVisa.getIdTypeVisa()).orElse(null) : null;
        TypeDemandeVisa typeDemandeVisa = demandeVisa.getIdTypeDemandeVisa() != null ? 
            typeDemandeVisaRepository.findById(demandeVisa.getIdTypeDemandeVisa()).orElse(null) : null;

        Demandeur demandeur = Demandeur.builder()
            .code(genererCodeDemande())
            .typeVisa(typeVisa)
            .typeDemandeVisa(typeDemandeVisa)
            .build();
        Demandeur demandeurSave = demandeurRepository.save(demandeur);

        // 2. Créer la demande avec statut initial "En attente"
        StatutDemande statutInitial = statutDemandeRepository.findById(1L).orElseGet(() -> 
            StatutDemande.builder().libelle("En attente").build()
        );
        
        Demande demande = Demande.builder()
            .dateDemande(LocalDate.now())
            .demandeur(demandeurSave)
            .statutDemande(statutInitial)
            .build();
        demandeRepository.save(demande);

        // 3. Créer/récupérer les références
        Sexe sexe = demandeVisa.getIdSexe() != null ? 
            sexeRepository.findById(demandeVisa.getIdSexe()).orElse(null) : null;
        SituationFamiliale situationFamiliale = demandeVisa.getIdSituationFamiliale() != null ? 
            situationFamilialeRepository.findById(demandeVisa.getIdSituationFamiliale()).orElse(null) : null;
        Nationalite nationalite = demandeVisa.getIdNationalite() != null ? 
            nationaliteRepository.findById(demandeVisa.getIdNationalite()).orElse(null) : null;
        Pays pays = demandeVisa.getIdPays() != null ? 
            paysRepository.findById(demandeVisa.getIdPays()).orElse(null) : null;

        // 4. Créer l'état civil avec le demandeur
        EtatCivil etatCivil = EtatCivil.builder()
            .nom(demandeVisa.getNom())
            .prenom(demandeVisa.getPrenom())
            .nomJeuneFille(demandeVisa.getNomJeuneFille())
            .dateNaissance(demandeVisa.getDateNaissance())
            .lieuNaissance(demandeVisa.getLieuNaissance())
            .email(demandeVisa.getEmail())
            .contact(demandeVisa.getContact())
            .sexe(sexe)
            .situationFamiliale(situationFamiliale)
            .nationalite(nationalite)
            .pays(pays)
            .demandeur(demandeurSave)
            .build();
        etatCivil = etatCivilRepository.save(etatCivil);

        // 5. Créer le passeport s'il y a des données
        if (demandeVisa.getNumero() != null && !demandeVisa.getNumero().isEmpty()) {
            Passeport passeport = Passeport.builder()
                .numero(demandeVisa.getNumero())
                .dateDelivrance(demandeVisa.getDateDelivrance())
                .dateExpiration(demandeVisa.getDateExpirationPasseport())
                .etatCivil(etatCivil)
                .build();
            passeportRepository.save(passeport);
        }

        // 6. Créer la visa transformable s'il y a des données
        if (demandeVisa.getReference() != null && !demandeVisa.getReference().isEmpty()) {
            VisaTransformable visaTransformable = VisaTransformable.builder()
                .reference(demandeVisa.getReference())
                .dateEntreeMada(demandeVisa.getDateEntreeMada())
                .lieu(demandeVisa.getLieu())
                .dateExpiration(demandeVisa.getDateExpirationVisa())
                .etatCivil(etatCivil)
                .build();
            visaTransformableRepository.save(visaTransformable);
        }

        return demandeurSave.getCode();
    }

    /**
     * Génère un code unique pour la demande
     */
    private String genererCodeDemande() {
        return "VLS-" + java.time.Year.now().getValue() + "-" 
            + String.format("%06d", (int)(Math.random() * 900000) + 100000);
    }
}
