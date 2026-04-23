package com.itu.visa.repository;

import com.itu.visa.entity.TypeDemandeVisa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeDemandeVisaRepository extends JpaRepository<TypeDemandeVisa, Long> {
}
