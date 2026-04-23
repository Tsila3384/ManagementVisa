package com.itu.visa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "type_visa_documents")
@IdClass(TypeVisaDocumentsId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeVisaDocuments {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_visa", nullable = false)
    private TypeVisa typeVisa;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_documents_types", nullable = false)
    private DocumentsType documentsType;
}
