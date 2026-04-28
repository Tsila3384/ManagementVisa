package com.itu.visa.repository;

import com.itu.visa.entity.HistoriqueDocument;
import com.itu.visa.entity.Demandeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoriqueDocumentRepository extends JpaRepository<HistoriqueDocument, Long> {
    List<HistoriqueDocument> findByDemandeur(Demandeur demandeur);
    
    @Query("SELECT DISTINCT h FROM HistoriqueDocument h " +
           "JOIN FETCH h.document d " +
           "WHERE h.demandeur.idDemandeur = :demandeurId " +
           "ORDER BY h.dateRemise DESC")
    List<HistoriqueDocument> findByDemandeurIdOrderByDateRemiseDesc(@Param("demandeurId") Long demandeurId);
}