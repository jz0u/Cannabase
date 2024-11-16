package com.cannabase.repositories;

import com.cannabase.models.StrainType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrainTypeRepository extends JpaRepository<StrainType, Long> {
    boolean existsByName(String name);
}