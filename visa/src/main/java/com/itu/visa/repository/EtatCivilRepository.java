package com.itu.visa.repository;

import com.itu.visa.entity.EtatCivil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtatCivilRepository extends JpaRepository<EtatCivil, Long> {
}
