package com.rhumuda.charterservice.dto;

import lombok.Data;
import java.util.List;

@Data
public class PackageDetails {
    private Long id;
    private String name;
    private String description;
    private String type;
    private Double price;
    private List<String> services;
    
    // Boat package specific fields
    private Double adultPrice;
    private Double kidPrice;
    private Double privateBoatPrice;
    
    // Fishing package specific fields
    private Double priceMin;
    private Double priceMax;
    private String distance;
    private List<String> techniques;
} 