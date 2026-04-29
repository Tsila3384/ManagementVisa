package com.itu.visa.service;

import com.itu.visa.entity.Demande;
import com.itu.visa.entity.Demandeur;
import com.itu.visa.repository.DemandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class DuplicataService {
    @Autowired
    private DemandeRepository demandeRepository;

    /**
     * Marque une demande existante comme duplicata (sans créer une nouvelle demande)
     */
    public Demande duplicatedemande(Long demandeId, String remarques) {
        Demande demande = demandeRepository.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande non trouvée"));

        demande.setIsDuplicata(true);
        demande.setDateDuplicata(LocalDate.now());
        return demandeRepository.save(demande);
    }

    /**
     * Crée une nouvelle demande sans copier (demande originale = NULL)
     */
    public Demande creerNouvelledemande(Demandeur demandeur, String remarques) {
        return creerNouvelledemande(demandeur, remarques, false);
    }

    /**
     * Crée une nouvelle demande (optionnellement marquée comme duplicata)
     */
    public Demande creerNouvelledemande(Demandeur demandeur, String remarques, boolean isDuplicata) {
        // Créer une nouvelle demande
        Demande nouvelleDemande = new Demande();
        nouvelleDemande.setDateDemande(LocalDate.now());
        nouvelleDemande.setIsDuplicata(isDuplicata);
        nouvelleDemande.setDateDuplicata(isDuplicata ? LocalDate.now() : null);
        nouvelleDemande.setDemandeur(demandeur);
        // Récupérer le statut par défaut (Ex: "En attente")
        return demandeRepository.save(nouvelleDemande);
    }
}
