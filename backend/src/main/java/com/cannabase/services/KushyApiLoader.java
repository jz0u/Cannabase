package com.cannabase.services;

import com.cannabase.models.Strain;
import com.cannabase.models.StrainType;
import com.cannabase.repositories.StrainRepository;
import com.cannabase.repositories.StrainTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class KushyApiLoader {
    private static final Logger logger = LoggerFactory.getLogger(KushyApiLoader.class);
    private static final String API_BASE_URL = "http://api.kushy.net/api/1.1/tables";
    
    private final RestTemplate restTemplate;
    private final StrainRepository strainRepository;
    private final StrainTypeRepository strainTypeRepository;
    private final ObjectMapper objectMapper;

    public KushyApiLoader(StrainRepository strainRepository, 
                         StrainTypeRepository strainTypeRepository) {
        this.restTemplate = new RestTemplate();
        this.strainRepository = strainRepository;
        this.strainTypeRepository = strainTypeRepository;
        this.objectMapper = new ObjectMapper();
    }

    public void loadStrains() {
        try {
            logger.info("Starting strain data import from Kushy API");
            
            // Get strains data
            ResponseEntity<String> response = restTemplate.getForEntity(
                API_BASE_URL + "/strains/rows",
                String.class
            );

            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode data = root.path("data");
            
            // Initialize strain types
            initializeStrainTypes();
            
            int processed = 0;
            int total = data.size();
            List<String> errors = new ArrayList<>();

            for (JsonNode strainData : data) {
                try {
                    processStrain(strainData);
                    processed++;
                    
                    if (processed % 10 == 0) {
                        logger.info("Processed {}/{} strains", processed, total);
                    }
                } catch (Exception e) {
                    String name = strainData.path("name").asText("Unknown");
                    String error = String.format("Error processing strain %s: %s", name, e.getMessage());
                    errors.add(error);
                    logger.error(error);
                }
            }

            // Log summary
            logger.info("Import completed. Processed {}/{} strains", processed, total);
            if (!errors.isEmpty()) {
                logger.warn("Encountered {} errors during import:", errors.size());
                errors.forEach(logger::warn);
            }

        } catch (Exception e) {
            logger.error("Error loading strains: {}", e.getMessage());
            throw new RuntimeException("Failed to load strains", e);
        }
    }

    private void processStrain(JsonNode strainData) {
        String name = strainData.path("name").asText();
        
        // Skip if strain already exists
        if (strainRepository.findByNameIgnoreCase(name).isPresent()) {
            logger.debug("Strain already exists: {}", name);
            return;
        }

        Strain strain = new Strain();
        strain.setName(name);
        
        // Build rich description
        StringBuilder description = new StringBuilder();
        description.append(strainData.path("description").asText(""));
        
        String breeder = strainData.path("breeder").asText();
        if (breeder != null && !breeder.isEmpty()) {
            description.append("\nBreeder: ").append(breeder);
        }
        
        String effects = strainData.path("effects").asText();
        if (effects != null && !effects.isEmpty()) {
            description.append("\nEffects: ").append(effects);
        }
        
        String ailment = strainData.path("ailment").asText();
        if (ailment != null && !ailment.isEmpty()) {
            description.append("\nMedical Uses: ").append(ailment);
        }
        
        String flavor = strainData.path("flavor").asText();
        if (flavor != null && !flavor.isEmpty()) {
            description.append("\nFlavor: ").append(flavor);
        }
        
        String terpenes = strainData.path("terpenes").asText();
        if (terpenes != null && !terpenes.isEmpty()) {
            description.append("\nDominant Terpenes: ").append(terpenes);
        }

        strain.setDescription(description.toString());
        
        // Set type
        String typeStr = strainData.path("type").asText("hybrid").toLowerCase();
        StrainType type = strainTypeRepository.findByNameIgnoreCase(normalizeType(typeStr))
            .orElseGet(() -> strainTypeRepository.findByNameIgnoreCase("hybrid").orElseThrow());
        strain.setType(type);
        
        // Set cannabinoid content
        // Convert from mg/g to percentage (divide by 10)
        strain.setThcContent(extractCannabinoidContent(strainData, "thc"));
        strain.setCbdContent(extractCannabinoidContent(strainData, "cbd"));

        strainRepository.save(strain);
        logger.debug("Saved strain: {}", name);
    }

    private void initializeStrainTypes() {
        createStrainType("indica", "Known for relaxing and sedating effects, typically best for nighttime use");
        createStrainType("sativa", "Known for energizing and uplifting effects, typically best for daytime use");
        createStrainType("hybrid", "A balanced combination of Indica and Sativa properties");
    }

    private void createStrainType(String name, String description) {
        if (!strainTypeRepository.findByNameIgnoreCase(name).isPresent()) {
            StrainType type = new StrainType();
            type.setName(name);
            type.setDescription(description);
            strainTypeRepository.save(type);
            logger.info("Created strain type: {}", name);
        }
    }

    private String normalizeType(String type) {
        if (type == null || type.trim().isEmpty()) return "hybrid";
        type = type.toLowerCase().trim();
        if (type.contains("indica")) return "indica";
        if (type.contains("sativa")) return "sativa";
        return "hybrid";
    }

    private BigDecimal extractCannabinoidContent(JsonNode strainData, String cannabinoid) {
        try {
            int content = strainData.path(cannabinoid).asInt(0);
            // Convert from mg/g to percentage
            return new BigDecimal(content).divide(new BigDecimal("10"));
        } catch (Exception e) {
            logger.warn("Could not extract {} content", cannabinoid);
            return cannabinoid.equals("thc") ? new BigDecimal("18.00") : new BigDecimal("0.50");
        }
    }
}