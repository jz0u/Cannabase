package com.cannabase.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// Response DTO for Strain list/search endpoints
public class StrainListDto {
    private Long id;
    private String name;
    private String type;
    private BigDecimal thcContent;
    private BigDecimal cbdContent;

    // Constructor
    public StrainListDto(Long id, String name, String type, BigDecimal thcContent, BigDecimal cbdContent) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.thcContent = thcContent;
        this.cbdContent = cbdContent;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public BigDecimal getThcContent() { return thcContent; }
    public void setThcContent(BigDecimal thcContent) { this.thcContent = thcContent; }
    public BigDecimal getCbdContent() { return cbdContent; }
    public void setCbdContent(BigDecimal cbdContent) { this.cbdContent = cbdContent; }
}


