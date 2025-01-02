package com.rhumuda.charterservice.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class PriceRange {
    private Double min;
    private Double max;
} 