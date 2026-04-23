package com.itu.visa.repository;

import com.itu.visa.entity.Demandeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeurRepository extends JpaRepository<Demandeur, Long> {
}
