package com.cannabase.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "strains")
public class Strain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private StrainType type;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "thc_content", precision = 5, scale = 2)
    private BigDecimal thcContent;

    @Column(name = "cbd_content", precision = 5, scale = 2)
    private BigDecimal cbdContent;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public StrainType getType() { return type; }
    public void setType(StrainType type) { this.type = type; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getThcContent() { return thcContent; }
    public void setThcContent(BigDecimal thcContent) { this.thcContent = thcContent; }
    public BigDecimal getCbdContent() { return cbdContent; }
    public void setCbdContent(BigDecimal cbdContent) { this.cbdContent = cbdContent; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}