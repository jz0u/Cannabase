package com.cannabase.controllers;

import com.cannabase.models.StrainType;
import com.cannabase.services.StrainTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/strain-types")
public class StrainTypeController {
    
    private final StrainTypeService strainTypeService;
    
    public StrainTypeController(StrainTypeService strainTypeService) {
        this.strainTypeService = strainTypeService;
    }
    
    @GetMapping
    public ResponseEntity<List<StrainType>> getAllStrainTypes() {
        return ResponseEntity.ok(strainTypeService.getAllStrainTypes());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<StrainType> getStrainTypeById(@PathVariable Long id) {
        return strainTypeService.getStrainTypeById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<StrainType> createStrainType(@RequestBody StrainType strainType) {
        return ResponseEntity.ok(strainTypeService.createStrainType(strainType));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<StrainType> updateStrainType(@PathVariable Long id, @RequestBody StrainType strainType) {
        return ResponseEntity.ok(strainTypeService.updateStrainType(id, strainType));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStrainType(@PathVariable Long id) {
        strainTypeService.deleteStrainType(id);
        return ResponseEntity.noContent().build();
    }
}