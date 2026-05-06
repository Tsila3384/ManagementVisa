package com.itu.visa.controller;

import com.itu.visa.entity.HistoriqueDocumentCommun;
import com.itu.visa.repository.DemandeRepository;
import com.itu.visa.repository.DemandeurRepository;
import com.itu.visa.repository.DocumentsCommunRepository;
import com.itu.visa.service.HistoriqueDocumentCommunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class HistoriqueDocumentCommunController {

    @Autowired
    private DemandeurRepository demandeurRepository;

    @Autowired
    private DocumentsCommunRepository documentsCommunRepository;

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private HistoriqueDocumentCommunService historiqueDocumentCommunService;

    @PostMapping("/api/historique-documents-communs/add")
    public Object addHistoriqueCommun(
            @RequestParam("demandeurId") Long demandeurId,
            @RequestParam("documentId") Long documentId
    ) {
        var demandeurOpt = demandeurRepository.findById(demandeurId);
        var documentOpt = documentsCommunRepository.findById(documentId);

        if (demandeurOpt.isEmpty() || documentOpt.isEmpty()) {
            if (demandeurId != null) {
                return "redirect:/ajouter-document-commun?demandeurId=" + demandeurId + "&error=Demandeur+ou+document+introuvable";
            }
            return ResponseEntity.badRequest().body("Demandeur ou document introuvable");
        }

        Optional<com.itu.visa.entity.Demande> demandeOpt = demandeRepository.findByDemandeur(demandeurOpt.get());
        LocalDateTime dateRemise = LocalDateTime.now();
        if (demandeOpt.isPresent()) {
            dateRemise = demandeOpt.get().getDateDemande().atStartOfDay();
        }

        HistoriqueDocumentCommun historique = new HistoriqueDocumentCommun(
                demandeurOpt.get(),
                documentOpt.get(),
                dateRemise
        );

        historiqueDocumentCommunService.save(historique);

        if (demandeOpt.isPresent()) {
            return "redirect:/demandes/" + demandeOpt.get().getIdDemande();
        }
        return ResponseEntity.ok("Historique commun ajouté");
    }
}
