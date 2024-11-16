package com.cannabase.mapper;

import com.cannabase.dto.StrainTypeDto;
import com.cannabase.dto.StrainTypeRequestDto;
import com.cannabase.models.StrainType;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StrainTypeMapper {
    
    public StrainTypeDto toDto(StrainType strainType) {
        return new StrainTypeDto(
            strainType.getId(),
            strainType.getName(),
            strainType.getDescription()
        );
    }

    public List<StrainTypeDto> toDto(List<StrainType> strainTypes) {
        return strainTypes.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    public StrainType toEntity(StrainTypeRequestDto dto) {
        StrainType strainType = new StrainType();
        strainType.setName(dto.getName());
        strainType.setDescription(dto.getDescription());
        return strainType;
    }

    public void updateEntity(StrainType strainType, StrainTypeRequestDto dto) {
        strainType.setName(dto.getName());
        strainType.setDescription(dto.getDescription());
    }
}