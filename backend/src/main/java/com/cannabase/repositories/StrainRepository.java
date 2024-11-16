package com.cannabase.repositories;

import com.cannabase.models.Strain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StrainRepository extends JpaRepository<Strain, Long> {
    boolean existsByName(String name);
    
    @Query("SELECT s FROM Strain s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Strain> searchByName(String searchTerm);
}