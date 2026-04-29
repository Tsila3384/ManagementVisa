package com.itu.visa.controller;

import com.itu.visa.dto.DemandeVisaDTO;
import com.itu.visa.repository.*;
import com.itu.visa.service.DemandeVisaService;
import com.itu.visa.service.HistoriqueDocumentService;
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
    private final DocumentsCommunRepository documentsCommunRepository;
    private final DocumentsTypeRepository documentsTypeRepository;
    private final DemandeRepository demandeRepository;
    private final DemandeurRepository demandeurRepository;
    private final DemandeDocumentsCommunRepository demandeDocumentsCommunRepository;
    private final DemandeDocumentsTypeRepository demandeDocumentsTypeRepository;
    private final EtatCivilRepository etatCivilRepository;
    private final HistoriqueDocumentService historiqueDocumentService;
    private final HistoriqueDocumentRepository historiqueDocumentRepository;
    private final DuplicataRepository duplicataRepository;

    public HomeController(SexeRepository sexeRepository,
                          SituationFamilialeRepository situationFamilialeRepository,
                          NationaliteRepository nationaliteRepository,
                          PaysRepository paysRepository,
                          TypeVisaRepository typeVisaRepository,
                          TypeDemandeVisaRepository typeDemandeVisaRepository,
                          StatutDemandeRepository statutDemandeRepository,
                          DemandeVisaService demandeVisaService,
                          DocumentsCommunRepository documentsCommunRepository,
                          DocumentsTypeRepository documentsTypeRepository,
                          DemandeRepository demandeRepository,
                          DemandeurRepository demandeurRepository,
                          DemandeDocumentsCommunRepository demandeDocumentsCommunRepository,
                          DemandeDocumentsTypeRepository demandeDocumentsTypeRepository,
                          EtatCivilRepository etatCivilRepository,
                          HistoriqueDocumentService historiqueDocumentService,
                          HistoriqueDocumentRepository historiqueDocumentRepository,
                          DuplicataRepository duplicataRepository) {
        this.sexeRepository = sexeRepository;
        this.situationFamilialeRepository = situationFamilialeRepository;
        this.nationaliteRepository = nationaliteRepository;
        this.paysRepository = paysRepository;
        this.typeVisaRepository = typeVisaRepository;
        this.typeDemandeVisaRepository = typeDemandeVisaRepository;
        this.statutDemandeRepository = statutDemandeRepository;
        this.demandeVisaService = demandeVisaService;
        this.documentsCommunRepository = documentsCommunRepository;
        this.documentsTypeRepository = documentsTypeRepository;
        this.demandeRepository = demandeRepository;
        this.demandeurRepository = demandeurRepository;
        this.demandeDocumentsCommunRepository = demandeDocumentsCommunRepository;
        this.demandeDocumentsTypeRepository = demandeDocumentsTypeRepository;
        this.etatCivilRepository = etatCivilRepository;
        this.historiqueDocumentService = historiqueDocumentService;
        this.historiqueDocumentRepository = historiqueDocumentRepository;
        this.duplicataRepository = duplicataRepository;
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

            // Validation des documents obligatoires
            String validationMessage = validerDocumentsObligatoires(demandeVisa);
            if (validationMessage != null) {
                response.put("success", false);
                response.put("message", validationMessage);
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
     * Valide que tous les documents obligatoires ont été cochés
     * 
     * @param demandeVisa Données du formulaire
     * @return Message d'erreur s'il y a un problème, null sinon
     */
    private String validerDocumentsObligatoires(DemandeVisaDTO demandeVisa) {
        // Vérifier les documents communs obligatoires
        var docsCommObligatoires = documentsCommunRepository.findAll().stream()
                .filter(d -> d.getIsObligatoire() != null && d.getIsObligatoire())
                .toList();

        for (var docObligatoire : docsCommObligatoires) {
            if (demandeVisa.getDocCommun() == null || 
                !demandeVisa.getDocCommun().contains(docObligatoire.getIdDocumentsCommune().toString())) {
                return "Document obligatoire manquant: " + docObligatoire.getLibelle();
            }
        }

        // Vérifier les documents spécifiques obligatoires
        var docsTypeObligatoires = documentsTypeRepository.findAll().stream()
                .filter(d -> d.getIsObligatoire() != null && d.getIsObligatoire())
                .toList();

        for (var docObligatoire : docsTypeObligatoires) {
            if (demandeVisa.getDocType() == null || 
                !demandeVisa.getDocType().contains(docObligatoire.getIdDocumentsTypes().toString())) {
                return "Document obligatoire manquant: " + docObligatoire.getLibelle();
            }
        }

        return null; // Tous les documents obligatoires sont cochés
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

    /**
     * Récupère tous les documents communs
     */
    @GetMapping("/api/documents-communs")
    @ResponseBody
    public java.util.List<Map<String, Object>> getDocumentsCommuns() {
        return documentsCommunRepository.findAll().stream()
            .map(doc -> {
                Map<String, Object> docMap = new HashMap<>();
                docMap.put("id", doc.getIdDocumentsCommune());
                docMap.put("label", doc.getLibelle());
                docMap.put("isObligatoire", doc.getIsObligatoire() != null ? doc.getIsObligatoire() : false);
                return docMap;
            })
            .toList();
    }

    /**
     * Récupère les documents de type pour un type de visa
     */
    @GetMapping("/api/documents-type/{typeVisaId}")
    @ResponseBody
    public java.util.List<Map<String, Object>> getDocumentsType(@PathVariable Long typeVisaId) {
        return documentsTypeRepository.findAll().stream()
            .map(doc -> {
                Map<String, Object> docMap = new HashMap<>();
                docMap.put("id", doc.getIdDocumentsTypes());
                docMap.put("label", doc.getLibelle());
                docMap.put("isObligatoire", doc.getIsObligatoire() != null ? doc.getIsObligatoire() : false);
                return docMap;
            })
            .toList();
    }

    /**
     * Affiche la page de liste des demandes
     */
    @GetMapping("/demandes")
    public String listDemandes(Model model) {
        var demandes = demandeRepository.findAll();
        var demandesWithDetails = demandes.stream()
            .map(demande -> {
                var etatCivil = etatCivilRepository.findByDemandeur(demande.getDemandeur()).orElse(null);
                Map<String, Object> demandeMap = new HashMap<>();
                demandeMap.put("demande", demande);
                demandeMap.put("etatCivil", etatCivil);
                demandeMap.put("hasDuplicata", Boolean.TRUE.equals(demande.getIsDuplicata()));
                return demandeMap;
            })
            .toList();
        
        model.addAttribute("demandesWithDetails", demandesWithDetails);
        return "demandes";
    }

    /**
     * Affiche les détails d'une demande
     */
    @GetMapping("/demandes/{id}")
    public String detailsDemande(@PathVariable Long id, Model model) {
        var demande = demandeRepository.findById(id).orElse(null);
        if (demande == null) {
            return "redirect:/demandes";
        }

        var demandeur = demande.getDemandeur();
        var etatCivil = etatCivilRepository.findByDemandeur(demandeur).orElse(null);
        var docsCommuns = demandeDocumentsCommunRepository.findAll().stream()
            .filter(d -> d.getDemandeur().getIdDemandeur().equals(demandeur.getIdDemandeur()))
            .toList();
        var docsTypes = demandeDocumentsTypeRepository.findAll().stream()
            .filter(d -> d.getDemandeur().getIdDemandeur().equals(demandeur.getIdDemandeur()))
            .toList();
        
        // Charger l'historique des documents remis
        var historiqueDocuments = historiqueDocumentRepository.findByDemandeurIdOrderByDateRemiseDesc(demandeur.getIdDemandeur());

        model.addAttribute("demande", demande);
        model.addAttribute("demandeur", demandeur);
        model.addAttribute("etatCivil", etatCivil);
        model.addAttribute("docsCommuns", docsCommuns);
        model.addAttribute("docsTypes", docsTypes);
        model.addAttribute("historiqueDocuments", historiqueDocuments);
        
        return "detail-demande";
    }
}
