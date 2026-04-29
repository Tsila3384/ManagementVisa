package com.itu.visa.controller;

import com.itu.visa.entity.Demande;
import com.itu.visa.entity.Demandeur;
import com.itu.visa.entity.StatutDemande;
import com.itu.visa.repository.DemandeRepository;
import com.itu.visa.repository.DemandeurRepository;
import com.itu.visa.repository.TypeVisaRepository;
import com.itu.visa.repository.TypeDemandeVisaRepository;
import com.itu.visa.repository.StatutDemandeRepository;
import com.itu.visa.service.DuplicataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/duplicata")
public class DuplicataController {
    @Autowired
    private DuplicataService duplicataService;

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private DemandeurRepository demandeurRepository;

    @Autowired
    private TypeVisaRepository typeVisaRepository;

    @Autowired
    private TypeDemandeVisaRepository typeDemandeVisaRepository;

    @Autowired
    private StatutDemandeRepository statutDemandeRepository;

    /**
     * Affiche la page de choix (dupliquer ou nouvelle)
     */
    @GetMapping("/choix")
    public String afficherChoix() {
        return "duplicata-choix";
    }

    /**
     * Affiche le formulaire de duplication
     */
    @GetMapping("/formulaire-duplicate")
    public String afficherFormulaireDuplicate(Model model) {
        List<Demande> demandes = demandeRepository.findAll();
        model.addAttribute("demandes", demandes);
        return "duplicata-formulaire";
    }

    /**
     * Affiche le formulaire de nouvelle demande
     */
    @GetMapping("/nouvelle")
    public String afficherFormulaireNouvelle(Model model) {
        model.addAttribute("typesVisa", typeVisaRepository.findAll());
        model.addAttribute("typesDemandeVisa", typeDemandeVisaRepository.findAll());
        return "duplicata-nouvelle";
    }

    /**
     * Marque directement une demande comme duplicata
     */
    @GetMapping("/marquer/{id}")
    public String marquerDuplicata(@PathVariable("id") Long demandeId) {
        try {
            Demande demande = duplicataService.duplicatedemande(demandeId, null);
            return "redirect:/demandes/" + demande.getIdDemande();
        } catch (Exception e) {
            return "redirect:/demandes?error=Erreur lors du marquage duplicata";
        }
    }

    /**
     * Traite la duplication d'une demande existante
     */
    @PostMapping("/duplicate")
    public String duplicatedemande(
            @RequestParam(value = "demandeId") Long demandeId,
            @RequestParam(value = "remarques", required = false) String remarques
    ) {
        try {
            Demande demande = duplicataService.duplicatedemande(demandeId, remarques);
            return "redirect:/demandes/" + demande.getIdDemande();
        } catch (Exception e) {
            return "redirect:/duplicata/formulaire-duplicate?error=Erreur lors de la duplication";
        }
    }

    /**
     * Crée une nouvelle demande vierge
     */
    @PostMapping("/nouvelle-save")
    public String creerNouvelledemande(
            @RequestParam(value = "typeVisaId") Long typeVisaId,
            @RequestParam(value = "typeDemandeId") Long typeDemandeId,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "remarques", required = false) String remarques,
            @RequestParam(value = "isDuplicata", required = false) Boolean isDuplicata
    ) {
        try {
            // Créer un nouveau demandeur
            var typeVisa = typeVisaRepository.findById(typeVisaId)
                    .orElseThrow(() -> new RuntimeException("Type de visa non trouvé"));
            var typeDemande = typeDemandeVisaRepository.findById(typeDemandeId)
                    .orElseThrow(() -> new RuntimeException("Type de demande non trouvé"));

            Demandeur demandeur = new Demandeur();
            demandeur.setCode(code != null && !code.isEmpty() ? code : "NEW-" + System.currentTimeMillis());
            demandeur.setTypeVisa(typeVisa);
            demandeur.setTypeDemandeVisa(typeDemande);
            demandeur = demandeurRepository.save(demandeur);

            // Récupérer le statut par défaut
            StatutDemande statut = statutDemandeRepository.findAll().stream()
                    .filter(s -> "En attente".equals(s.getLibelle()))
                    .findFirst()
                    .orElse(statutDemandeRepository.findAll().get(0));

                // Créer la nouvelle demande
                boolean duplicataFlag = Boolean.TRUE.equals(isDuplicata);
                Demande nouvelleDemande = duplicataService.creerNouvelledemande(demandeur, remarques, duplicataFlag);
            nouvelleDemande.setStatutDemande(statut);
            nouvelleDemande = demandeRepository.save(nouvelleDemande);

            return "redirect:/demandes/" + nouvelleDemande.getIdDemande();
        } catch (Exception e) {
            return "redirect:/duplicata/nouvelle?error=Erreur lors de la création";
        }
    }
}
