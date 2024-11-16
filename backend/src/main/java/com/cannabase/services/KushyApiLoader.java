package com.cannabase.services;

import com.cannabase.models.Strain;
import com.cannabase.models.StrainType;
import com.cannabase.repositories.StrainRepository;
import com.cannabase.repositories.StrainTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;

@Service
public class KushyApiLoader {
    private static final Logger logger = LoggerFactory.getLogger(KushyApiLoader.class);
    private static final String KUSHY_API_URL = "http://api.kushy.net/api/1.1/tables/strains/rows";

    @Autowired
    private StrainRepository strainRepository;

    @Autowired
    private StrainTypeRepository strainTypeRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final Map<String, StrainType> strainTypeCache = new HashMap<>();

    @Transactional
    public void loadStrainData() {
        try {
            logger.info("Starting strain data load from Kushy API");
            
            // Initialize basic strain types
            initializeStrainTypes();
            
            // Set up headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            // Fetch data from Kushy API
            logger.info("Fetching data from Kushy API: {}", KUSHY_API_URL);
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(KUSHY_API_URL, String.class);
            String response = responseEntity.getBody();
            
            if (response == null || response.isEmpty()) {
                logger.error("Received empty response from Kushy API");
                return;
            }
            
            logger.info("Received response from Kushy API: {} characters", response.length());
            logger.debug("Raw API response: {}", response);
            
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            JsonNode rows = root.get("data");

            if (rows == null || !rows.isArray()) {
                logger.error("No data array found in API response. Response structure: {}", root.toString());
                return;
            }
            
            logger.info("Found {} strains in API response", rows.size());
            int processed = 0;
            int saved = 0;

            for (JsonNode row : rows) {
                try {
                    processed++;
                    if (processStrainData(row)) {
                        saved++;
                    }
                    if (processed % 10 == 0) {
                        logger.info("Processed {} strains, saved {}", processed, saved);
                    }
                } catch (Exception e) {
                    logger.error("Error processing strain: " + row.toString(), e);
                }
            }

            logger.info("Completed strain data load. Processed: {}, Saved: {}", processed, saved);
            logger.info("Final counts - Strains: {}, Types: {}", 
                strainRepository.count(), strainTypeRepository.count());
        } catch (Exception e) {
            logger.error("Error loading strain data", e);
            throw new RuntimeException("Failed to load strain data", e);
        }
    }

    private void initializeStrainTypes() {
        logger.info("Initializing strain types");
        createStrainType("Sativa", "Cannabis sativa is known for its energizing effects");
        createStrainType("Indica", "Cannabis indica is known for its relaxing effects");
        createStrainType("Hybrid", "A mix of Sativa and Indica");
        logger.info("Strain types initialized. Count: {}", strainTypeRepository.count());
    }

    private void createStrainType(String name, String description) {
        try {
            StrainType type = strainTypeRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> {
                    StrainType newType = new StrainType();
                    newType.setName(name);
                    newType.setDescription(description);
                    return strainTypeRepository.save(newType);
                });
            strainTypeCache.put(name.toLowerCase(), type);
            logger.info("Strain type processed: {}", name);
        } catch (Exception e) {
            logger.error("Error creating strain type: " + name, e);
        }
    }

    private boolean processStrainData(JsonNode strainData) {
        String name = strainData.path("name").asText();
        if (name == null || name.isEmpty()) {
            logger.warn("Skipping strain with empty name");
            return false;
        }

        logger.debug("Processing strain: {}", name);

        // Skip if strain already exists
        if (strainRepository.existsByNameIgnoreCase(name)) {
            logger.info("Strain already exists: {}", name);
            return false;
        }

        try {
            Strain strain = new Strain();
            strain.setName(name);
            
            String description = strainData.path("description").asText();
            strain.setDescription(description);
            logger.debug("Description set: {}", description.substring(0, Math.min(50, description.length())));
            
            // Parse THC and CBD content
            try {
                String thcStr = strainData.path("thc").asText();
                if (thcStr != null && !thcStr.isEmpty()) {
                    String cleanThc = thcStr.replaceAll("[^0-9.]", "");
                    if (!cleanThc.isEmpty()) {
                        strain.setThcContent(new BigDecimal(cleanThc));
                        logger.debug("THC content set: {}", cleanThc);
                    }
                }
            } catch (Exception e) {
                logger.warn("Error parsing THC content for strain: {}", name, e);
            }

            try {
                String cbdStr = strainData.path("cbd").asText();
                if (cbdStr != null && !cbdStr.isEmpty()) {
                    String cleanCbd = cbdStr.replaceAll("[^0-9.]", "");
                    if (!cleanCbd.isEmpty()) {
                        strain.setCbdContent(new BigDecimal(cleanCbd));
                        logger.debug("CBD content set: {}", cleanCbd);
                    }
                }
            } catch (Exception e) {
                logger.warn("Error parsing CBD content for strain: {}", name, e);
            }

            // Set strain type
            String typeStr = strainData.path("type").asText().toLowerCase();
            logger.debug("Raw type string: {}", typeStr);
            StrainType type = determineStrainType(typeStr);
            if (type != null) {
                strain.setType(type);
                logger.debug("Type set: {}", type.getName());
            } else {
                logger.warn("Could not determine type for strain: {}", name);
                return false;
            }

            Strain savedStrain = strainRepository.save(strain);
            logger.info("Saved strain: {} with ID: {}", name, savedStrain.getId());
            return true;
        } catch (Exception e) {
            logger.error("Error saving strain: {}", name, e);
            return false;
        }
    }

    private StrainType determineStrainType(String typeStr) {
        if (typeStr == null || typeStr.isEmpty()) {
            return strainTypeCache.get("hybrid"); // default to hybrid if type is unknown
        }
        
        typeStr = typeStr.toLowerCase();
        if (typeStr.contains("sativa")) {
            return strainTypeCache.get("sativa");
        } else if (typeStr.contains("indica")) {
            return strainTypeCache.get("indica");
        } else {
            return strainTypeCache.get("hybrid");
        }
    }
}