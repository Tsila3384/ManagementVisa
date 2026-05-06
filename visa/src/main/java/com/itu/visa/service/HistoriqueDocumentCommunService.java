package com.itu.visa.service;

import com.itu.visa.entity.HistoriqueDocumentCommun;
import com.itu.visa.repository.HistoriqueDocumentCommunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HistoriqueDocumentCommunService {
    @Autowired
    private HistoriqueDocumentCommunRepository historiqueDocumentCommunRepository;

    public HistoriqueDocumentCommun save(HistoriqueDocumentCommun historiqueDocumentCommun) {
        return historiqueDocumentCommunRepository.save(historiqueDocumentCommun);
    }

    public List<HistoriqueDocumentCommun> findByDemandeurId(Long demandeurId) {
        return historiqueDocumentCommunRepository.findByDemandeurIdOrderByDateRemiseDesc(demandeurId);
    }

    public Set<Long> getDocumentsRemis(Long demandeurId) {
        return historiqueDocumentCommunRepository.findByDemandeurIdOrderByDateRemiseDesc(demandeurId)
                .stream()
                .map(h -> h.getDocument().getIdDocumentsCommune())
                .collect(Collectors.toSet());
    }
}
