package com.cannabase.services;

import com.cannabase.dto.StrainRequestDto;
import com.cannabase.models.Strain;
import com.cannabase.models.StrainType;
import com.cannabase.repositories.StrainRepository;
import com.cannabase.repositories.StrainTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StrainServiceTest {

    @Mock
    private StrainRepository strainRepository;
    
    @Mock
    private StrainTypeRepository strainTypeRepository;
    
    @Mock
    private ValidationService validationService;
    
    @Mock
    private AuditService auditService;

    private StrainService strainService;
    private StrainType sampleType;

    @BeforeEach
    void setUp() {
        sampleType = new StrainType();
        sampleType.setId(1L);
        sampleType.setName("sativa");
        
        strainService = new StrainService(
            strainRepository,
            strainTypeRepository,
            new StrainMapper(),
            validationService,
            auditService
        );
    }

    @Test
    void createStrain_ValidData_Success() {
        // Arrange
        StrainRequestDto dto = new StrainRequestDto();
        dto.setName("Test Strain");
        dto.setType("sativa");
        dto.setThcContent(new BigDecimal("20.0"));
        dto.setCbdContent(new BigDecimal("1.0"));

        when(strainTypeRepository.findByNameIgnoreCase("sativa"))
            .thenReturn(Optional.of(sampleType));
        when(strainRepository.existsByNameIgnoreCase("Test Strain"))
            .thenReturn(false);
        when(strainRepository.save(any(Strain.class)))
            .thenAnswer(i -> {
                Strain s = i.getArgument(0);
                s.setId(1L);
                return s;
            });

        // Act
        Strain result = strainService.createStrain(dto);

        // Assert
        assertNotNull(result);
        assertEquals("Test Strain", result.getName());
        assertEquals(sampleType, result.getType());
        assertEquals(new BigDecimal("20.0"), result.getThcContent());
    }

    @Test
    void createStrain_DuplicateName_ThrowsException() {
        // Arrange
        StrainRequestDto dto = new StrainRequestDto();
        dto.setName("Existing Strain");
        dto.setType("sativa");

        when(strainRepository.existsByNameIgnoreCase("Existing Strain"))
            .thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
            () -> strainService.createStrain(dto));
    }

    @Test
    void createStrain_InvalidType_ThrowsException() {
        // Arrange
        StrainRequestDto dto = new StrainRequestDto();
        dto.setName("Test Strain");
        dto.setType("invalid_type");

        when(strainTypeRepository.findByNameIgnoreCase("invalid_type"))
            .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class,
            () -> strainService.createStrain(dto));
    }
}