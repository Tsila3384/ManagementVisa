package com.itu.visa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "demandeur_documents_communs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DemandeDocumentsCommun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demandeur_documents_communs")
    private Long idDemandeDocumentsCommuns;

    @Column(name = "is_ok")
    private Boolean isOk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_demandeur", nullable = false)
    private Demandeur demandeur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_documents_commune", nullable = false)
    private DocumentsCommun documentsCommun;
}
