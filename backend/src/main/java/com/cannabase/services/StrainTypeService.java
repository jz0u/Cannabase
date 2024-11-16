package com.cannabase.services;

import com.cannabase.models.StrainType;
import com.cannabase.repositories.StrainTypeRepository;
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
    
    @Transactional
    public StrainType createStrainType(StrainType strainType) {
        if (strainTypeRepository.existsByNameIgnoreCase(strainType.getName())) {
            throw new IllegalArgumentException("Strain type already exists");
        }
        return strainTypeRepository.save(strainType);
    }
    
    public Optional<StrainType> getStrainTypeById(Long id) {
        return strainTypeRepository.findById(id);
    }
    
    public Optional<StrainType> getStrainTypeByName(String name) {
        return strainTypeRepository.findByNameIgnoreCase(name);
    }
    
    @Transactional
    public StrainType updateStrainType(Long id, StrainType updatedType) {
        return strainTypeRepository.findById(id)
            .map(type -> {
                type.setName(updatedType.getName());
                type.setDescription(updatedType.getDescription());
                return strainTypeRepository.save(type);
            })
            .orElseThrow(() -> new IllegalArgumentException("Strain type not found with id: " + id));
    }
    
    public void deleteStrainType(Long id) {
        strainTypeRepository.deleteById(id);
    }
}