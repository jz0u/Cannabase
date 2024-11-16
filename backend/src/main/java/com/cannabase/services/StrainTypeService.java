package com.cannabase.services;

import com.cannabase.dto.StrainTypeRequestDto;
import com.cannabase.models.StrainType;
import com.cannabase.repositories.StrainTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class StrainTypeService {
    
    private final StrainTypeRepository strainTypeRepository;
    
    public StrainTypeService(StrainTypeRepository strainTypeRepository) {
        this.strainTypeRepository = strainTypeRepository;
    }
    
    public List<StrainType> getAllStrainTypes() {
        return strainTypeRepository.findAll();
    }
    
    public Optional<StrainType> getStrainTypeById(Long id) {
        return strainTypeRepository.findById(id);
    }
    
    @Transactional
    public StrainType createStrainType(StrainTypeRequestDto dto) {
        if (strainTypeRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new IllegalArgumentException("Strain type '" + dto.getName() + "' already exists");
        }

        StrainType strainType = new StrainType();
        strainType.setName(dto.getName());
        strainType.setDescription(dto.getDescription());
        
        return strainTypeRepository.save(strainType);
    }
    
    @Transactional
    public StrainType updateStrainType(Long id, StrainTypeRequestDto dto) {
        StrainType strainType = strainTypeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Strain type not found with id: " + id));

        if (!strainType.getName().equalsIgnoreCase(dto.getName()) && 
            strainTypeRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new IllegalArgumentException("Strain type '" + dto.getName() + "' already exists");
        }

        strainType.setName(dto.getName());
        strainType.setDescription(dto.getDescription());
        
        return strainTypeRepository.save(strainType);
    }
    
    @Transactional
    public void deleteStrainType(Long id) {
        if (!strainTypeRepository.existsById(id)) {
            throw new EntityNotFoundException("Strain type not found with id: " + id);
        }
        strainTypeRepository.deleteById(id);
    }
}