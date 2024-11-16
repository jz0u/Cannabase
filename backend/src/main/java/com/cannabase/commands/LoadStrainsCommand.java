// src/main/java/com/cannabase/commands/LoadStrainsCommand.java
package com.cannabase.commands;

import com.cannabase.services.KushyApiLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class LoadStrainsCommand implements CommandLineRunner {
    @Autowired
    private CsvDataLoader csvDataLoader;
    
    @Override
    public void run(String... args) {
        if (args.length > 0 && args[0].equals("load-strains")) {
            csvDataLoader.loadStrainData();
        }
    }
}