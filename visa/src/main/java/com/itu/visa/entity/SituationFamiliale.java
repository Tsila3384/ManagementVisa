package com.itu.visa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "situation_familiale")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SituationFamiliale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_situation_familiale")
    private Long idSituationFamiliale;

    @Column(name = "libelle", length = 50)
    private String libelle;
}
