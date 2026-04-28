package com.itu.visa.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "duplicata")
public class Duplicata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_duplicata")
    private Long idDuplicata;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_demande_original")
    private Demande demandeOriginal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_demande_duplicata", nullable = false)
    private Demande demandeDuplicata;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "remarques", length = 500)
    private String remarques;

    public Duplicata() {}

    public Duplicata(Demande demandeOriginal, Demande demandeDuplicata, String remarques) {
        this.demandeOriginal = demandeOriginal;
        this.demandeDuplicata = demandeDuplicata;
        this.remarques = remarques;
        this.dateCreation = LocalDateTime.now();
    }

    public Long getIdDuplicata() {
        return idDuplicata;
    }

    public void setIdDuplicata(Long idDuplicata) {
        this.idDuplicata = idDuplicata;
    }

    public Demande getDemandeOriginal() {
        return demandeOriginal;
    }

    public void setDemandeOriginal(Demande demandeOriginal) {
        this.demandeOriginal = demandeOriginal;
    }

    public Demande getDemandeDuplicata() {
        return demandeDuplicata;
    }

    public void setDemandeDuplicata(Demande demandeDuplicata) {
        this.demandeDuplicata = demandeDuplicata;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getRemarques() {
        return remarques;
    }

    public void setRemarques(String remarques) {
        this.remarques = remarques;
    }
}
