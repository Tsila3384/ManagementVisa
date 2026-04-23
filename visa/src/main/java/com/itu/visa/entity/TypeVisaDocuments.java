package com.itu.visa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "type_visa_documents")
@IdClass(TypeVisaDocumentsId.class)
public class TypeVisaDocuments {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_visa", nullable = false)
    private TypeVisa typeVisa;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_documents_types", nullable = false)
    private DocumentsType documentsType;

    public TypeVisaDocuments() {
    }

    public TypeVisaDocuments(TypeVisa typeVisa, DocumentsType documentsType) {
        this.typeVisa = typeVisa;
        this.documentsType = documentsType;
    }

    public TypeVisa getTypeVisa() {
        return typeVisa;
    }

    public void setTypeVisa(TypeVisa typeVisa) {
        this.typeVisa = typeVisa;
    }

    public DocumentsType getDocumentsType() {
        return documentsType;
    }

    public void setDocumentsType(DocumentsType documentsType) {
        this.documentsType = documentsType;
    }
}
