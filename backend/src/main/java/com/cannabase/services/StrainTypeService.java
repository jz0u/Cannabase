package com.cannabase.services;

import com.cannabase.models.StrainType;
import com.cannabase.repositories.StrainTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class StrainTypeService {
    private final StrainTypeRepository strainTypeRepository;

    @Autowired
    public StrainTypeService(StrainTypeRepository strainTypeRepository) {
        this.strainTypeRepository = strainTypeRepository;
    }

    public List<StrainType> getAllTypes() {
        return strainTypeRepository.findAll();
    }

    public StrainType getTypeById(Long id) {
        return strainTypeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("StrainType not found with id: " + id));
    }

    public StrainType createType(StrainType strainType) {
        if (strainTypeRepository.existsByName(strainType.getName())) {
            throw new IllegalArgumentException("StrainType with name " + strainType.getName() + " already exists");
        }
        return strainTypeRepository.save(strainType);
    }
}