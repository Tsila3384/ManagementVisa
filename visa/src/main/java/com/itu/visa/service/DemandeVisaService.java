package com.itu.visa.service;

import com.itu.visa.dto.DemandeVisaDTO;
import com.itu.visa.entity.*;
import com.itu.visa.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service
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
    private final DemandeDocumentsCommunRepository demandeDocumentsCommunRepository;
    private final DemandeDocumentsTypeRepository demandeDocumentsTypeRepository;
    private final DocumentsCommunRepository documentsCommunRepository;
    private final DocumentsTypeRepository documentsTypeRepository;

    public DemandeVisaService(EtatCivilRepository etatCivilRepository, PasseportRepository passeportRepository,
            VisaTransformableRepository visaTransformableRepository, DemandeurRepository demandeurRepository,
            DemandeRepository demandeRepository, StatutDemandeRepository statutDemandeRepository,
            SexeRepository sexeRepository, SituationFamilialeRepository situationFamilialeRepository,
            NationaliteRepository nationaliteRepository, PaysRepository paysRepository,
            TypeVisaRepository typeVisaRepository, TypeDemandeVisaRepository typeDemandeVisaRepository,
            DemandeDocumentsCommunRepository demandeDocumentsCommunRepository,
            DemandeDocumentsTypeRepository demandeDocumentsTypeRepository,
            DocumentsCommunRepository documentsCommunRepository,
            DocumentsTypeRepository documentsTypeRepository) {
        this.etatCivilRepository = etatCivilRepository;
        this.passeportRepository = passeportRepository;
        this.visaTransformableRepository = visaTransformableRepository;
        this.demandeurRepository = demandeurRepository;
        this.demandeRepository = demandeRepository;
        this.statutDemandeRepository = statutDemandeRepository;
        this.sexeRepository = sexeRepository;
        this.situationFamilialeRepository = situationFamilialeRepository;
        this.nationaliteRepository = nationaliteRepository;
        this.paysRepository = paysRepository;
        this.typeVisaRepository = typeVisaRepository;
        this.typeDemandeVisaRepository = typeDemandeVisaRepository;
        this.demandeDocumentsCommunRepository = demandeDocumentsCommunRepository;
        this.demandeDocumentsTypeRepository = demandeDocumentsTypeRepository;
        this.documentsCommunRepository = documentsCommunRepository;
        this.documentsTypeRepository = documentsTypeRepository;
    }

    /**
     * Traite la soumission du formulaire de demande de visa
     * 
     * @param demandeVisa Données du formulaire
     * @return Code de référence de la demande
     */
    public String traiterDemande(DemandeVisaDTO demandeVisa) {
        // 1. Créer la demande de visa PREMIER (elle n'a pas de dépendance)
        TypeVisa typeVisa = demandeVisa.getIdTypeVisa() != null
                ? typeVisaRepository.findById(demandeVisa.getIdTypeVisa()).orElse(null)
                : null;
        TypeDemandeVisa typeDemandeVisa = demandeVisa.getIdTypeDemandeVisa() != null
                ? typeDemandeVisaRepository.findById(demandeVisa.getIdTypeDemandeVisa()).orElse(null)
                : null;

        Demandeur demandeur = new Demandeur();
        demandeur.setCode(genererCodeDemande());
        demandeur.setTypeVisa(typeVisa);
        demandeur.setTypeDemandeVisa(typeDemandeVisa);
        Demandeur demandeurSave = demandeurRepository.save(demandeur);

        // 2. Créer la demande avec statut initial "En attente"
        StatutDemande statutInitial = statutDemandeRepository.findById(1L).orElseGet(() -> {
            StatutDemande s = new StatutDemande();
            s.setLibelle("En attente");
            return s;
        });

        Demande demande = new Demande();
        demande.setDateDemande(LocalDate.now());
        demande.setDemandeur(demandeurSave);
        demande.setStatutDemande(statutInitial);
        demandeRepository.save(demande);

        // 3. Créer/récupérer les références
        Sexe sexe = demandeVisa.getIdSexe() != null ? sexeRepository.findById(demandeVisa.getIdSexe()).orElse(null)
                : null;
        SituationFamiliale situationFamiliale = demandeVisa.getIdSituationFamiliale() != null
                ? situationFamilialeRepository.findById(demandeVisa.getIdSituationFamiliale()).orElse(null)
                : null;
        Nationalite nationalite = demandeVisa.getIdNationalite() != null
                ? nationaliteRepository.findById(demandeVisa.getIdNationalite()).orElse(null)
                : null;
        Pays pays = demandeVisa.getIdPays() != null ? paysRepository.findById(demandeVisa.getIdPays()).orElse(null)
                : null;

        // 4. Créer l'état civil avec le demandeur
        EtatCivil etatCivil = new EtatCivil();
        etatCivil.setNom(demandeVisa.getNom());
        etatCivil.setPrenom(demandeVisa.getPrenom());
        etatCivil.setNomJeuneFille(demandeVisa.getNomJeuneFille());
        etatCivil.setDateNaissance(demandeVisa.getDateNaissance());
        etatCivil.setLieuNaissance(demandeVisa.getLieuNaissance());
        etatCivil.setEmail(demandeVisa.getEmail());
        etatCivil.setContact(demandeVisa.getContact());
        etatCivil.setSexe(sexe);
        etatCivil.setSituationFamiliale(situationFamiliale);
        etatCivil.setNationalite(nationalite);
        etatCivil.setPays(pays);
        etatCivil.setDemandeur(demandeurSave);
        etatCivil = etatCivilRepository.save(etatCivil);

        // 5. Créer le passeport s'il y a des données
        if (demandeVisa.getNumero() != null && !demandeVisa.getNumero().isEmpty()) {
            Passeport passeport = new Passeport();
            passeport.setNumero(demandeVisa.getNumero());
            passeport.setDateDelivrance(demandeVisa.getDateDelivrance());
            passeport.setDateExpiration(demandeVisa.getDateExpirationPasseport());
            passeport.setEtatCivil(etatCivil);
            passeportRepository.save(passeport);
        }

        // 6. Créer la visa transformable s'il y a des données
        if (demandeVisa.getReference() != null && !demandeVisa.getReference().isEmpty()) {
            VisaTransformable visaTransformable = new VisaTransformable();
            visaTransformable.setReference(demandeVisa.getReference());
            visaTransformable.setDateEntreeMada(demandeVisa.getDateEntreeMada());
            visaTransformable.setLieu(demandeVisa.getLieu());
            visaTransformable.setDateExpiration(demandeVisa.getDateExpirationVisa());
            visaTransformable.setEtatCivil(etatCivil);
            visaTransformableRepository.save(visaTransformable);
        }

        // 7. Enregistrer les documents communs cochés
        if (demandeVisa.getDocCommun() != null && !demandeVisa.getDocCommun().isEmpty()) {
            for (String docId : demandeVisa.getDocCommun()) {
                try {
                    Long documentId = Long.parseLong(docId);
                    DocumentsCommun documentsCommun = documentsCommunRepository.findById(documentId).orElse(null);
                    if (documentsCommun != null) {
                        DemandeDocumentsCommun demandeDocumentsCommun = new DemandeDocumentsCommun();
                        demandeDocumentsCommun.setIsOk(true);
                        demandeDocumentsCommun.setDemandeur(demandeurSave);
                        demandeDocumentsCommun.setDocumentsCommun(documentsCommun);
                        demandeDocumentsCommunRepository.save(demandeDocumentsCommun);
                    }
                } catch (NumberFormatException e) {
                    // Ignorer les IDs invalides
                }
            }
        }

        // 8. Enregistrer les documents de type coché
        if (demandeVisa.getDocType() != null && !demandeVisa.getDocType().isEmpty()) {
            for (String docId : demandeVisa.getDocType()) {
                // Les IDs textuels (inv_1, tra_1, etc.) sont des identifiants client
                // Essayer d'abord en tant que Long, sinon créer un document type avec le libelle
                DocumentsType documentsType = null;
                
                try {
                    // Essayer de convertir en Long (pour les IDs numériques)
                    Long documentId = Long.parseLong(docId);
                    documentsType = documentsTypeRepository.findById(documentId).orElse(null);
                } catch (NumberFormatException e) {
                    // Si c'est un ID texte (inv_1, tra_1), créer un document type temporaire
                    // avec le libelle basé sur le mapping client
                    String libelle = mapDocIdToLibelle(docId);
                    if (libelle != null && !libelle.isEmpty()) {
                        // Pour les documents spécifiques, créer ou récupérer par libelle
                        // Pour simplifier, on crée un nouveau DocumentsType à chaque fois
                        // Dans une vraie app, on utiliserait une recherche par libelle
                        documentsType = new DocumentsType();
                        documentsType.setLibelle(libelle);
                        documentsType.setIsObligatoire(false);
                        documentsType = documentsTypeRepository.save(documentsType);
                    }
                }
                
                if (documentsType != null) {
                    DemandeDocumentsType demandeDocumentsType = new DemandeDocumentsType();
                    demandeDocumentsType.setIsOk(true);
                    demandeDocumentsType.setDocumentsType(documentsType);
                    demandeDocumentsType.setDemandeur(demandeurSave);
                    demandeDocumentsTypeRepository.save(demandeDocumentsType);
                }
            }
        }

        return demandeurSave.getCode();
    }

    /**
     * Génère un code unique pour la demande
     */
    private String genererCodeDemande() {
        return "VLS-" + java.time.Year.now().getValue() + "-"
                + String.format("%06d", (int) (Math.random() * 900000) + 100000);
    }

    /**
     * Map les IDs textuels des documents spécifiques à leurs libellés
     */
    private String mapDocIdToLibelle(String docId) {
        switch (docId) {
            // Documents Investisseur
            case "inv_1":
                return "Plan d'affaires détaillé";
            case "inv_2":
                return "Preuve de capacité financière (relevés bancaires 3 mois)";
            case "inv_3":
                return "Attestation d'investissement ou lettre d'intention";
            case "inv_4":
                return "Statuts de la société (si déjà constituée)";
            case "inv_5":
                return "Autorisation de l'EDBM (Economic Development Board)";
            case "inv_6":
                return "Rapport d'audit financier (2 dernières années)";
            // Documents Travailleur
            case "tra_1":
                return "Contrat de travail visé par le Ministère du Travail";
            case "tra_2":
                return "Autorisation de travail délivrée par les autorités";
            case "tra_3":
                return "Diplômes et qualifications professionnelles (traduction)";
            case "tra_4":
                return "Lettre de l'employeur sur papier en-tête officiel";
            case "tra_5":
                return "Fiche de poste et description des missions";
            case "tra_6":
                return "Preuve d'enregistrement de l'employeur à Madagascar";
            default:
                return null;
        }
    }
}
