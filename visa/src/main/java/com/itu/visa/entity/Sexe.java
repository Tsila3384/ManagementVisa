package com.itu.visa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sexe")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sexe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sexe")
    private Long idSexe;

    @Column(name = "libelle", length = 50)
    private String libelle;
}
