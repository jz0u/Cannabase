package com.cannabase.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StrainTypeDto {
    private Long id;
    private String name;
    private String description;
}