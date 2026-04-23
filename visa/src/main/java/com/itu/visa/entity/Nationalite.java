package com.itu.visa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "nationalite")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nationalite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nationalite")
    private Long idNationalite;

    @Column(name = "libelle", length = 50)
    private String libelle;
}
