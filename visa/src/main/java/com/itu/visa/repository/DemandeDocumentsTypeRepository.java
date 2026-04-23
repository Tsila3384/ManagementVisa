package com.itu.visa.repository;

import com.itu.visa.entity.DemandeDocumentsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeDocumentsTypeRepository extends JpaRepository<DemandeDocumentsType, Long> {
}
