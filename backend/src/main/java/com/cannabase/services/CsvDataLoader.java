package com.cannabase.services;

import com.cannabase.models.Strain;
import com.cannabase.models.StrainType;
import com.cannabase.repositories.StrainRepository;
import com.cannabase.repositories.StrainTypeRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Optional;

@Service
public class CsvDataLoader {
    private static final Logger logger = LoggerFactory.getLogger(CsvDataLoader.class);

    @Autowired
    private StrainRepository strainRepository;
    
    @Autowired
    private StrainTypeRepository strainTypeRepository;

    @Transactional
    public void loadStrainData() {
        try {
            // Clear existing data
            clearExistingData();
            // Initialize strain types
            initializeStrainTypes();
            
            // Read CSV file
            ClassPathResource resource = new ClassPathResource("strains.csv");
            CSVReader reader = new CSVReaderBuilder(new InputStreamReader(resource.getInputStream()))
                .withSkipLines(1) // Skip header row
                .build();

            String[] line;
            int processed = 0;
            int saved = 0;

            while ((line = reader.readNext()) != null) {
                processed++;
                try {
                    if (processStrain(line)) {
                        saved++;
                    }
                } catch (Exception e) {
                    logger.error("Error processing line: " + Arrays.toString(line), e);
                }

                if (processed % 100 == 0) {
                    logger.info("Processed {} strains, saved {}", processed, saved);
                }
            }

            logger.info("Completed strain import. Processed: {}, Saved: {}", processed, saved);
            
        } catch (Exception e) {
            logger.error("Error loading strain data", e);
            throw new RuntimeException("Failed to load strain data", e);
        }

    }

    private void clearExistingData() {
        logger.info("Clearing existing data...");
        try {
            long strainCount = strainRepository.count();
            long typeCount = strainTypeRepository.count();
            logger.info("Current counts - Strains: {}, Types: {}", strainCount, typeCount);
            
            strainRepository.deleteAll();
            strainTypeRepository.deleteAll();
            
            logger.info("Cleared all existing data");
        } catch (Exception e) {
            logger.error("Error clearing data", e);
        }
    }

    private void initializeStrainTypes() {
        logger.info("Initializing strain types...");
        Arrays.asList(
            new String[]{"sativa", "Cannabis sativa is known for its energizing effects"},
            new String[]{"indica", "Cannabis indica is known for its relaxing effects"},
            new String[]{"hybrid", "A mix of Sativa and Indica"}
        ).forEach(type -> createStrainType(type[0], type[1]));
        
        logger.info("Strain types initialized. Count: {}", strainTypeRepository.count());
    }

    private void createStrainType(String name, String description) {
        try {
            if (!strainTypeRepository.existsByNameIgnoreCase(name)) {
                StrainType type = new StrainType();
                type.setName(name);
                type.setDescription(description);
                strainTypeRepository.save(type);
                logger.info("Created strain type: {}", name);
            } else {
                logger.info("Strain type already exists: {}", name);
            }
        } catch (Exception e) {
            logger.error("Error creating strain type: " + name, e);
        }
    }

    private boolean processStrain(String[] line) {
        if (line.length < 6) {
            logger.warn("Invalid line format: {}", Arrays.toString(line));
            return false;
        }

        String name = line[0].replace("-", " ");
        String type = line[1].toLowerCase();
        String description = line[5];

        logger.info("Processing strain - Name: {}, Type: {}", name, type);

        if (name.isEmpty()) {
            logger.warn("Empty strain name, skipping");
            return false;
        }

        if (strainRepository.existsByNameIgnoreCase(name)) {
            logger.info("Strain already exists: {}", name);
            return false;
        }

        try {
            Strain strain = new Strain();
            strain.setName(name);
            strain.setDescription(description);

            // Set strain type
            Optional<StrainType> strainType = strainTypeRepository.findByNameIgnoreCase(type);
            if (strainType.isPresent()) {
                strain.setType(strainType.get());
                logger.info("Found strain type: {} for strain: {}", type, name);
            } else {
                logger.warn("Strain type not found: {} for strain: {}", type, name);
                return false;
            }

            Strain savedStrain = strainRepository.save(strain);
            logger.info("Successfully saved strain: {} with ID: {}", name, savedStrain.getId());
            return true;
        } catch (Exception e) {
            logger.error("Error saving strain: " + name, e);
            return false;
        }
    }
}