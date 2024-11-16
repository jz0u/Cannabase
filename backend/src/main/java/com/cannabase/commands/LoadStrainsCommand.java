package com.cannabase.commands;

import com.cannabase.services.CsvDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class LoadStrainsCommand implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(LoadStrainsCommand.class);
    
    @Autowired
    private CsvDataLoader csvDataLoader;

    @Override
    public void run(String... args) {
        if (args.length > 0 && "load-strains".equals(args[0])) {
            logger.info("Starting strain data load process...");
            try {
                csvDataLoader.loadStrainData();
                logger.info("Strain data load completed successfully!");
            } catch (Exception e) {
                logger.error("Error loading strain data: " + e.getMessage(), e);
            }
        }
    }
}