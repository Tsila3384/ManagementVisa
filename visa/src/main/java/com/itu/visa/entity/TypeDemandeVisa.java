package com.itu.visa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "type_demande_visa")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeDemandeVisa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_demande_visa")
    private Long idTypeDemandeVisa;

    @Column(name = "libelle", length = 50)
    private String libelle;
}
