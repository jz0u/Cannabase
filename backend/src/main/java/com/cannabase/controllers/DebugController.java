package com.cannabase.controllers;

import com.cannabase.models.Strain;
import com.cannabase.models.StrainType;
import com.cannabase.repositories.StrainRepository;
import com.cannabase.repositories.StrainTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/debug")
public class DebugController {
    
    @Autowired
    private StrainRepository strainRepository;
    
    @Autowired
    private StrainTypeRepository strainTypeRepository;
    
    @GetMapping("/database-stats")
    public ResponseEntity<Map<String, Object>> getDatabaseStats() {
        Map<String, Object> stats = new HashMap<>();
        
        // Get total counts
        stats.put("totalStrains", strainRepository.count());
        stats.put("totalTypes", strainTypeRepository.count());
        
        // Get counts by type
        List<StrainType> types = strainTypeRepository.findAll();
        Map<String, Long> strainsByType = new HashMap<>();
        for (StrainType type : types) {
            long count = strainRepository.countByType_Name(type.getName());
            strainsByType.put(type.getName(), count);
        }
        stats.put("strainsByType", strainsByType);
        
        // Get sample strains
        stats.put("sampleStrains", strainRepository.findAll()
            .stream()
            .limit(5)
            .map(s -> Map.of(
                "id", s.getId(),
                "name", s.getName(),
                "type", s.getType() != null ? s.getType().getName() : "null"
            ))
            .collect(Collectors.toList()));
            
        return ResponseEntity.ok(stats);
    }
}