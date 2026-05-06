package com.itu.visa.repository;

import com.itu.visa.entity.HistoriqueDocumentCommun;
import com.itu.visa.entity.Demandeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoriqueDocumentCommunRepository extends JpaRepository<HistoriqueDocumentCommun, Long> {
    List<HistoriqueDocumentCommun> findByDemandeur(Demandeur demandeur);

    @Query("SELECT DISTINCT h FROM HistoriqueDocumentCommun h " +
           "JOIN FETCH h.document d " +
           "WHERE h.demandeur.idDemandeur = :demandeurId " +
           "ORDER BY h.dateRemise DESC")
    List<HistoriqueDocumentCommun> findByDemandeurIdOrderByDateRemiseDesc(@Param("demandeurId") Long demandeurId);
}
