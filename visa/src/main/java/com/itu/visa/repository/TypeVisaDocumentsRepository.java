package com.itu.visa.repository;

import com.itu.visa.entity.TypeVisaDocuments;
import com.itu.visa.entity.TypeVisaDocumentsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeVisaDocumentsRepository extends JpaRepository<TypeVisaDocuments, TypeVisaDocumentsId> {
}
