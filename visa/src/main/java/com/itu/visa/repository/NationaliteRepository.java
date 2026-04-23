package com.itu.visa.repository;

import com.itu.visa.entity.Nationalite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NationaliteRepository extends JpaRepository<Nationalite, Long> {
}
