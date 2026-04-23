package com.itu.visa.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "passeport")
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

    public Passeport() {
    }

    public Passeport(Long idPasseport, String numero, LocalDate dateDelivrance, LocalDate dateExpiration,
            EtatCivil etatCivil) {
        this.idPasseport = idPasseport;
        this.numero = numero;
        this.dateDelivrance = dateDelivrance;
        this.dateExpiration = dateExpiration;
        this.etatCivil = etatCivil;
    }

    public Long getIdPasseport() {
        return idPasseport;
    }

    public void setIdPasseport(Long idPasseport) {
        this.idPasseport = idPasseport;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getDateDelivrance() {
        return dateDelivrance;
    }

    public void setDateDelivrance(LocalDate dateDelivrance) {
        this.dateDelivrance = dateDelivrance;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public EtatCivil getEtatCivil() {
        return etatCivil;
    }

    public void setEtatCivil(EtatCivil etatCivil) {
        this.etatCivil = etatCivil;
    }
}
