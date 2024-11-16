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
@RequestMapping("/api/v1/test")
public class TestController {
    
    @Autowired
    private StrainRepository strainRepository;
    
    @Autowired
    private StrainTypeRepository strainTypeRepository;
    
    @GetMapping("/db-status")
    public ResponseEntity<?> getDatabaseStatus() {
        Map<String, Object> status = new HashMap<>();
        
        try {
            // Get counts
            long strainCount = strainRepository.count();
            long typeCount = strainTypeRepository.count();
            
            status.put("strainCount", strainCount);
            status.put("typeCount", typeCount);
            
            // Get sample data
            List<Map<String, Object>> strainTypes = strainTypeRepository.findAll()
                .stream()
                .map(type -> {
                    Map<String, Object> typeInfo = new HashMap<>();
                    typeInfo.put("id", type.getId());
                    typeInfo.put("name", type.getName());
                    return typeInfo;
                })
                .collect(Collectors.toList());
            
            status.put("types", strainTypes);
            
            // Get some sample strains if any exist
            if (strainCount > 0) {
                List<Map<String, Object>> sampleStrains = strainRepository.findAll()
                    .stream()
                    .limit(5)
                    .map(strain -> {
                        Map<String, Object> strainInfo = new HashMap<>();
                        strainInfo.put("id", strain.getId());
                        strainInfo.put("name", strain.getName());
                        strainInfo.put("type", strain.getType() != null ? strain.getType().getName() : null);
                        return strainInfo;
                    })
                    .collect(Collectors.toList());
                
                status.put("sampleStrains", sampleStrains);
            }
            
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            status.put("error", e.getMessage());
            return ResponseEntity.status(500).body(status);
        }
    }
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> test() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Test endpoint is working!");
        response.put("timestamp", new java.util.Date());
        return ResponseEntity.ok(response);
    }
}