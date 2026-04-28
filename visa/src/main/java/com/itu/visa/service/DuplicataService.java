package com.itu.visa.service;

import com.itu.visa.entity.*;
import com.itu.visa.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DuplicataService {
    @Autowired
    private DuplicataRepository duplicataRepository;

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private DemandeurRepository demandeurRepository;

    @Autowired
    private DemandeDocumentsTypeRepository demandeDocumentsTypeRepository;

    @Autowired
    private DemandeDocumentsCommunRepository demandeDocumentsCommunRepository;

    /**
     * Crée une copie d'une demande existante
     */
    public Demande duplicatedemande(Long demandeOriginalId, String remarques) {
        // Récupérer la demande originale
        Demande demandeOriginal = demandeRepository.findById(demandeOriginalId)
                .orElseThrow(() -> new RuntimeException("Demande originale non trouvée"));

        // Créer un nouveau demandeur copie
        Demandeur demandeurOriginal = demandeOriginal.getDemandeur();
        Demandeur nouveauDemandeur = new Demandeur();
        nouveauDemandeur.setCode(demandeurOriginal.getCode() + "-DUP-" + System.currentTimeMillis());
        nouveauDemandeur.setTypeVisa(demandeurOriginal.getTypeVisa());
        nouveauDemandeur.setTypeDemandeVisa(demandeurOriginal.getTypeDemandeVisa());
        nouveauDemandeur = demandeurRepository.save(nouveauDemandeur);

        // Créer une nouvelle demande
        Demande nouvelleDemande = new Demande();
        nouvelleDemande.setDateDemande(LocalDate.now());
        nouvelleDemande.setDemandeur(nouveauDemandeur);
        nouvelleDemande.setStatutDemande(demandeOriginal.getStatutDemande());
        nouvelleDemande = demandeRepository.save(nouvelleDemande);

        // Copier les documents sélectionnés
        List<DemandeDocumentsType> docsTypeOriginaux = demandeDocumentsTypeRepository.findByDemandeur(demandeurOriginal);
        for (DemandeDocumentsType doc : docsTypeOriginaux) {
            DemandeDocumentsType nouveauDoc = new DemandeDocumentsType();
            nouveauDoc.setDocumentsType(doc.getDocumentsType());
            nouveauDoc.setDemandeur(nouveauDemandeur);
            nouveauDoc.setIsOk(doc.getIsOk());
            demandeDocumentsTypeRepository.save(nouveauDoc);
        }

        // Copier les documents communs
        List<DemandeDocumentsCommun> docsCommaunsOriginaux = demandeDocumentsCommunRepository.findByDemandeur(demandeurOriginal);
        for (DemandeDocumentsCommun doc : docsCommaunsOriginaux) {
            DemandeDocumentsCommun nouveauDoc = new DemandeDocumentsCommun();
            nouveauDoc.setDocumentsCommun(doc.getDocumentsCommun());
            nouveauDoc.setDemandeur(nouveauDemandeur);
            nouveauDoc.setIsOk(doc.getIsOk());
            demandeDocumentsCommunRepository.save(nouveauDoc);
        }

        // Enregistrer le duplicata avec demande originale
        Duplicata duplicata = new Duplicata(demandeOriginal, nouvelleDemande, remarques);
        duplicataRepository.save(duplicata);

        return nouvelleDemande;
    }

    /**
     * Crée une nouvelle demande sans copier (demande originale = NULL)
     */
    public Demande creerNouvelledemande(Demandeur demandeur, String remarques) {
        // Créer une nouvelle demande
        Demande nouvelleDemande = new Demande();
        nouvelleDemande.setDateDemande(LocalDate.now());
        nouvelleDemande.setDemandeur(demandeur);
        // Récupérer le statut par défaut (Ex: "En attente")
        nouvelleDemande = demandeRepository.save(nouvelleDemande);

        // Enregistrer dans duplicata avec demandeOriginal = NULL
        Duplicata duplicata = new Duplicata();
        duplicata.setDemandeOriginal(null); // Pas de demande originale
        duplicata.setDemandeDuplicata(nouvelleDemande);
        duplicata.setRemarques(remarques);
        duplicata.setDateCreation(LocalDateTime.now());
        duplicataRepository.save(duplicata);

        return nouvelleDemande;
    }

    public Duplicata saveDuplicata(Duplicata duplicata) {
        return duplicataRepository.save(duplicata);
    }

    public List<Duplicata> findByDemandeOriginal(Demande demande) {
        return duplicataRepository.findByDemandeOriginal(demande);
    }
}
