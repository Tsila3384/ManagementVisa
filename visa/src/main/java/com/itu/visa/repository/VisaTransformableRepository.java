package com.itu.visa.repository;

import com.itu.visa.entity.VisaTransformable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisaTransformableRepository extends JpaRepository<VisaTransformable, Long> {
}
