package com.cannabase.services;

import com.cannabase.dto.StrainCsvDto;
import com.cannabase.models.Strain;
import com.cannabase.models.StrainType;
import com.cannabase.repositories.StrainRepository;
import com.cannabase.repositories.StrainTypeRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStreamReader;
import java.util.Arrays;

@Service
public class CsvDataLoader {
    private static final Logger logger = LoggerFactory.getLogger(CsvDataLoader.class);

    @Autowired
    private StrainRepository strainRepository;
    
    @Autowired
    private StrainTypeRepository strainTypeRepository;
    
    @Value("classpath:strains.csv")
    private Resource csvResource;

    @Transactional
    public void loadStrainData() {
        try {
            // Initialize CSV reader
            InputStreamReader reader = new InputStreamReader(csvResource.getInputStream());
            CsvToBean<StrainCsvDto> csvReader = new CsvToBeanBuilder<StrainCsvDto>(reader)
                .withType(StrainCsvDto.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

            // Initialize strain types
            initializeStrainTypes();
            
            // Process each row
            int processed = 0;
            int saved = 0;
            
            for (StrainCsvDto csvStrain : csvReader) {
                try {
                    processed++;
                    if (processStrain(csvStrain)) {
                        saved++;
                    }
                    
                    if (processed % 100 == 0) {
                        logger.info("Processed {} strains, saved {}", processed, saved);
                    }
                } catch (Exception e) {
                    logger.error("Error processing strain: " + csvStrain.getName(), e);
                }
            }
            
            logger.info("Completed strain import. Processed: {}, Saved: {}", processed, saved);
            
        } catch (Exception e) {
            logger.error("Error loading CSV data", e);
            throw new RuntimeException("Failed to load strain data", e);
        }
    }

    private void initializeStrainTypes() {
        Arrays.asList(
            new String[]{"sativa", "Cannabis sativa is known for its energizing effects"},
            new String[]{"indica", "Cannabis indica is known for its relaxing effects"},
            new String[]{"hybrid", "A mix of Sativa and Indica"}
        ).forEach(type -> createStrainType(type[0], type[1]));
    }

    private void createStrainType(String name, String description) {
        if (!strainTypeRepository.existsByNameIgnoreCase(name)) {
            StrainType type = new StrainType();
            type.setName(name);
            type.setDescription(description);
            strainTypeRepository.save(type);
            logger.info("Created strain type: {}", name);
        }
    }

    private boolean processStrain(StrainCsvDto csvStrain) {
        if (csvStrain.getName() == null || csvStrain.getName().isEmpty()) {
            return false;
        }

        String strainName = csvStrain.getName().replace("-", " ");
        
        if (strainRepository.existsByNameIgnoreCase(strainName)) {
            logger.debug("Strain already exists: {}", strainName);
            return false;
        }

        try {
            Strain strain = new Strain();
            strain.setName(strainName);
            strain.setDescription(csvStrain.getDescription());
            
            // Set strain type
            String typeName = csvStrain.getType().toLowerCase();
            strainTypeRepository.findByNameIgnoreCase(typeName)
                .ifPresent(strain::setType);

            // Could add THC/CBD content if available in your CSV
            // Could add effects and flavors to separate tables if needed

            strainRepository.save(strain);
            logger.debug("Saved strain: {}", strainName);
            return true;
        } catch (Exception e) {
            logger.error("Error saving strain: {}", strainName, e);
            return false;
        }
    }
}