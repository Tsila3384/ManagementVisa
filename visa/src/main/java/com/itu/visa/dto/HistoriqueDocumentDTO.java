package com.itu.visa.dto;

import java.time.LocalDateTime;

public class HistoriqueDocumentDTO {
    private Long demandeurId;
    private Long documentId;
    private LocalDateTime dateRemise;

    public HistoriqueDocumentDTO() {}

    public HistoriqueDocumentDTO(Long demandeurId, Long documentId, LocalDateTime dateRemise) {
        this.demandeurId = demandeurId;
        this.documentId = documentId;
        this.dateRemise = dateRemise;
    }

    public Long getDemandeurId() {
        return demandeurId;
    }

    public void setDemandeurId(Long demandeurId) {
        this.demandeurId = demandeurId;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public LocalDateTime getDateRemise() {
        return dateRemise;
    }

    public void setDateRemise(LocalDateTime dateRemise) {
        this.dateRemise = dateRemise;
    }
}