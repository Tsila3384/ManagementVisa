package com.itu.visa.repository;

import com.itu.visa.entity.SituationFamiliale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SituationFamilialeRepository extends JpaRepository<SituationFamiliale, Long> {
}
