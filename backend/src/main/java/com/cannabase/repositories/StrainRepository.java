package com.cannabase.repositories;

import com.cannabase.models.Strain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StrainRepository extends JpaRepository<Strain, Long> {
    Optional<Strain> findByNameIgnoreCase(String name);
    
    boolean existsByNameIgnoreCase(String name);
    
    @Query("SELECT s FROM Strain s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Strain> searchByName(@Param("name") String name);
    
    // Additional useful queries
    List<Strain> findByTypeNameIgnoreCase(String typeName);
    List<Strain> findByThcContentGreaterThan(double thcContent);
    List<Strain> findByCbdContentGreaterThan(double cbdContent);
}