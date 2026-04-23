package com.itu.visa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "documents_communs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentsCommun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documents_commune")
    private Long idDocumentsCommune;

    @Column(name = "libelle", length = 50)
    private String libelle;

    @Column(name = "is_obligatoire")
    private Boolean isObligatoire;
}
