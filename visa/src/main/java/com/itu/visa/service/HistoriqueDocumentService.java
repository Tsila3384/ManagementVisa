package com.itu.visa.service;

import com.itu.visa.entity.HistoriqueDocument;
import com.itu.visa.entity.Demandeur;
import com.itu.visa.entity.DocumentsType;
import com.itu.visa.repository.HistoriqueDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HistoriqueDocumentService {
    @Autowired
    private HistoriqueDocumentRepository historiqueDocumentRepository;

    public HistoriqueDocument save(HistoriqueDocument historiqueDocument) {
        return historiqueDocumentRepository.save(historiqueDocument);
    }

    public List<HistoriqueDocument> findByDemandeurId(Long demandeurId) {
        return historiqueDocumentRepository.findByDemandeurIdOrderByDateRemiseDesc(demandeurId);
    }

    /**
     * Récupère les IDs des documents déjà remis pour un demandeur
     */
    public Set<Long> getDocumentsRemis(Long demandeurId) {
        return historiqueDocumentRepository.findByDemandeurIdOrderByDateRemiseDesc(demandeurId)
                .stream()
                .map(h -> h.getDocument().getIdDocumentsTypes())
                .collect(Collectors.toSet());
    }
}