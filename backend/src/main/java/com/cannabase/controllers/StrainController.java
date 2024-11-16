package com.cannabase.controllers;

import com.cannabase.dto.StrainDetailDto;
import com.cannabase.dto.StrainListDto;
import com.cannabase.dto.StrainRequestDto;
import com.cannabase.mapper.StrainMapper;
import com.cannabase.services.StrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/strains")
public class StrainController {
    private final StrainService strainService;
    private final StrainMapper strainMapper;
    
    @Autowired
    public StrainController(StrainService strainService, StrainMapper strainMapper) {
        this.strainService = strainService;
        this.strainMapper = strainMapper;
    }
    
    @GetMapping
    public ResponseEntity<List<StrainListDto>> getAllStrains() {
        return ResponseEntity.ok(strainMapper.toListDto(strainService.getAllStrains()));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<StrainDetailDto> getStrainById(@PathVariable Long id) {
        return strainService.getStrainById(id)
            .map(strain -> ResponseEntity.ok(strainMapper.toDetailDto(strain)))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<StrainListDto>> searchStrains(@RequestParam String name) {
        return ResponseEntity.ok(strainMapper.toListDto(strainService.searchStrainsByName(name)));
    }
    
    @PostMapping
    public ResponseEntity<StrainDetailDto> createStrain(@Valid @RequestBody StrainRequestDto requestDto) {
        return ResponseEntity.ok(strainMapper.toDetailDto(strainService.createStrain(requestDto)));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<StrainDetailDto> updateStrain(
            @PathVariable Long id, 
            @Valid @RequestBody StrainRequestDto requestDto) {
        return ResponseEntity.ok(strainMapper.toDetailDto(strainService.updateStrain(id, requestDto)));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStrain(@PathVariable Long id) {
        strainService.deleteStrain(id);
        return ResponseEntity.noContent().build();
    }
}