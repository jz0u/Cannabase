package com.cannabase.mapper;

import com.cannabase.dto.StrainListDto;
import com.cannabase.dto.StrainDetailDto;
import com.cannabase.dto.StrainRequestDto;
import com.cannabase.models.Strain;
import com.cannabase.models.StrainType;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StrainMapper {
    
    public StrainListDto toListDto(Strain strain) {
        return new StrainListDto(
            strain.getId(),
            strain.getName(),
            strain.getType() != null ? strain.getType().getName() : null,
            strain.getThcContent(),
            strain.getCbdContent()
        );
    }

    public List<StrainListDto> toListDto(List<Strain> strains) {
        return strains.stream()
            .map(this::toListDto)
            .collect(Collectors.toList());
    }

    public StrainDetailDto toDetailDto(Strain strain) {
        return new StrainDetailDto(
            strain.getId(),
            strain.getName(),
            strain.getType() != null ? strain.getType().getName() : null,
            strain.getDescription(),
            strain.getThcContent(),
            strain.getCbdContent(),
            strain.getCreatedAt(),
            strain.getUpdatedAt()
        );
    }

    public Strain toEntity(StrainRequestDto dto, StrainType type) {
        Strain strain = new Strain();
        updateEntity(strain, dto, type);
        return strain;
    }

    public void updateEntity(Strain strain, StrainRequestDto dto, StrainType type) {
        strain.setName(dto.getName());
        strain.setType(type);
        strain.setDescription(dto.getDescription());
        strain.setThcContent(dto.getThcContent());
        strain.setCbdContent(dto.getCbdContent());
    }
}