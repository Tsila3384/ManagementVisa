package com.itu.visa.repository;

import com.itu.visa.entity.DemandeDocumentsCommun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeDocumentsCommunRepository extends JpaRepository<DemandeDocumentsCommun, Long> {
}
