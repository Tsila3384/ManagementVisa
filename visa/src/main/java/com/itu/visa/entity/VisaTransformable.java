package com.itu.visa.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "visa_transformable")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisaTransformable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_visa_transformable")
    private Long idVisaTransformable;

    @Column(name = "reference", length = 50)
    private String reference;

    @Column(name = "date_entree_mada")
    private LocalDate dateEntreeMada;

    @Column(name = "lieu", length = 50)
    private String lieu;

    @Column(name = "date_expiration")
    private LocalDate dateExpiration;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_etat_civil", nullable = false, unique = true)
    private EtatCivil etatCivil;
}
