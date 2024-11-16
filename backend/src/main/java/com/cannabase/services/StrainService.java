package com.cannabase.services;

import com.cannabase.models.Strain;
import com.cannabase.models.StrainType;
import com.cannabase.repositories.StrainRepository;
import com.cannabase.repositories.StrainTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class StrainService {
    
    private final StrainRepository strainRepository;
    private final StrainTypeRepository strainTypeRepository;
    
    public StrainService(StrainRepository strainRepository, StrainTypeRepository strainTypeRepository) {
        this.strainRepository = strainRepository;
        this.strainTypeRepository = strainTypeRepository;
    }
    
    public List<Strain> getAllStrains() {
        return strainRepository.findAll();
    }
    
    public Optional<Strain> getStrainById(Long id) {
        return strainRepository.findById(id);
    }
    
    public List<Strain> searchStrainsByName(String name) {
        return strainRepository.searchByName(name);
    }
    
    @Transactional
    public Strain createStrain(Strain strain) {
        if (strainRepository.existsByNameIgnoreCase(strain.getName())) {
            throw new IllegalArgumentException("Strain with this name already exists");
        }
        return strainRepository.save(strain);
    }
    
    @Transactional
    public Strain updateStrain(Long id, Strain updatedStrain) {
        return strainRepository.findById(id)
            .map(strain -> {
                strain.setName(updatedStrain.getName());
                strain.setDescription(updatedStrain.getDescription());
                strain.setType(updatedStrain.getType());
                strain.setThcContent(updatedStrain.getThcContent());
                strain.setCbdContent(updatedStrain.getCbdContent());
                return strainRepository.save(strain);
            })
            .orElseThrow(() -> new IllegalArgumentException("Strain not found with id: " + id));
    }
    
    public void deleteStrain(Long id) {
        strainRepository.deleteById(id);
    }
    
    public List<Strain> getStrainsByType(String typeName) {
        return strainRepository.findByTypeNameIgnoreCase(typeName);
    }
}