package com.itu.visa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "type_visa")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeVisa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type_visa")
    private Long idTypeVisa;

    @Column(name = "libelle", length = 50)
    private String libelle;
}
