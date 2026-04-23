package com.itu.visa.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "passeport")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Passeport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_passeport")
    private Long idPasseport;

    @Column(name = "numero", length = 50)
    private String numero;

    @Column(name = "date_delivrance")
    private LocalDate dateDelivrance;

    @Column(name = "date_expiration")
    private LocalDate dateExpiration;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_etat_civil", nullable = false, unique = true)
    private EtatCivil etatCivil;
}
