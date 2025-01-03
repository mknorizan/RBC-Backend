package com.rhumuda.charterservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "packages")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "package_type")
@Getter
@Setter
public abstract class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(columnDefinition = "text")
    private String description;
    
    @Column(nullable = false)
    private Double price;
    
    @Column(name = "package_type", insertable = false, updatable = false)
    private String packageType;
    
    @ElementCollection
    @CollectionTable(name = "package_services", 
        joinColumns = @JoinColumn(name = "package_id"))
    @Column(name = "service_name")
    private List<String> services;
    
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
} 