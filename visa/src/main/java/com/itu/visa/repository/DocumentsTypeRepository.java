package com.itu.visa.repository;

import com.itu.visa.entity.DocumentsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsTypeRepository extends JpaRepository<DocumentsType, Long> {
}
