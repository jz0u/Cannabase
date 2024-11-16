package com.cannabase.controllers;

import com.cannabase.models.Strain;
import com.cannabase.services.StrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/strains")
public class StrainController {
    private final StrainService strainService;

    @Autowired
    public StrainController(StrainService strainService) {
        this.strainService = strainService;
    }

    @GetMapping
    public ResponseEntity<Page<Strain>> getAllStrains(Pageable pageable) {
        return ResponseEntity.ok(strainService.getAllStrains(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Strain> getStrainById(@PathVariable Long id) {
        return ResponseEntity.ok(strainService.getStrainById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Strain>> searchStrains(@RequestParam String query) {
        return ResponseEntity.ok(strainService.searchStrains(query));
    }

    @PostMapping
    public ResponseEntity<Strain> createStrain(@RequestBody Strain strain) {
        return ResponseEntity.ok(strainService.createStrain(strain));
    }
}
