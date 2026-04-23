package com.itu.visa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "demandeur_documents_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DemandeDocumentsType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demandeur_documents")
    private Long idDemandeDocuments;

    @Column(name = "is_ok")
    private Boolean isOk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_documents_types", nullable = false)
    private DocumentsType documentsType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_demandeur", nullable = false)
    private Demandeur demandeur;
}
