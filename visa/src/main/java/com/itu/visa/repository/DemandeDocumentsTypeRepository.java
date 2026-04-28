package com.itu.visa.repository;

import com.itu.visa.entity.DemandeDocumentsType;
import com.itu.visa.entity.Demandeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeDocumentsTypeRepository extends JpaRepository<DemandeDocumentsType, Long> {
    List<DemandeDocumentsType> findByDemandeurAndIsOkTrue(Demandeur demandeur);
    List<DemandeDocumentsType> findByDemandeur(Demandeur demandeur);
}
