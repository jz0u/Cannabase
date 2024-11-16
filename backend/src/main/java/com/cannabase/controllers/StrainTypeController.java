package com.cannabase.controllers;

import com.cannabase.models.StrainType;
import com.cannabase.services.StrainTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/strain-types")
public class StrainTypeController {
    private final StrainTypeService strainTypeService;

    @Autowired
    public StrainTypeController(StrainTypeService strainTypeService) {
        this.strainTypeService = strainTypeService;
    }

    @GetMapping
    public ResponseEntity<List<StrainType>> getAllTypes() {
        return ResponseEntity.ok(strainTypeService.getAllTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StrainType> getTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(strainTypeService.getTypeById(id));
    }

    @PostMapping
    public ResponseEntity<StrainType> createType(@RequestBody StrainType strainType) {
        return ResponseEntity.ok(strainTypeService.createType(strainType));
    }
}