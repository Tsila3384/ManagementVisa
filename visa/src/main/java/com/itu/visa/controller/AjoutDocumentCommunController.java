package com.itu.visa.controller;

import com.itu.visa.entity.Demandeur;
import com.itu.visa.entity.DocumentsCommun;
import com.itu.visa.repository.DemandeDocumentsCommunRepository;
import com.itu.visa.repository.DemandeurRepository;
import com.itu.visa.repository.DocumentsCommunRepository;
import com.itu.visa.service.HistoriqueDocumentCommunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class AjoutDocumentCommunController {

    @Autowired
    private DemandeurRepository demandeurRepository;

    @Autowired
    private DocumentsCommunRepository documentsCommunRepository;

    @Autowired
    private DemandeDocumentsCommunRepository demandeDocumentsCommunRepository;

    @Autowired
    private HistoriqueDocumentCommunService historiqueDocumentCommunService;

    @GetMapping("/ajouter-document-commun")
    public String afficherFormulaireAjout(@RequestParam("demandeurId") Long demandeurId, Model model) {
        Optional<Demandeur> demandeurOpt = demandeurRepository.findById(demandeurId);
        if (demandeurOpt.isEmpty()) {
            model.addAttribute("error", "Demandeur introuvable");
            return "ajouter-document-commun";
        }

        Demandeur demandeur = demandeurOpt.get();

        // Documents communs déjà remis (historique)
        Set<Long> documentsRemis = historiqueDocumentCommunService.getDocumentsRemis(demandeurId);

        // Documents communs sélectionnés lors de la demande (marqués OK)
        Set<Long> documentsSelectionnes = demandeDocumentsCommunRepository.findByDemandeur(demandeur)
            .stream()
            .filter(d -> Boolean.TRUE.equals(d.getIsOk()))
            .map(d -> d.getDocumentsCommun().getIdDocumentsCommune())
            .collect(Collectors.toSet());

        documentsRemis.addAll(documentsSelectionnes);

        List<DocumentsCommun> documents = documentsCommunRepository.findAll().stream()
            .filter(doc -> !documentsRemis.contains(doc.getIdDocumentsCommune()))
                .toList();

        model.addAttribute("demandeur", demandeur);
        model.addAttribute("documents", documents);
        return "ajouter-document-commun";
    }
}
