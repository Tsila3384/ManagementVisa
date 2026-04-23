package com.itu.visa.repository;

import com.itu.visa.entity.TypeVisa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeVisaRepository extends JpaRepository<TypeVisa, Long> {
}
