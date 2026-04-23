package com.itu.visa.controller;

import com.itu.visa.dto.DemandeVisaDTO;
import com.itu.visa.repository.*;
import com.itu.visa.service.DemandeVisaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    private final SexeRepository sexeRepository;
    private final SituationFamilialeRepository situationFamilialeRepository;
    private final NationaliteRepository nationaliteRepository;
    private final PaysRepository paysRepository;
    private final TypeVisaRepository typeVisaRepository;
    private final TypeDemandeVisaRepository typeDemandeVisaRepository;
    private final StatutDemandeRepository statutDemandeRepository;
    private final DemandeVisaService demandeVisaService;

    public HomeController(SexeRepository sexeRepository,
                          SituationFamilialeRepository situationFamilialeRepository,
                          NationaliteRepository nationaliteRepository,
                          PaysRepository paysRepository,
                          TypeVisaRepository typeVisaRepository,
                          TypeDemandeVisaRepository typeDemandeVisaRepository,
                          StatutDemandeRepository statutDemandeRepository,
                          DemandeVisaService demandeVisaService) {
        this.sexeRepository = sexeRepository;
        this.situationFamilialeRepository = situationFamilialeRepository;
        this.nationaliteRepository = nationaliteRepository;
        this.paysRepository = paysRepository;
        this.typeVisaRepository = typeVisaRepository;
        this.typeDemandeVisaRepository = typeDemandeVisaRepository;
        this.statutDemandeRepository = statutDemandeRepository;
        this.demandeVisaService = demandeVisaService;
    }

    /**
     * Affiche la page d'accueil du formulaire de demande de visa
     * 
     * @param model Modèle pour passer les données à la vue
     * @return La vue index
     */
    @GetMapping("/")
    public String index(Model model) {
        loadFormData(model);
        return "index";
    }

    /**
     * Route alternative pour accéder au formulaire
     * 
     * @param model Modèle pour passer les données à la vue
     * @return La vue index
     */
    @GetMapping("/visa")
    public String visaForm(Model model) {
        loadFormData(model);
        return "index";
    }

    /**
     * Traite la soumission du formulaire de demande de visa
     * 
     * @param demandeVisa Données du formulaire
     * @return Message de succès ou redirection
     */
    @PostMapping("/api/demande-visa")
    @ResponseBody
    public Map<String, Object> submitDemande(@RequestBody DemandeVisaDTO demandeVisa) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Validation de base
            if (demandeVisa.getNom() == null || demandeVisa.getNom().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Le nom est requis");
                return response;
            }

            if (demandeVisa.getPrenom() == null || demandeVisa.getPrenom().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Le prénom est requis");
                return response;
            }

            // Traiter la demande
            String refCode = demandeVisaService.traiterDemande(demandeVisa);

            response.put("success", true);
            response.put("message", "Demande soumise avec succès");
            response.put("refCode", refCode);
            return response;
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Erreur lors de la soumission: " + e.getMessage());
            e.printStackTrace();
            return response;
        }
    }

    /**
     * Charge les données nécessaires pour le formulaire
     */
    private void loadFormData(Model model) {
        model.addAttribute("sexes", sexeRepository.findAll());
        model.addAttribute("situationsFamiliales", situationFamilialeRepository.findAll());
        model.addAttribute("nationalites", nationaliteRepository.findAll());
        model.addAttribute("pays", paysRepository.findAll());
        model.addAttribute("typesVisa", typeVisaRepository.findAll());
        model.addAttribute("typesDemandeVisa", typeDemandeVisaRepository.findAll());
        model.addAttribute("statutsDemande", statutDemandeRepository.findAll());
    }
}
