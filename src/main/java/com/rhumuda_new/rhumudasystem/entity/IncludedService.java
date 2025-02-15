package com.rhumuda_new.rhumudasystem.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "included_services")
public class IncludedService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_name", nullable = false)
    private String name;

    private String description;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false)
    private Package packageEntity;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Package getPackageEntity() { return packageEntity; }
    public void setPackageEntity(Package packageEntity) { this.packageEntity = packageEntity; }
} 