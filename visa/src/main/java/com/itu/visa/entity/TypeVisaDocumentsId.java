package com.itu.visa.entity;

import java.io.Serializable;

public class TypeVisaDocumentsId implements Serializable {
    private Long typeVisa;
    private Long documentsType;

    public TypeVisaDocumentsId() {
    }

    public TypeVisaDocumentsId(Long typeVisa, Long documentsType) {
        this.typeVisa = typeVisa;
        this.documentsType = documentsType;
    }

    public Long getTypeVisa() {
        return typeVisa;
    }

    public void setTypeVisa(Long typeVisa) {
        this.typeVisa = typeVisa;
    }

    public Long getDocumentsType() {
        return documentsType;
    }

    public void setDocumentsType(Long documentsType) {
        this.documentsType = documentsType;
    }
}
