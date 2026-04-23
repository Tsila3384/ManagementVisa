package com.itu.visa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pays")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pays {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pays")
    private Long idPays;

    @Column(name = "libelle", length = 50)
    private String libelle;
}
