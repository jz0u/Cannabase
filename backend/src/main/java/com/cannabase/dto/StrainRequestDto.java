package com.cannabase.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class StrainRequestDto {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 255, message = "Name must be between 2 and 255 characters")
    private String name;

    @NotBlank(message = "Type is required")
    private String type;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @DecimalMin(value = "0.0", message = "THC content must be positive")
    @DecimalMax(value = "100.0", message = "THC content must be less than 100")
    private BigDecimal thcContent;

    @DecimalMin(value = "0.0", message = "CBD content must be positive")
    @DecimalMax(value = "100.0", message = "CBD content must be less than 100")
    private BigDecimal cbdContent;
}