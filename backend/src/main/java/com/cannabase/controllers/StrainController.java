package com.cannabase.controllers;

import com.cannabase.models.Strain;
import com.cannabase.services.StrainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/strains")
public class StrainController {
    
    private final StrainService strainService;
    
    public StrainController(StrainService strainService) {
        this.strainService = strainService;
    }
    
    @GetMapping
    public ResponseEntity<List<Strain>> getAllStrains() {
        return ResponseEntity.ok(strainService.getAllStrains());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Strain> getStrainById(@PathVariable Long id) {
        return strainService.getStrainById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Strain>> searchStrains(@RequestParam String name) {
        return ResponseEntity.ok(strainService.searchStrainsByName(name));
    }
    
    @PostMapping
    public ResponseEntity<Strain> createStrain(@RequestBody Strain strain) {
        return ResponseEntity.ok(strainService.createStrain(strain));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Strain> updateStrain(@PathVariable Long id, @RequestBody Strain strain) {
        return ResponseEntity.ok(strainService.updateStrain(id, strain));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStrain(@PathVariable Long id) {
        strainService.deleteStrain(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/type/{typeName}")
    public ResponseEntity<List<Strain>> getStrainsByType(@PathVariable String typeName) {
        return ResponseEntity.ok(strainService.getStrainsByType(typeName));
    }
}