package com.itu.visa.repository;

import com.itu.visa.entity.DocumentsCommun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsCommunRepository extends JpaRepository<DocumentsCommun, Long> {
}
