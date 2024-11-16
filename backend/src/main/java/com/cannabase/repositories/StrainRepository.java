package com.cannabase.repositories;

import com.cannabase.models.Strain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StrainRepository extends JpaRepository<Strain, Long> {
    // Find strains by name (case-insensitive)
    List<Strain> findByNameContainingIgnoreCase(String name);
    
    // Find strains by type (case-insensitive)
    List<Strain> findByType_NameIgnoreCase(String typeName);
    
    // Check if strain exists by name (case-insensitive)
    boolean existsByNameIgnoreCase(String name);
    
    // Count strains by type
    long countByType_Name(String typeName);
}