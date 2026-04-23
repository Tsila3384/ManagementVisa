package com.itu.visa.repository;

import com.itu.visa.entity.Sexe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SexeRepository extends JpaRepository<Sexe, Long> {
}
