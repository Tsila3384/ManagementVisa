package com.itu.visa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "statut_demande")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatutDemande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_statut_demande")
    private Long idStatutDemande;

    @Column(name = "libelle", length = 50)
    private String libelle;
}
