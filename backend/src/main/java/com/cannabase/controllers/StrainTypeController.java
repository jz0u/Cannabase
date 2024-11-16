package com.cannabase.controllers;

import com.cannabase.dto.StrainTypeDto;
import com.cannabase.dto.StrainTypeRequestDto;
import com.cannabase.mapper.StrainTypeMapper;
import com.cannabase.services.StrainTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/strain-types")
public class StrainTypeController {
    private final StrainTypeService strainTypeService;
    private final StrainTypeMapper strainTypeMapper;

    public StrainTypeController(StrainTypeService strainTypeService, StrainTypeMapper strainTypeMapper) {
        this.strainTypeService = strainTypeService;
        this.strainTypeMapper = strainTypeMapper;
    }

    @GetMapping
    public ResponseEntity<List<StrainTypeDto>> getAllStrainTypes() {
        var strainTypes = strainTypeService.getAllStrainTypes();
        return ResponseEntity.ok(strainTypeMapper.toDto(strainTypes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StrainTypeDto> getStrainTypeById(@PathVariable Long id) {
        return strainTypeService.getStrainTypeById(id)
            .map(strainTypeMapper::toDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StrainTypeDto> createStrainType(@Valid @RequestBody StrainTypeRequestDto requestDto) {
        var strainType = strainTypeService.createStrainType(requestDto);
        return ResponseEntity.ok(strainTypeMapper.toDto(strainType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StrainTypeDto> updateStrainType(
            @PathVariable Long id, 
            @Valid @RequestBody StrainTypeRequestDto requestDto) {
        var strainType = strainTypeService.updateStrainType(id, requestDto);
        return ResponseEntity.ok(strainTypeMapper.toDto(strainType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStrainType(@PathVariable Long id) {
        strainTypeService.deleteStrainType(id);
        return ResponseEntity.noContent().build();
    }
}