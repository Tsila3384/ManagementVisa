package com.itu.visa.repository;

import com.itu.visa.entity.EtatCivil;
import com.itu.visa.entity.Demandeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EtatCivilRepository extends JpaRepository<EtatCivil, Long> {
    Optional<EtatCivil> findByDemandeur(Demandeur demandeur);
}
