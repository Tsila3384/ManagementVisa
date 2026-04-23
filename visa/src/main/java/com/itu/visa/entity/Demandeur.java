package com.itu.visa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "demandeur")
public class Demandeur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demandeur")
    private Long idDemandeur;

    @Column(name = "code", length = 50)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_visa", nullable = false)
    private TypeVisa typeVisa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_demande_visa", nullable = false)
    private TypeDemandeVisa typeDemandeVisa;

    public Demandeur() {
    }

    public Demandeur(Long idDemandeur, String code, TypeVisa typeVisa, TypeDemandeVisa typeDemandeVisa) {
        this.idDemandeur = idDemandeur;
        this.code = code;
        this.typeVisa = typeVisa;
        this.typeDemandeVisa = typeDemandeVisa;
    }

    public Long getIdDemandeur() {
        return idDemandeur;
    }

    public void setIdDemandeur(Long idDemandeur) {
        this.idDemandeur = idDemandeur;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public TypeVisa getTypeVisa() {
        return typeVisa;
    }

    public void setTypeVisa(TypeVisa typeVisa) {
        this.typeVisa = typeVisa;
    }

    public TypeDemandeVisa getTypeDemandeVisa() {
        return typeDemandeVisa;
    }

    public void setTypeDemandeVisa(TypeDemandeVisa typeDemandeVisa) {
        this.typeDemandeVisa = typeDemandeVisa;
    }
}
