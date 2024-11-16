package com.cannabase.services;

import com.cannabase.dto.StrainRequestDto;
import com.cannabase.mapper.StrainMapper;
import com.cannabase.models.Strain;
import com.cannabase.models.StrainType;
import com.cannabase.repositories.StrainRepository;
import com.cannabase.repositories.StrainTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class StrainService {
    private final StrainRepository strainRepository;
    private final StrainTypeRepository strainTypeRepository;
    private final StrainMapper strainMapper;
    
    public StrainService(
        StrainRepository strainRepository, 
        StrainTypeRepository strainTypeRepository,
        StrainMapper strainMapper
    ) {
        this.strainRepository = strainRepository;
        this.strainTypeRepository = strainTypeRepository;
        this.strainMapper = strainMapper;
    }
    
    public List<Strain> getAllStrains() {
        return strainRepository.findAll();
    }
    
    public Optional<Strain> getStrainById(Long id) {
        return strainRepository.findById(id);
    }
    
    public List<Strain> searchStrainsByName(String name) {
        return strainRepository.findByNameContainingIgnoreCase(name);
    }
    
    public List<Strain> getStrainsByType(String typeName) {
        return strainRepository.findByType_NameIgnoreCase(typeName);
    }
    
    @Transactional
    public Strain createStrain(StrainRequestDto dto) {
        validateStrainName(dto.getName());
        StrainType type = findStrainType(dto.getType());
        Strain strain = strainMapper.toEntity(dto, type);
        return strainRepository.save(strain);
    }
    
    @Transactional
    public Strain updateStrain(Long id, StrainRequestDto dto) {
        Strain strain = strainRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Strain not found with id: " + id));
            
        if (!strain.getName().equalsIgnoreCase(dto.getName())) {
            validateStrainName(dto.getName());
        }
        
        StrainType type = findStrainType(dto.getType());
        strainMapper.updateEntity(strain, dto, type);
        return strainRepository.save(strain);
    }
    
    @Transactional
    public void deleteStrain(Long id) {
        if (!strainRepository.existsById(id)) {
            throw new EntityNotFoundException("Strain not found with id: " + id);
        }
        strainRepository.deleteById(id);
    }

    private void validateStrainName(String name) {
        if (strainRepository.existsByNameIgnoreCase(name)) {
            throw new IllegalArgumentException("Strain with name '" + name + "' already exists");
        }
    }

    private StrainType findStrainType(String typeName) {
        return strainTypeRepository.findByNameIgnoreCase(typeName)
            .orElseThrow(() -> new EntityNotFoundException("Strain type '" + typeName + "' not found"));
    }
}