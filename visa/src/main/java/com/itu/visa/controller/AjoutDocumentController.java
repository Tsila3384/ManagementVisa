package com.itu.visa.controller;

import com.itu.visa.entity.Demandeur;
import com.itu.visa.entity.DocumentsType;
import com.itu.visa.repository.DemandeurRepository;
import com.itu.visa.repository.DocumentsTypeRepository;
import com.itu.visa.repository.DemandeDocumentsTypeRepository;
import com.itu.visa.repository.DemandeDocumentsCommunRepository;
import com.itu.visa.service.HistoriqueDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class AjoutDocumentController {
    @Autowired
    private DemandeurRepository demandeurRepository;

    @Autowired
    private DocumentsTypeRepository documentsTypeRepository;
    
    @Autowired
    private HistoriqueDocumentService historiqueDocumentService;
    
    @Autowired
    private DemandeDocumentsTypeRepository demandeDocumentsTypeRepository;
    
    @Autowired
    private DemandeDocumentsCommunRepository demandeDocumentsCommunRepository;

    @GetMapping("/ajouter-document")
    public String afficherFormulaireAjout(@RequestParam("demandeurId") Long demandeurId, Model model) {
        Optional<Demandeur> demandeurOpt = demandeurRepository.findById(demandeurId);
        if (demandeurOpt.isEmpty()) {
            model.addAttribute("error", "Demandeur introuvable");
            return "ajouter-document";
        }
        
        // Récupérer les IDs des documents déjà remis
        Set<Long> documentsRemis = historiqueDocumentService.getDocumentsRemis(demandeurId);
        
        // Récupérer les IDs des documents sélectionnés lors de la demande (marqués OK)
        Set<Long> documentsSelectionneses = demandeDocumentsTypeRepository.findByDemandeurAndIsOkTrue(demandeurOpt.get())
                .stream()
                .map(d -> d.getDocumentsType().getIdDocumentsTypes())
                .collect(java.util.stream.Collectors.toSet());
        
        // Fusionner les deux sets
        documentsRemis.addAll(documentsSelectionneses);
        
        // Récupérer tous les documents et filtrer ceux non remis ET non sélectionnés
        List<DocumentsType> documents = documentsTypeRepository.findAll().stream()
                .filter(doc -> !documentsRemis.contains(doc.getIdDocumentsTypes()))
                .toList();
        
        model.addAttribute("demandeur", demandeurOpt.get());
        model.addAttribute("documents", documents);
        return "ajouter-document";
    }
}