package com.rhumuda.charterservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("BOAT")
public class BoatPackage extends Package {
    private Double adultPrice;
    private Double kidPrice;
    private Double privateBoatPrice;
} 