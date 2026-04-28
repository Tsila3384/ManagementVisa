package com.itu.visa.controller;

import com.itu.visa.dto.HistoriqueDocumentDTO;
import com.itu.visa.entity.Demandeur;
import com.itu.visa.entity.DocumentsType;
import com.itu.visa.entity.HistoriqueDocument;
import com.itu.visa.repository.DemandeurRepository;
import com.itu.visa.repository.DocumentsTypeRepository;
import com.itu.visa.repository.DemandeRepository;
import com.itu.visa.service.HistoriqueDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/historique-documents")
public class HistoriqueDocumentController {
    @Autowired
    private HistoriqueDocumentService historiqueDocumentService;

    @Autowired
    private DemandeurRepository demandeurRepository;

    @Autowired
    private DocumentsTypeRepository documentsTypeRepository;
    
    @Autowired
    private DemandeRepository demandeRepository;

    @PostMapping(value = "/add", consumes = "application/x-www-form-urlencoded")
    public Object addHistoriqueDocument(
            @RequestParam(value = "demandeurId") Long demandeurId,
            @RequestParam(value = "documentId") Long documentId
    ) {
        Optional<Demandeur> demandeurOpt = demandeurRepository.findById(demandeurId);
        Optional<DocumentsType> documentOpt = documentsTypeRepository.findById(documentId);
        
        if (demandeurOpt.isEmpty() || documentOpt.isEmpty()) {
            if (demandeurId != null) {
                return "redirect:/ajouter-document?demandeurId=" + demandeurId + "&error=Demandeur+ou+document+introuvable";
            }
            return ResponseEntity.badRequest().body("Demandeur ou document introuvable");
        }
        
        // Charger la demande pour obtenir sa date
        Optional<com.itu.visa.entity.Demande> demandeOpt = demandeRepository.findByDemandeur(demandeurOpt.get());
        LocalDateTime dateRemise = LocalDateTime.now();
        
        if (demandeOpt.isPresent()) {
            // Convertir la date de la demande en LocalDateTime (à minuit)
            dateRemise = demandeOpt.get().getDateDemande().atStartOfDay();
        }
        
        HistoriqueDocument historique = new HistoriqueDocument(
                demandeurOpt.get(),
                documentOpt.get(),
                dateRemise
        );
        historiqueDocumentService.save(historique);
        
        if (demandeOpt.isPresent()) {
            return "redirect:/demandes/" + demandeOpt.get().getIdDemande();
        }
        return ResponseEntity.ok("Historique ajouté");
    }
}