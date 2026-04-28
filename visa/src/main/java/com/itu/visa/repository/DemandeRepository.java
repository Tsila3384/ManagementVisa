package com.itu.visa.repository;

import com.itu.visa.entity.Demande;
import com.itu.visa.entity.Demandeur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DemandeRepository extends JpaRepository<Demande, Long> {
    Optional<Demande> findByDemandeur(Demandeur demandeur);
}
