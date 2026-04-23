package com.itu.visa.repository;

import com.itu.visa.entity.Passeport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasseportRepository extends JpaRepository<Passeport, Long> {
}
