package com.itu.visa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "demandeur")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
