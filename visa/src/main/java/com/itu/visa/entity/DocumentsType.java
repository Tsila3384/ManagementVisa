package com.itu.visa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "documents_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentsType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documents_types")
    private Long idDocumentsTypes;

    @Column(name = "libelle", length = 50)
    private String libelle;

    @Column(name = "is_obligatoire")
    private Boolean isObligatoire;
}
