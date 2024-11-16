package com.cannabase.services;

import com.cannabase.models.Strain;
import com.cannabase.repositories.StrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class StrainService {
    private final StrainRepository strainRepository;

    @Autowired
    public StrainService(StrainRepository strainRepository) {
        this.strainRepository = strainRepository;
    }

    public Page<Strain> getAllStrains(Pageable pageable) {
        return strainRepository.findAll(pageable);
    }

    public Strain getStrainById(Long id) {
        return strainRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Strain not found with id: " + id));
    }

    public List<Strain> searchStrains(String searchTerm) {
        return strainRepository.searchByName(searchTerm);
    }

    public Strain createStrain(Strain strain) {
        if (strainRepository.existsByName(strain.getName())) {
            throw new IllegalArgumentException("Strain with name " + strain.getName() + " already exists");
        }
        return strainRepository.save(strain);
    }
}