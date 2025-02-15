package com.rhumuda_new.rhumudasystem.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "packages")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "base_price")
    private BigDecimal basePrice;

    @Column(name = "max_capacity")
    private Integer maxCapacity;

    private Integer duration;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "is_private")
    private Boolean isPrivate;

    @Column(name = "distance_min_km")
    private Integer distanceMinKm;

    @Column(name = "distance_max_km")
    private Integer distanceMaxKm;

    @Column(name = "fishing_type")
    private String fishingType;

    @Column(name = "image_url")
    private String imageUrl;

    @JsonManagedReference
    @OneToMany(mappedBy = "packageEntity", cascade = CascadeType.ALL)
    private List<PriceTier> priceTiers = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "packageEntity", cascade = CascadeType.ALL)
    private List<IncludedService> services = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private PackageCategory category;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getBasePrice() { return basePrice; }
    public void setBasePrice(BigDecimal basePrice) { this.basePrice = basePrice; }

    public Integer getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(Integer maxCapacity) { this.maxCapacity = maxCapacity; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public Boolean getIsPrivate() { return isPrivate; }
    public void setIsPrivate(Boolean isPrivate) { this.isPrivate = isPrivate; }

    public Integer getDistanceMinKm() { return distanceMinKm; }
    public void setDistanceMinKm(Integer distanceMinKm) { this.distanceMinKm = distanceMinKm; }

    public Integer getDistanceMaxKm() { return distanceMaxKm; }
    public void setDistanceMaxKm(Integer distanceMaxKm) { this.distanceMaxKm = distanceMaxKm; }

    public String getFishingType() { return fishingType; }
    public void setFishingType(String fishingType) { this.fishingType = fishingType; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public List<PriceTier> getPriceTiers() { return priceTiers; }
    public void setPriceTiers(List<PriceTier> priceTiers) { this.priceTiers = priceTiers; }

    public List<IncludedService> getServices() { return services; }
    public void setServices(List<IncludedService> services) { this.services = services; }

    public PackageCategory getCategory() { return category; }
    public void setCategory(PackageCategory category) { this.category = category; }

    @Override
    public String toString() {
        return "Package{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", categoryId=" + categoryId +
            ", isActive=" + isActive +
            '}';
    }
} 