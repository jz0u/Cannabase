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
    boolean existsByNameIgnoreCase(String name);
    List<Strain> findByNameContainingIgnoreCase(String name);
    List<Strain> findByType_NameIgnoreCase(String typeName);
    List<Strain> findByNameContainingIgnoreCaseAndType_NameIgnoreCase(String name, String typeName);
    List<Strain> searchByName(String name);
    List<Strain> findByTypeNameIgnoreCase(String typeName);
    long countByType_Name(String typeName);
}
