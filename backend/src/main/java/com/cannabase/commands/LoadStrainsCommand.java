package com.cannabase.commands;

import com.cannabase.services.KushyApiLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LoadStrainsCommand implements CommandLineRunner {
    
    private final KushyApiLoader kushyApiLoader;
    
    public LoadStrainsCommand(KushyApiLoader kushyApiLoader) {
        this.kushyApiLoader = kushyApiLoader;
    }
    
    @Override
    public void run(String... args) {
        if (args.length > 0 && "load-strains".equals(args[0])) {
            kushyApiLoader.loadStrains();
        }
    }
}