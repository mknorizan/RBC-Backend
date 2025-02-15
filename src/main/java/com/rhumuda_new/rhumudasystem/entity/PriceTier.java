package com.rhumuda_new.rhumudasystem.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "price_tiers")
public class PriceTier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tier_type", nullable = false)
    private String type;

    @Column(nullable = false)
    private BigDecimal price;

    private String label;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id", nullable = false)
    private Package packageEntity;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public Package getPackageEntity() { return packageEntity; }
    public void setPackageEntity(Package packageEntity) { this.packageEntity = packageEntity; }

    // Add toString for debugging
    @Override
    public String toString() {
        return "PriceTier{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", label='" + label + '\'' +
                '}';
    }
} 