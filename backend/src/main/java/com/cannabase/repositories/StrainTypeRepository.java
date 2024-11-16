package com.cannabase.repositories;

import com.cannabase.models.StrainType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StrainTypeRepository extends JpaRepository<StrainType, Long> {
    Optional<StrainType> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}