package com.cannabase.services;

import com.cannabase.exceptions.ValidationException;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class ValidationService {
    /**
     * Validates strain-related data according to business rules
     * @param name Strain name
     * @param thcContent THC percentage
     * @param cbdContent CBD percentage
     * @throws ValidationException if validation fails
     */
    public void validateStrainData(String name, BigDecimal thcContent, BigDecimal cbdContent) {
        StringBuilder errors = new StringBuilder();

        // Validate name
        if (name != null && (name.length() < 2 || name.length() > 255)) {
            errors.append("Strain name must be between 2 and 255 characters. ");
        }

        // Validate THC content
        if (thcContent != null) {
            if (thcContent.compareTo(BigDecimal.ZERO) < 0 || thcContent.compareTo(new BigDecimal("100")) > 0) {
                errors.append("THC content must be between 0 and 100%. ");
            }
        }

        // Validate CBD content
        if (cbdContent != null) {
            if (cbdContent.compareTo(BigDecimal.ZERO) < 0 || cbdContent.compareTo(new BigDecimal("100")) > 0) {
                errors.append("CBD content must be between 0 and 100%. ");
            }
        }

        if (errors.length() > 0) {
            throw new ValidationException(errors.toString().trim());
        }
    }
}