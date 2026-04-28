package com.itu.visa.repository;

import com.itu.visa.entity.DemandeDocumentsCommun;
import com.itu.visa.entity.Demandeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeDocumentsCommunRepository extends JpaRepository<DemandeDocumentsCommun, Long> {
    List<DemandeDocumentsCommun> findByDemandeur(Demandeur demandeur);
}
