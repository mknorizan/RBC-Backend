package com.rhumuda.charterservice.dto;

import lombok.Data;
import java.util.List;

@Data
public class PackageDTO {
    private String packageId;
    private String packageType;
    private String title;
    private String description;
    private String duration;
    private int capacity;
    private List<String> services;
    
    // Boat package specific fields
    private Double adultPrice;
    private Double kidPrice;
    private Double privateBoatPrice;
    
    // Fishing package specific fields
    private Double priceMin;
    private Double priceMax;
    private String distanceRange;
    private List<String> techniques;
} 