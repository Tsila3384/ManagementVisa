package com.itu.visa.repository;

import com.itu.visa.entity.Duplicata;
import com.itu.visa.entity.Demande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DuplicataRepository extends JpaRepository<Duplicata, Long> {
    List<Duplicata> findByDemandeOriginal(Demande demandeOriginal);
    List<Duplicata> findByDemandeDuplicata(Demande demandeDuplicata);
}
