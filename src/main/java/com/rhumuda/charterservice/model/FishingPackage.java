package com.rhumuda.charterservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("FISHING")
public class FishingPackage extends Package {
    @Column(name = "price_min")
    private Double priceMin;
    
    @Column(name = "price_max")
    private Double priceMax;
    
    @Column(name = "distance_range")
    private String distance;
    
    @ElementCollection
    @CollectionTable(name = "package_techniques", 
        joinColumns = @JoinColumn(name = "package_id"))
    @Column(name = "technique_name")
    private List<String> techniques;
} 