package com.cannabase.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StrainDetailDto {
    private Long id;
    private String name;
    private String type;
    private String description;
    private BigDecimal thcContent;
    private BigDecimal cbdContent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public StrainDetailDto() {}  // Default constructor needed for serialization

    public StrainDetailDto(Long id, String name, String type, String description, 
                          BigDecimal thcContent, BigDecimal cbdContent, 
                          LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.thcContent = thcContent;
        this.cbdContent = cbdContent;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getThcContent() { return thcContent; }
    public void setThcContent(BigDecimal thcContent) { this.thcContent = thcContent; }
    public BigDecimal getCbdContent() { return cbdContent; }
    public void setCbdContent(BigDecimal cbdContent) { this.cbdContent = cbdContent; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}