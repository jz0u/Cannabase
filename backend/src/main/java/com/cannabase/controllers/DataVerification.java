// src/main/java/com/cannabase/controllers/DataVerification.java
package com.cannabase.controllers;

import com.cannabase.repositories.StrainRepository;
import com.cannabase.repositories.StrainTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/verify")
public class DataVerification {

    @Autowired
    private StrainRepository strainRepository;

    @Autowired
    private StrainTypeRepository strainTypeRepository;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/database")
    public ResponseEntity<Map<String, Object>> verifyDatabase() {
        Map<String, Object> status = new HashMap<>();
        
        // Count records
        long strainCount = strainRepository.count();
        long typeCount = strainTypeRepository.count();
        
        status.put("strainCount", strainCount);
        status.put("typeCount", typeCount);
        
        // Test Kushy API connection
        try {
            String response = restTemplate.getForObject(
                "http://api.kushy.net/api/1.1/tables/strains/rows", 
                String.class
            );
            status.put("kushyApiConnection", "Success");
            status.put("kushyApiResponse", response != null && !response.isEmpty());
        } catch (Exception e) {
            status.put("kushyApiConnection", "Failed");
            status.put("kushyApiError", e.getMessage());
        }

        return ResponseEntity.ok(status);
    }
}