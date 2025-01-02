package com.rhumuda.charterservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "packages")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "package_type")
public abstract class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String packageId;
    private String title;
    private String description;
    private String duration;
    private int capacity;
    
    @ElementCollection
    @CollectionTable(name = "package_services", 
        joinColumns = @JoinColumn(name = "package_id", referencedColumnName = "id"))
    @Column(name = "service_name")
    private List<String> services;
} 